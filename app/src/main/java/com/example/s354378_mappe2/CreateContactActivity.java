package com.example.s354378_mappe2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateContactActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText phone;

    DBHandler dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact);

        Button btnMain = (Button) findViewById(R.id.btnMain);
        firstName = (EditText) findViewById(R.id.inputFirstName);
        lastName = (EditText) findViewById(R.id.inputLastName);
        phone = (EditText) findViewById(R.id.inputPhone);

        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);



        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMain();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact();
                newContact.first = firstName.getText().toString();
                newContact.last = lastName.getText().toString();
                newContact.phone = phone.getText().toString();

                System.out.println(newContact.first + " " + newContact.last + " " + newContact+phone);

                firstName.setText("");
                lastName.setText("");
                phone.setText("");

                dbHelper.addContact(db, newContact);
            }
        });
    }

    @Override
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }

    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
