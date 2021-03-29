package com.bpal.feesmanagement.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpal.feesmanagement.DBModel.User;
import com.bpal.feesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ss.anoop.awesometextinputlayout.AwesomeTextInputLayout;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressDialog progressDialog;
    EditText etFirstName, etLastName, etEmail, ph, etPassword;
    Button btRegister;
    ImageView Pass1;
    TextView tvAlreadyRegistered;
    DatabaseReference reference;
    String email, pass, firstName, lastname, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btRegister = findViewById(R.id.btRegister);
        ph = findViewById(R.id.et_stph);
        tvAlreadyRegistered = findViewById(R.id.tvAlreadyRegistered);
        Pass1 = findViewById(R.id.show3);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Staff");
        //reference.keepSynced(true);
        auth = FirebaseAuth.getInstance();

        tvAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StaffLoginActivity.class));
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                pass = etPassword.getText().toString();
                firstName = etFirstName.getText().toString();
                lastname = etLastName.getText().toString();
                phone = ph.getText().toString();


                if (firstName.isEmpty()) {
                    etFirstName.setError("Required!");
                    etFirstName.requestFocus();
                    return;
                }
                if (lastname.isEmpty()) {
                    etLastName.setError("Required!");
                    etLastName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    etEmail.setError("Required!");
                    etEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Enter valid Email-ID");
                    etEmail.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    etPassword.setError("Required!");
                    etPassword.requestFocus();
                    return;
                }
                if (pass.length() <= 8) {
                    etPassword.setError("Enter valid Password!");
                    etPassword.requestFocus();
                    return;
                }
                if (phone.length() != 10) {
                    ph.setError("Enter valid Mobile Number!");
                    ph.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    ph.setError("Required!");
                    ph.requestFocus();
                    return;
                }

                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                final User user = new User(firstName, lastname, email, pass, phone);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "User already exists!!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (!dataSnapshot.child(phone).exists()) {
                            auth.signInAnonymously().addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        reference.child(phone).setValue(user);
                                        Toast.makeText(getApplicationContext(), "Successfully SignedUp!", Toast.LENGTH_LONG).show();
                                        auth.signOut();
                                        startActivity(new Intent(SignupActivity.this, StaffLoginActivity.class));
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Registration Error!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }

    public void showpass3(View view) {
        if (view.getId()==R.id.show3) {
            if(etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                Pass1.setImageResource(R.drawable.vispass);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                Pass1.setImageResource(R.drawable.showpass);
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}