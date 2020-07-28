package com.example.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthcare.R;
public class Selection extends AppCompatActivity {

    private Button user  , doctor , bloodbank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        user = (Button)findViewById(R.id.user);
        doctor = (Button)findViewById(R.id.doctor);
        bloodbank = (Button)findViewById(R.id.bloodbank);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Selection.this , userlogin.class);
                startActivity(i);
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Selection.this , doctorlogin.class);
                startActivity(i);
            }
        });

        bloodbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Selection.this , banklogin.class);
                startActivity(i);
            }
        });
    }
}
