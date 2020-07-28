package com.example.healthcare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;
import com.example.healthcare.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private EditText inputemail, inputpassword,fullName, address, contact;
    private Button btnSignup;
    private ProgressDialog pd;
    private Spinner gender, bloodgroup;

    private boolean isUpdate = false;

    private DatabaseReference db_ref, donor_ref;
    private FirebaseDatabase db_User;
    private CheckBox isDonor;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference , databaseReference1;

    private UserData ud = new UserData();

    String p;

    private SharedPreferences sharedPreferences;

    String Contact;
    String Name;
    String blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("hc" , MODE_PRIVATE);

        db_User = FirebaseDatabase.getInstance();
        db_ref = db_User.getReference("users");
        donor_ref = db_User.getReference("donors");

        inputemail = findViewById(R.id.input_userEmail);
        inputpassword = findViewById(R.id.input_password);
        fullName = findViewById(R.id.input_fullName);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.inputAddress);
        bloodgroup = findViewById(R.id.inputBloodGroup);
        contact = findViewById(R.id.inputMobile);
        isDonor = findViewById(R.id.checkbox);

        btnSignup = findViewById(R.id.button_register);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference1 = firebaseDatabase.getReference("donors");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                final String email = inputemail.getText().toString();
                final String password = inputpassword.getText().toString();
                Name = fullName.getText().toString();
                final int Gender = gender.getSelectedItemPosition();
                Contact = contact.getText().toString();
                final int BloodGroup = bloodgroup.getSelectedItemPosition();
                final String Address = address.getText().toString();
                blood = bloodgroup.getSelectedItem().toString();

                ud.setAddress(Address);
                ud.setBloodGroup(blood);
                ud.setContact(Contact);
                ud.setEmail(email);
                ud.setGender(Gender);
                ud.setName(Name);

                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                assert digest != null;
                byte[] pp =  digest.digest(password.getBytes(StandardCharsets.UTF_8));
                p= Arrays.toString(pp);

                ud.setPassword(p);



                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(Contact).exists()){
                            Toast.makeText(ProfileActivity.this , "User already exists" , Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                            finish();
                        }else{


                            databaseReference.child(Contact).setValue(ud);

                            if(isDonor.isChecked()){
                                databaseReference1.child("donors").child(blood).setValue(ud);
                            }
                            Toast.makeText(getApplicationContext(),"Registration Successful!",Toast.LENGTH_LONG).show();
                            savePreferences();
                            Intent i = new Intent(ProfileActivity.this , Dashboard.class);
                            i.putExtra("PhoneNo" , Contact);
                            startActivity(i);
                            pd.dismiss();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(),"Process Cancelled!",Toast.LENGTH_LONG).show();
                        pd.cancel();

                    }
                });


            }
        });
    }

    private void savePreferences(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name" , Name);
        editor.putString("Password" , p);
        editor.putBoolean("LoggedIn" , true);
        editor.putString("phone number of current user" , Contact);
        editor.commit();
    }
}