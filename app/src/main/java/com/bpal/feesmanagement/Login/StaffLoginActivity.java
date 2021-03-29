package com.bpal.feesmanagement.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpal.feesmanagement.Common.Common;
import com.bpal.feesmanagement.DBModel.User;
import com.bpal.feesmanagement.R;
import com.bpal.feesmanagement.Staff.Staff_MainActivity;
import com.bpal.feesmanagement.Student.Student_HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class StaffLoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressDialog progressDialog;
    EditText etphone, etPassword;
    Button btLogin;
    ImageView Pass1;
    TextView  tvForgot, signup;
    DatabaseReference reference;
    String phone, pass;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);

        etphone = findViewById(R.id.st_phone);
        etPassword = findViewById(R.id.st_Password);
        checkBox = findViewById(R.id.st_RememberMe);
        btLogin = findViewById(R.id.bt_stLogin);
        Pass1 = findViewById(R.id.show2);
        signup = findViewById(R.id.st_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Staff");
        reference.keepSynced(true);
        auth = FirebaseAuth.getInstance();

        auth = FirebaseAuth.getInstance();
        Paper.init(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etphone.getText().toString();
                pass = etPassword.getText().toString();

                if (phone.isEmpty()) {
                    etphone.setError("Required!");
                    etphone.requestFocus();
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

                progressDialog.setMessage("Logging In...");
                progressDialog.show();
                if (checkBox.isChecked()) {
                    Paper.book("Staff").write(Common.Staff, phone);
                    Paper.book("Staff").write(Common.Staff_password, pass);
                }
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            progressDialog.dismiss();
                            User user = dataSnapshot.child(phone).getValue(User.class);
                            if (user.getPhone().equals(phone) && user.getPassword().equals(pass)) {
                                auth.signInAnonymously().addOnCompleteListener(StaffLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Common.currentstaff = user;
                                            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(StaffLoginActivity.this, Staff_MainActivity.class));
                                            finish();
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "User not exists!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void showpass2(View view) {
        if (view.getId()==R.id.show2) {
            if(etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                Pass1.setImageResource(R.drawable.vispass);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                Pass1.setImageResource(R.drawable.showpass);
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Paper.init(this);

        String phone = Paper.book("Staff").read(Common.Staff);
        String pwd = Paper.book("Staff").read(Common.Staff_password);
        if (phone!=null && pwd!=null) {
            if (!phone.isEmpty() && !pwd.isEmpty())
                login(phone, pwd);
        }

    }

    private void login(String phone, String pwd) {
        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists() ) {
                    progressDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    if (user.getPhone().equals(phone) && user.getPassword().equals(pwd)) {
                        auth.signInAnonymously().addOnCompleteListener(StaffLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Common.currentstaff = user;
                                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(StaffLoginActivity.this, Staff_MainActivity.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "User not exists!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}