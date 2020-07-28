package com.example.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healthcare.R;

import java.util.ArrayList;

public class contact extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText msg;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        msg = findViewById(R.id.msg);
        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m1 = name.getText().toString();
                String m2 = email.getText().toString();
                String m3 = phone.getText().toString();
                String m4 = msg.getText().toString();

                String message = "Name :- "+m1+"\n"+"Email-id :- "+m2+"\n"+"Phone no. :- "+m3+"\n\n"+"Message :- \n"+m4;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ayush@mnnit.ac.in"});

                intent.putExtra(Intent.EXTRA_TEXT, message);
                String s = getString(R.string.chooser);
                Intent ch = Intent.createChooser(intent, s);
                startActivity(ch);

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Us");
    }
}