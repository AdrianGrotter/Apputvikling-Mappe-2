package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AvtaleoversiktActivity extends AppCompatActivity {
    TextView appointmentOutput;

    DBHandler dbHelper;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtaleoversikt);

        appointmentOutput = (TextView) findViewById(R.id.appointmentOutput);

        Button btnMain = (Button) findViewById(R.id.btnMain);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMain();
            }
        });

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
        appointmentOutput.setText(output);
    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
