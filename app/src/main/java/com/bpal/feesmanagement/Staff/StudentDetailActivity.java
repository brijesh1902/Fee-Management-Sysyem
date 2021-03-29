package com.bpal.feesmanagement.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpal.feesmanagement.Adapter.YearAdapter;
import com.bpal.feesmanagement.Common.Common;
import com.bpal.feesmanagement.DBModel.DBFee;
import com.bpal.feesmanagement.DBModel.DBPaid;
import com.bpal.feesmanagement.DBModel.Option;
import com.bpal.feesmanagement.DBModel.Student;
import com.bpal.feesmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentDetailActivity extends AppCompatActivity {

    TextView txt_name, txt_uid, tx_ph, txt_dob, txt_strm, txt_course, txt_year;
    TextView txt_year1, tx_tfee, txt_fpaid, txt_pdate, txt_frem, txt_y;
    Button btn_upfee;
    RecyclerView recyclerView;
    ArrayList<Option> list;
    YearAdapter adapter;
    DatabaseReference reference;
    int fee = 0 ;
    String key= String.valueOf(System.currentTimeMillis());
    Student student = Common.currentstudent;
    static EditText date_paid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

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

        btn_upfee = findViewById(R.id.btn_upfee);
        txt_y = findViewById(R.id.txt_y);
        recyclerView = findViewById(R.id.rv_stdet);

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

        btn_upfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatefee(v);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Student");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        reference.child(student.getPhone()).child("Fee").child("EMI").child("Years").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list = new ArrayList<>();
                    Log.d("-----Key-----", dataSnapshot.getKey());
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Option k = snap.getValue(Option.class);
                        list.add(k);
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

    private void updatefee(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.update_details, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnok = dialogView.findViewById(R.id.btn_reg);
        EditText year=dialogView.findViewById(R.id.st_year);
        EditText total=dialogView.findViewById(R.id.st_total);
        EditText paid=dialogView.findViewById(R.id.st_fp);
        EditText remains=dialogView.findViewById(R.id.st_frem);
        date_paid1=dialogView.findViewById(R.id.st_fpdate1);
        ImageView cal2=dialogView.findViewById(R.id.cal2);

        cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdate(v);
            }
        });

        year.setText(student.getYear());
        total.setText(student.getTotal());
        paid.setText(student.getPaid());
        remains.setText(student.getRemaining());

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Year=year.getText().toString().trim();
                String Total=total.getText().toString().trim();
                String Paid=paid.getText().toString().trim();
                String Date=date_paid1.getText().toString().trim();
                String Remaining=remains.getText().toString().trim();
                if (Year.isEmpty()){
                    year.setError("Required");
                    year.requestFocus();
                }
                if (Total.isEmpty()){
                    total.setError("Required");
                    total.requestFocus();
                }
                if (Paid.isEmpty()){
                    paid.setError("Required");
                    paid.requestFocus();
                }
                if (Date.isEmpty()){
                    date_paid1.setError("Required");
                    date_paid1.requestFocus();
                }
                if (Remaining.isEmpty()){
                    remains.setError("Required");
                    remains.requestFocus();
                }

                DBFee dbFee = new DBFee();
                DBPaid dbPaid = new DBPaid(Total, Paid, Remaining, Date, Year);

                Option option = new Option(Year);

                reference.child(student.getPhone()).child("Fee").child("EMI").child(Year).child(key).setValue(dbPaid);
                reference.child(student.getPhone()).child("Fee").child("EMI").child("Years").child(Year).setValue(option);
                if (Year.equals(student.getYear())) {
                    fee += (Integer.parseInt(student.getPaid())) + (Integer.parseInt(Paid));
                    //Log.d("-------VALUE-------", String.valueOf(fee));
                    student.setPaid(String.valueOf(fee));
                    student.setTotal(Total);
                    student.setYear(Year);
                    student.setDate(Date);
                    student.setRemaining(Remaining);
                    reference.child(student.getPhone()).child("Profile").setValue(student);

                    dbFee.setPaidFee(String.valueOf(fee));
                    dbFee.setPaidDate(Date);
                    dbFee.setRemainingFee(Remaining);
                    dbFee.setCourse(student.getCourse());
                    dbFee.setCurrentYear(Year);
                    dbFee.setStream(student.getStream());
                    dbFee.setTotalFee(Total);
                    reference.child(student.getPhone()).child("Fee").child("Info").setValue(dbFee);
                } else {
                    student.setPaid(Paid);
                    student.setTotal(Total);
                    student.setYear(Year);
                    student.setDate(Date);
                    student.setRemaining(Remaining);
                    reference.child(student.getPhone()).child("Profile").setValue(student);

                    dbFee.setPaidFee(String.valueOf(fee));
                    dbFee.setPaidDate(Date);
                    dbFee.setRemainingFee(Remaining);
                    dbFee.setCourse(student.getCourse());
                    dbFee.setCurrentYear(Year);
                    dbFee.setStream(student.getStream());
                    dbFee.setTotalFee(Total);
                    reference.child(student.getPhone()).child("Fee").child("Info").setValue(dbFee);
                }
                finish();
                alertDialog.dismiss();
            }
        });
    }

    private void setdate(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            date_paid1.setText(day + "/" + (month + 1) + "/" + year);
        }

    }

}