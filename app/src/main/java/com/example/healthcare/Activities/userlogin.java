package com.example.healthcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import cz.msebera.android.httpclient.extras.PRNGFixes;

public class userlogin extends AppCompatActivity {

    private EditText phone , password;
    private Button login , signup;
    SharedPreferences sharedPreferences;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog pd;

    private String phoneno , pswrd , p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        sharedPreferences = getSharedPreferences("hc" , MODE_PRIVATE);

        phone = (EditText)findViewById(R.id.phoneno);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        signup = (Button)findViewById(R.id.signup);

        phoneno = phone.getText().toString().trim();
        pswrd = password.getText().toString().trim();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        if (sharedPreferences != null){

            if (checkIfLoggedIn()){
                Intent i = new Intent(userlogin.this , Dashboard.class);
                startActivity(i);
                finish();
            }else{

            }
        }

        //setContentView(R.layout.activity_main);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userlogin.this  , ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert digest != null;
        byte[] pp =  digest.digest(pswrd.getBytes(StandardCharsets.UTF_8));
        p= Arrays.toString(pp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            UserData ud = d.getValue(UserData.class);
                            if (ud.getPassword().equals(p)) {
                                Intent i = new Intent(userlogin.this , Dashboard.class);
                                startActivity(i);
                                pd.dismiss();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public boolean checkIfLoggedIn(){

        boolean check = sharedPreferences.getBoolean("LoggedIn" , false);

        if (check){
            return true;
        }else{
            return false;
        }
    }

}