package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText phone;
    TextView contactOutput;

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

        contactOutput = (TextView) findViewById(R.id.contactOutput);


        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        Button btnShowContacts = (Button) findViewById(R.id.btnShowContacts);


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

        btnShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output = "";
                List<Contact> contactList= dbHelper.retrieveAllContacts(db);
                for (Contact myContact : contactList){
                    /*
                    output += "Id: "+ myContact.get_ID() +
                            ", Fornavn: "+myContact.getFirst() +
                            ", Etternavn: "+ myContact.getLast() +
                            ", Telefonnummer: "+ myContact.getPhone() + "\n";
                    */
                    output += myContact.get_ID() + ", "+ myContact.getFirst() + " " + myContact.getLast() + ", " + myContact.getPhone() + "\n";
                }
                contactOutput.setText(output);
            }
        });
    }

    @Override
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }
}