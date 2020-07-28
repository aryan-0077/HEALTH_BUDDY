package com.example.healthcare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.healthcare.viewmodels.DocInfo;
import com.example.healthcare.R;

import java.util.ArrayList;

public class doctor_home extends AppCompatActivity {

    private Toolbar toolbar;
    private CardView dentist , opthalmologist , cardioloist , neurologist , gastroentrologist , peditrician;

    Intent i;

    private ArrayList<DocInfo> docs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Doctor");

        dentist = (CardView)findViewById(R.id.dentist);
        opthalmologist = (CardView)findViewById(R.id.opthal);
        cardioloist = (CardView)findViewById(R.id.cardio);
        neurologist = (CardView)findViewById(R.id.neuro);
        gastroentrologist = (CardView)findViewById(R.id.gastro);
        peditrician = (CardView)findViewById(R.id.pedia);

        i = new Intent(doctor_home.this , docList.class);

        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               i.putExtra("type" , "dentist");
               startActivity(i);
            }
        });

        opthalmologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type" , "opthalmologist");
                startActivity(i);
            }
        });

        cardioloist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type" , "cardiologist");
                startActivity(i);
            }
        });

        neurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type" , "neurologist");
                startActivity(i);
            }
        });

        gastroentrologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type" , "gastroentrologist");
                startActivity(i);
            }
        });

        peditrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("type" , "peditrician");
                startActivity(i);
            }
        });
    }


}
