package com.example.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.healthcare.R;

import java.util.ArrayList;

public class about extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        Log.i("xyz", "xyz");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar.setTitleTextColor(getResources().getColor(R.color.someColor));
        getSupportActionBar().setTitle("About Us");
    }
}