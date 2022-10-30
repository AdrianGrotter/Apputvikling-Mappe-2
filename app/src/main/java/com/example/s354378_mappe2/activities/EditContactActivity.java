package com.example.s354378_mappe2.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.s354378_mappe2.models.Contact;
import com.example.s354378_mappe2.DBHandler;
import com.example.s354378_mappe2.R;

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

        firstName = findViewById(R.id.inputFirstName);
        lastName = findViewById(R.id.inputLastName);
        phone = findViewById(R.id.inputPhone);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnReturn = findViewById(R.id.btnReturn);

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
        btnSubmit.setOnClickListener(view -> {
            Contact newContact = new Contact();
            newContact.set_ID(finalMyContact.get_ID());
            newContact.setFirst(firstName.getText().toString());
            newContact.setLast(lastName.getText().toString());
            newContact.setPhone(phone.getText().toString());
            dbHelper.editContact(db, newContact);

            Toast.makeText(getApplicationContext(), "Kontakten ble oppdatert!", Toast.LENGTH_SHORT).show();
            back();
        });
        btnReturn.setOnClickListener(view -> back());
    }
    private void back() {
        Intent myIntent = new Intent(this, KontaktoversiktActivity.class);
        startActivity(myIntent);
    }
}
