package com.example.healthcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.healthcare.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static int SPLASH_TIME_OUT = 2500;
    private Toolbar toolbar;
    Context mContext;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private CardView doctor;
    private CardView blog;
    private CardView mood;
    private CardView faq;
    private CardView blood;
    private CardView disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        doctor = findViewById(R.id.doctor);
        blog = findViewById(R.id.blog);
        mood = findViewById(R.id.mood);
        faq = findViewById(R.id.faq);
        blood = findViewById(R.id.blood);
        disease = findViewById(R.id.disease);


        doctor.setOnClickListener(this);
        blog.setOnClickListener(this);
        mood.setOnClickListener(this);
        faq.setOnClickListener(this);
        blood.setOnClickListener(this);
        disease.setOnClickListener(this);


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        Log.i("hn", "hn");
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i, i2, i3, i4, i5, i6, i7, i8;
        switch (v.getId())
        {
            case R.id.doctor : i = new Intent(this, doctor_home.class); this.startActivity ( i );
                break;

            case R.id.mood : i2 = new Intent(this, com.example.healthcare.Activities.mood.class); this.startActivity ( i2 );
                break;

            case R.id.blood : i3 = new Intent(this, Dashboard.class); this.startActivity ( i3 );
                break;

            case R.id.disease : i3 = new Intent(this, Disease.class); this.startActivity ( i3 );
                break;

            case R.id.blog : i3 = new Intent(this, Blog.class); this.startActivity ( i3 );
                break;
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Intent i;
        Log.i("Problem", "Don't know");
        int id = item.getItemId();

        if (id == R.id.pedo)
        {
            i=new Intent(MainActivity.this, Stepcount.class);
            startActivity(i);
        }
        else if (id == R.id.about)
        {
            i=new Intent(MainActivity.this, about.class);
            startActivity(i);
        }
        else if (id == R.id.contact)
        {
            i=new Intent(MainActivity.this, contact.class);
            startActivity(i);
        } else if (id == R.id.feedback)
        {
            i=new Intent(MainActivity.this, feedback.class);
            startActivity(i);
        }
        return true;
    }
}

