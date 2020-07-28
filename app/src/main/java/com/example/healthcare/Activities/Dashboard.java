package com.example.healthcare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.healthcare.R;
import com.example.healthcare.UserData;
import com.example.healthcare.fragments.AboutUs;
import com.example.healthcare.fragments.AchievmentsView;
import com.example.healthcare.fragments.BloodInfo;
import com.example.healthcare.fragments.HomeView;
import com.example.healthcare.fragments.NearByHospitalActivity;
import com.example.healthcare.fragments.SearchDonorFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    private TextView getUserName;
    private TextView getUserEmail;
    private FirebaseDatabase user_db;
    private DatabaseReference userdb_ref;

    private ProgressDialog pd;
    private UserData ud;

    String contactNo;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences("H3" , MODE_PRIVATE);
        contactNo = sharedPreferences.getString("phone number of current user" , "0");

        user_db = FirebaseDatabase.getInstance();
        userdb_ref = user_db.getReference("users");

        //ud = userdb_ref.child(contactNo).getValue(ud);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, PostActivity.class);
                i.putExtra("Contact" , contactNo);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.donateinfo) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new BloodInfo()).addToBackStack("tag").commit();
        }
        if (id == R.id.devinfo) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new AboutUs()).addToBackStack("tag").commit();
        }else if (id == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new HomeView()).addToBackStack("tag").commit();

        } else if (id == R.id.userprofile) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
        else if (id == R.id.user_achiev) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new AchievmentsView()).addToBackStack("tag").commit();

        }
        else if (id == R.id.logout) {
            changePreferences();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.blood_storage){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new SearchDonorFragment()).addToBackStack("tag").commit();

        } else if (id == R.id.nearby_hospital) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new NearByHospitalActivity()).addToBackStack("tag").commit();

        }

        return super.onOptionsItemSelected(item);
    }

    private void changePreferences() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LoggedIn" , true);
        editor.commit();
    }
}
