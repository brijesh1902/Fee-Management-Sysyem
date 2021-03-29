package com.bpal.feesmanagement.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpal.feesmanagement.Adapter.YearAdapter;
import com.bpal.feesmanagement.Common.Common;
import com.bpal.feesmanagement.DBModel.Option;
import com.bpal.feesmanagement.DBModel.Student;
import com.bpal.feesmanagement.MainActivity;
import com.bpal.feesmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;
import okhttp3.internal.Util;


public class Student_HomeActivity extends AppCompatActivity {

    TextView txt_name, txt_uid, tx_ph, txt_dob, txt_strm, txt_course, txt_year;
    TextView txt_year1, tx_tfee, txt_fpaid, txt_pdate, txt_frem;
    RecyclerView recyclerView;
    ArrayList<Option> list;
    YearAdapter adapter;
    DatabaseReference reference;
    EditText pass;
    ImageView image;
    Student student = Common.currentstudent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student, menu);
        //MenuItem menuItem = menu.findItem(R.id.logout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.slogout) {
            Paper.book("Student").delete(Common.Student);
            Paper.book("Student").delete(Common.Student_password);
            //auth.signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        if (id==R.id.sync_pass) {
           changepassword();
        }

        return super.onOptionsItemSelected(item);
    }

    private void changepassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Student_HomeActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.change_password, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnok = dialogView.findViewById(R.id.button2);
        Button btncancel = dialogView.findViewById(R.id.button);
        pass = dialogView.findViewById(R.id.cpassword);
        image = dialogView.findViewById(R.id.cshow);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        pass.setText(student.getPassword());

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Pass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(Pass)){
                    pass.setError("Required!");
                    pass.requestFocus();
                    return;
                }
                if (Pass.length() <= 8) {
                    pass.setError("Enter valid Password!");
                    pass.requestFocus();
                    return;
                }

                student.setPassword(Pass);
                reference.child(student.getPhone()).child("Profile").setValue(student);
                Paper.book("Student").delete(Common.Student);
                Paper.book("Student").delete(Common.Student_password);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                alertDialog.dismiss();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__home);

        txt_name = findViewById(R.id.txt_name);
        txt_uid = findViewById(R.id.txt_uid);
        tx_ph = findViewById(R.id.tx_ph);
        txt_dob = findViewById(R.id.txt_dob);
        txt_strm = findViewById(R.id.txt_strm);
        txt_course = findViewById(R.id.txt_course);
        txt_year = findViewById(R.id.txt_year);

        tx_tfee = findViewById(R.id.tx_tfee);
        txt_fpaid = findViewById(R.id.txt_fpaid);
        txt_pdate = findViewById(R.id.txt_pdate);
        txt_frem = findViewById(R.id.txt_frem);
        txt_year1 = findViewById(R.id.txt_year1);

        recyclerView = findViewById(R.id.rv_stdet);

        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Student");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        txt_name.setText(student.getFirstname()+" "+student.getLastname());
        txt_uid.setText(student.getUid());
        tx_ph.setText(student.getPhone());
        txt_dob.setText(student.getDob());
        txt_strm.setText(student.getStream());
        txt_course.setText(student.getCourse());
        txt_year.setText(student.getYear());

        txt_year1.setText(student.getYear());
        tx_tfee.setText("₹ "+student.getTotal()+".00");
        txt_fpaid.setText("₹ "+student.getPaid()+".00");
        txt_pdate.setText(student.getDate());
        txt_frem.setText("₹ "+student.getRemaining()+".00");

        reference.child(student.getPhone()).child("Fee").child("EMI").child("Years").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list = new ArrayList<>();
                    Log.d("-----Key-----", dataSnapshot.getKey());
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Option k = snap.getValue(Option.class);
                        list.add(k);
                        Log.d("-----sKey-----", snap.getKey());
                    }
                    adapter = new YearAdapter(getApplicationContext(), list);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void cshowpass(View view) {
        if (view.getId()==R.id.cshow) {
            if(pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                image.setImageResource(R.drawable.vispass);
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                image.setImageResource(R.drawable.showpass);
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}