package com.bpal.feesmanagement.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bpal.feesmanagement.DBModel.DBFee;
import com.bpal.feesmanagement.DBModel.DBPaid;
import com.bpal.feesmanagement.DBModel.Option;
import com.bpal.feesmanagement.DBModel.Student;
import com.bpal.feesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Add_Students extends AppCompatActivity {

    EditText fname, lname,  uid, ph, year, total, paid, remains;
    static EditText dob, date_paid;
    Button reg;
    RadioGroup cagroup, ccgroup, csgroup, sgroup;
    RadioButton ca_ba, ca_bfa, ca_bba, ca_llb, ca_bjm, ca_bfd, ca_bhm;
    RadioButton cc_ca, cc_cs, cc_bac, cc_bba, cc_bms, cc_bca, cc_baf;
    RadioButton cs_bfa, cs_bsc, cs_bca, cs_mbbs, cs_bp;
    RadioButton arts, com, sci;
    DatabaseReference reference;
    String firstname, lastname, DOB, UID, phone, Year, Total, Paid, Remaining, Date;
    String Stream, Course, key= String.valueOf(System.currentTimeMillis());
    ProgressDialog progressDialog;
    ImageView cal1, cal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__students);

        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Student");
        progressDialog = new ProgressDialog(this);

        uid=findViewById(R.id.st_uid);
        fname=findViewById(R.id.st_1name);
        lname=findViewById(R.id.st_lname);
        dob=findViewById(R.id.st_dob);
        ph=findViewById(R.id.stph);

        year=findViewById(R.id.st_year);
        total=findViewById(R.id.st_total);
        paid=findViewById(R.id.st_fp);
        remains=findViewById(R.id.st_frem);
        date_paid=findViewById(R.id.st_fpdate);

        reg=findViewById(R.id.btn_reg);
        cal1=findViewById(R.id.cal1);
        cal2=findViewById(R.id.cal2);

        cagroup=findViewById(R.id.cagroup);
        ccgroup=findViewById(R.id.ccgroup);
        csgroup=findViewById(R.id.csgroup);

        arts=findViewById(R.id.st_art);
        com=findViewById(R.id.st_com);
        sci=findViewById(R.id.st_sci);
    //Stream
        sgroup=findViewById(R.id.sgroup);
    //Arts
        ca_ba=findViewById(R.id.ca_ba);
        ca_bfa=findViewById(R.id.ca_bfa);
        ca_bba=findViewById(R.id.ca_bba);
        ca_llb=findViewById(R.id.ca_llb);
        ca_bjm=findViewById(R.id.ca_bjm);
        ca_bfd=findViewById(R.id.ca_bfd);
        ca_bhm=findViewById(R.id.ca_bhm);
    //commerce
        cc_ca=findViewById(R.id.cc_ca);
        cc_cs=findViewById(R.id.cc_cs);
        cc_bac=findViewById(R.id.cc_bac);
        cc_bba=findViewById(R.id.cc_bba);
        cc_bms=findViewById(R.id.cc_bms);
        cc_bca=findViewById(R.id.cc_bca);
        cc_baf=findViewById(R.id.cc_baf);
    //Science
        cs_bfa=findViewById(R.id.cs_bfa);
        cs_bsc=findViewById(R.id.cs_bsc);
        cs_bca=findViewById(R.id.cs_bca);
        cs_mbbs=findViewById(R.id.cs_mbbs);
        cs_bp=findViewById(R.id.cs_bp);

        cal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdob(v);
            }
        });

        cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdate(v);
            }
        });

        arts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cagroup.setVisibility(View.VISIBLE);
                    ccgroup.setVisibility(View.GONE);
                    csgroup.setVisibility(View.GONE);
                    Stream = arts.getText().toString().trim();

                    cagroup.clearCheck();
                    csgroup.clearCheck();
                    ccgroup.clearCheck();
                }
            }
        });
        com.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked){
                  ccgroup.setVisibility(View.VISIBLE);
                  cagroup.setVisibility(View.GONE);
                  csgroup.setVisibility(View.GONE);
                  Stream = com.getText().toString().trim();
                  cagroup.clearCheck();
                  csgroup.clearCheck();
                  ccgroup.clearCheck();
              }
            }
        });
        sci.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    csgroup.setVisibility(View.VISIBLE);
                    cagroup.setVisibility(View.GONE);
                    ccgroup.setVisibility(View.GONE);
                    Stream = sci.getText().toString().trim();
                    cagroup.clearCheck();
                    csgroup.clearCheck();
                    ccgroup.clearCheck();
                }
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                UID=uid.getText().toString().trim();
                firstname=fname.getText().toString().trim();
                lastname=lname.getText().toString().trim();
                DOB=dob.getText().toString().trim();
                phone=ph.getText().toString().trim();

                Year=year.getText().toString().trim();
                Total=total.getText().toString().trim();
                Paid=paid.getText().toString().trim();
                Date=date_paid.getText().toString().trim();
                Remaining=remains.getText().toString().trim();

                progressDialog.setMessage("Adding Student " + firstname + " " + lastname + ". Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                if (UID.isEmpty()){
                    uid.setError("Required");
                    uid.requestFocus();
                }
                if (firstname.isEmpty()){
                    fname.setError("Required");
                    fname.requestFocus();
                }
                if (lastname.isEmpty()){
                    lname.setError("Required");
                    lname.requestFocus();
                }
                if (DOB.isEmpty()){
                    dob.setError("Required");
                    dob.requestFocus();
                }
                if (phone.isEmpty()){
                    ph.setError("Required");
                    ph.requestFocus();
                }
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
                    date_paid.setError("Required");
                    date_paid.requestFocus();
                }
                if (Remaining.isEmpty()){
                    remains.setError("Required");
                    remains.requestFocus();
                }


                if (sgroup.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast.makeText(getApplicationContext(), "Please select Stream!!", Toast.LENGTH_LONG).show();
                } else {
                    // one of the radio buttons is checked
                    if (cagroup.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getApplicationContext(), "Please select Course!!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (ca_ba.isChecked()) {
                            Course = ca_ba.getText().toString();
                        } else if (ca_bfa.isChecked()) {
                            Course = ca_bfa.getText().toString();
                        } else if (ca_bba.isChecked()) {
                            Course = ca_bba.getText().toString();
                        } else if (ca_llb.isChecked()) {
                            Course = ca_llb.getText().toString();
                        } else if (ca_bjm.isChecked()) {
                            Course = ca_bjm.getText().toString();
                        } else if (ca_bfd.isChecked()) {
                            Course = ca_bfd.getText().toString();
                        } else if (ca_bhm.isChecked()) {
                            Course = ca_bhm.getText().toString();
                        }
                    }

                    if (ccgroup.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getApplicationContext(), "Please select Course!!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (cc_ca.isChecked()) {
                            Course = cc_ca.getText().toString();
                        } else if (cc_cs.isChecked()) {
                            Course = cc_cs.getText().toString();
                        } else if (cc_bac.isChecked()) {
                            Course = cc_bac.getText().toString();
                        } else if (cc_bba.isChecked()) {
                            Course = cc_bba.getText().toString();
                        } else if (cc_bms.isChecked()) {
                            Course = cc_bms.getText().toString();
                        } else if (cc_bca.isChecked()) {
                            Course = cc_bca.getText().toString();
                        } else if (cc_baf.isChecked()) {
                            Course = cc_baf.getText().toString();
                        }
                    }

                    if (csgroup.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getApplicationContext(), "Please select Course!!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (cs_bfa.isChecked()) {
                            Course = cs_bfa.getText().toString();
                        } else if (cs_bsc.isChecked()) {
                            Course = cs_bsc.getText().toString();
                        } else if (cs_bca.isChecked()) {
                            Course = cs_bca.getText().toString();
                        } else if (cs_mbbs.isChecked()) {
                            Course = cs_mbbs.getText().toString();
                        } else if (cs_bp.isChecked()) {
                            Course = cs_bp.getText().toString();
                        }
                    }

                    Log.d("-----------Course------------", Course);
                    Log.d("-----------Stream------------", Stream);

                    Student student = new Student(UID, firstname, lastname, DOB, phone, phone, "asdfghjkl",
                            Stream, Course, Year, Total, Paid, Remaining, Date);
                    DBFee dbFee = new DBFee(Stream, Course, Year, Total, Paid, Remaining, Date);
                    DBPaid dbPaid = new DBPaid(Total, Paid, Remaining, Date, Year);

                    Option option = new Option(Year);

                    reference.child(phone).child("Profile").setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                reference.child(phone).child("Fee").child("Info").setValue(dbFee);
                                reference.child(student.getPhone()).child("Fee").child("EMI").child("Years").child(Year).setValue(option);
                                reference.child(phone).child("Fee").child("EMI").child(Year).child(key).setValue(dbPaid);
                                Toast.makeText(getApplicationContext(), "Student Added Successfully", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Some Error occured! Please try again later!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
                }

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

            date_paid.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    private void setdob(View v) {
        DialogFragment newFragment = new DobPickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public static class DobPickerFragment extends DialogFragment implements
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

            dob.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

}