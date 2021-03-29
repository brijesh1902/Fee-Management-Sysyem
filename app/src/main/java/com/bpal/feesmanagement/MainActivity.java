package com.bpal.feesmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpal.feesmanagement.Login.StaffLoginActivity;
import com.bpal.feesmanagement.Login.StudentLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cvStaff = findViewById(R.id.cvStaff);

        CardView cvStudent = findViewById(R.id.cvStudent);

        TextView text = findViewById(R.id.textView25);
        Typeface type = Typeface.createFromAsset(getAssets(), "font/rockwellextrabold.ttf");
        text.setTypeface(type);

        cvStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StaffLoginActivity.class));
            }
        });

        cvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentLoginActivity.class));
            }
        });

    }
}