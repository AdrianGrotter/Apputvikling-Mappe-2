package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText phone;

    DBHandler dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.inputFirstName);
        lastName = (EditText) findViewById(R.id.inputLastName);
        phone = (EditText) findViewById(R.id.inputPhone);


        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);


        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact();
                newContact.first = firstName.getText().toString();
                newContact.last = lastName.getText().toString();
                newContact.phone = phone.getText().toString();

                System.out.println(newContact.first + " " + newContact.last + " " + newContact+phone);

                dbHelper.addContact(db, newContact);
            }
        });
    }

    @Override
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }
}