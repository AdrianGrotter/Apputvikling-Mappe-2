package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditContactActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText phone;

    DBHandler dbHelper;
    SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState){
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        firstName = (EditText) findViewById(R.id.inputFirstName);
        lastName = (EditText) findViewById(R.id.inputLastName);
        phone = (EditText) findViewById(R.id.inputPhone);

        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        Button btnReturn = (Button) findViewById(R.id.btnReturn);

        List<Contact> myContacts = dbHelper.retrieveAllContacts(db);
        String id = getIntent().getExtras().getString("id");
        Contact myContact = new Contact();
        for (Contact c : myContacts){
            if(c.get_ID() == Long.parseLong(id)){
                myContact = c;
                break;
            }
        }
        firstName.setText(myContact.getFirst());
        lastName.setText(myContact.getLast());
        phone.setText(myContact.getPhone());


        Contact finalMyContact = myContact;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact();
                newContact.set_ID(finalMyContact.get_ID());
                newContact.first = firstName.getText().toString();
                newContact.last = lastName.getText().toString();
                newContact.phone = phone.getText().toString();
                dbHelper.editContact(db, newContact);
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private void back() {
        Intent myIntent = new Intent(this, KontaktoversiktActivity.class);
        startActivity(myIntent);
    }
}
