package com.example.healthcare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.healthcare.R;
import com.example.healthcare.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PostActivity extends AppCompatActivity {

    ProgressDialog pd;

    EditText text1, text2;
    Spinner spinner1;
    Button btnpost , bloodbank;

    FirebaseDatabase fdb;
    DatabaseReference db_ref , user_db;

    Calendar cal;
    String uid;
    String Time, Date;
    String contact;
    UserData ud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent i = getIntent();
        contact = i.getStringExtra("Contact");
        Toast.makeText(this , contact , Toast.LENGTH_SHORT).show();

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        Toolbar tb= (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Post Blood Request");


        text1 = findViewById(R.id.getMobile);
        text2 = findViewById(R.id.getLocation);
        bloodbank = (Button)findViewById(R.id.BloodBanks);

        spinner1 = findViewById(R.id.SpinnerBlood);

        btnpost = findViewById(R.id.postbtn);

        cal = Calendar.getInstance();

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        month+=1;
        Time = "";
        Date = "";
        String ampm="AM";

        if(cal.get(Calendar.AM_PM) ==1)
        {
            ampm = "PM";
        }

        if(hour<10)
        {
            Time += "0";
        }
        Time += hour;
        Time +=":";

        if(min<10) {
            Time += "0";
        }

        Time +=min;
        Time +=(" "+ampm);

        Date = day+"/"+month+"/"+year;

        fdb = FirebaseDatabase.getInstance();
        db_ref = fdb.getReference("posts");
        user_db = fdb.getReference("users");

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                final Query findname = user_db.child(contact);

                if(text1.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter your contact number!",
                            Toast.LENGTH_LONG).show();
                }
                else if(text2.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter your location!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    findname.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ud = dataSnapshot.getValue(UserData.class);

                            if (ud == null){
                                Toast.makeText(PostActivity.this , "Here here" , Toast.LENGTH_SHORT).show();

                            }

                            if (dataSnapshot.exists()) {
                                db_ref.child(contact).child("Name").setValue(ud.getName());
                                db_ref.child(contact).child("Contact").setValue(text1.getText().toString());
                                db_ref.child(contact).child("Address").setValue(text2.getText().toString());
                                db_ref.child(contact).child("BloodGroup").setValue(spinner1.getSelectedItem().toString());
                                db_ref.child(contact).child("Time").setValue(Time);
                                db_ref.child(contact).child("Date").setValue(Date);
                                Toast.makeText(PostActivity.this, "Your post has been created successfully",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PostActivity.this, Dashboard.class));
                                pd.dismiss();
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "Database error occured.",
                                        Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("User", databaseError.getMessage());

                        }
                    });
                }
            }
        });

        bloodbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle = new Bundle();
               bundle.putString("Type" , spinner1.getSelectedItem().toString());
               Intent i = new Intent(PostActivity.this , BloodBanksNearMe.class);
               i.putExtra("Type" , spinner1.getSelectedItem().toString());
               startActivity(i);
            }
        });


    }
}
