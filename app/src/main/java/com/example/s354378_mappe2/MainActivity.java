package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.inputFirstName);
        lastName = (EditText) findViewById(R.id.inputLastName);

        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Friend newFriend = new Friend();
                newFriend.first = firstName.getText().toString();
                newFriend.last = lastName.getText().toString();
                System.out.println(newFriend.first + " " + newFriend.last);
            }
        });
    }
}