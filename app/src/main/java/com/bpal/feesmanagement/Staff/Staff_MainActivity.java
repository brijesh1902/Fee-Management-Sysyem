package com.bpal.feesmanagement.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.bpal.feesmanagement.Adapter.StudentAdapter;
import com.bpal.feesmanagement.Common.Common;
import com.bpal.feesmanagement.DBModel.Student;
import com.bpal.feesmanagement.MainActivity;
import com.bpal.feesmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import io.paperdb.Paper;


public class Staff_MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    StudentAdapter adapter;
    ArrayList<Student> list;
    DatabaseReference reference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        MenuItem menuItem = menu.findItem(R.id.searchview);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
        return true;
    }

    private void search(String newText) {
        ArrayList<Student> mylist = new ArrayList<>();
        for (Student student:list) {
            if ( student.getYear().toLowerCase().contains(newText.toLowerCase()) || student.getFirstname().toLowerCase().contains(newText.toLowerCase())
                    || student.getLastname().toLowerCase().contains(newText.toLowerCase()) || student.getPhone().toLowerCase().contains(newText.toLowerCase())
                    || student.getUid().toLowerCase().contains(newText.toLowerCase()) || student.getCourse().toLowerCase().contains(newText.toLowerCase())
                    || student.getStream().toLowerCase().contains(newText.toLowerCase())) {
                mylist.add(student);
            } else {
                Toast.makeText(getApplicationContext(), "Data not available!!", Toast.LENGTH_LONG).show();
            }
        }
        adapter = new StudentAdapter(getApplicationContext(), mylist);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.searchview) {
            return true;
        }
        if (id==R.id.logout) {
            Paper.book("Staff").delete(Common.Staff);
            Paper.book("Staff").delete(Common.Staff_password);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__main);

        recyclerView = findViewById(R.id.rv_addstudents);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Add_Students.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Student");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (reference != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            list.add(snap.child("Profile").getValue(Student.class));
                            Log.d("Valueeeee>>>>", snap.child("Profile").getRef().getKey());

                        }
                        adapter = new StudentAdapter(getApplicationContext(), list);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }
}