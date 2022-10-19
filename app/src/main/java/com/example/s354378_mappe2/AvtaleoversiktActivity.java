package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvtaleoversiktActivity extends AppCompatActivity {
    TextView appointmentOutput;

    DBHandler dbHelper;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtaleoversikt);

        Button btnMain = (Button) findViewById(R.id.btnMain);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        RecyclerView rvAppointments = (RecyclerView) findViewById(R.id.rvAppointments);
        List<Appointment> appointmentList = dbHelper.retrieveAllAppointments(db);
        List<Contact> contactList = dbHelper.retrieveAllContacts(db);
        for(Appointment a : appointmentList){
            for(Contact c : contactList){
                if(Long.parseLong(a.getParticipants()) == c.get_ID()){
                    a.setParticipants(c.first);
                    break;
                }
            }
        }

        AppointmentAdapter adapter = new AppointmentAdapter(appointmentList);
        rvAppointments.setAdapter(adapter);
        rvAppointments.setLayoutManager(new LinearLayoutManager(this));

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMain();
            }
        });

        /*String output = "";
        List<Appointment> apmntList = dbHelper.retrieveAllAppointments(db);
        List<Contact> contactList = dbHelper.retrieveAllContacts(db);
        for (Appointment myApmnt : apmntList){
            Contact myContact = contactList.get(0);
            for (Contact c : contactList){
                if(c.get_ID() == Long.parseLong(myApmnt.getParticipants())){
                    myContact = c;
                    break;
                }
            }
       }*/
    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
