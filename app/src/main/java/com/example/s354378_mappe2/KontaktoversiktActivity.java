package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class KontaktoversiktActivity extends AppCompatActivity {
    TextView contactOutput;

    DBHandler dbHelper;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontaktoversikt);

        contactOutput = (TextView) findViewById(R.id.contactOutput);

        Button btnMain = (Button) findViewById(R.id.btnMain);
        Button btnShowContacts = (Button) findViewById(R.id.btnShowContacts);
        Button btnShowAppointments = (Button) findViewById(R.id.btnShowAppointments);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMain();
            }
        });

        btnShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output = "";
                List<Contact> contactList = dbHelper.retrieveAllContacts(db);
                for (Contact myContact : contactList){
                    output += myContact.get_ID() + ", "+ myContact.getFirst() + " " + myContact.getLast() + ", " + myContact.getPhone() + "\n";
                }
                contactOutput.setText(output);
            }
        });

        btnShowAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output = "";
                List<Appointment> apmntList = dbHelper.retrieveAllAppointments(db);
                List<Contact> contactList = dbHelper.retrieveAllContacts(db);
                for (Appointment myApmnt : apmntList){
                    Contact myContact = contactList.get(0);
                    for (Contact c : contactList){
                        if(c.get_ID() == myApmnt.getParticipants()){
                            myContact = c;
                            break;
                        }
                    }
                    output += myApmnt.getName() + ", " + myApmnt.getDate() + ", " + myApmnt.getTime() + " " + myApmnt.getLocation() + ", " + myContact.getFirst() + ", " + myApmnt.getMessage() + "\n";
                }
                contactOutput.setText(output);
            }
        });
    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
