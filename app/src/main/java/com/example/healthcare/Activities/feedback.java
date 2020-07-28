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

public class feedback extends AppCompatActivity {

    private Toolbar toolbar1;
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        toolbar1 = findViewById(R.id.toolbar);
        editText = findViewById(R.id.fdback);
        button = findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = editText.getText().toString();
                String message = "Feedback :- "+feedback;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ayush@mnnit.ac.in"});

                intent.putExtra(Intent.EXTRA_TEXT, message);
                String s = getString(R.string.chooser);
                Intent ch = Intent.createChooser(intent, s);
                startActivity(ch);
            }
        });
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Feedback");
    }
}