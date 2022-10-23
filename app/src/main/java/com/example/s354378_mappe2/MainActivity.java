package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    DBHandler dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRegistrer = (Button) findViewById(R.id.btnCreateContact);
        Button btnShowContacts = (Button) findViewById(R.id.btnShowContactsPage);
        Button btnShowAppointments = (Button) findViewById(R.id.btnShowAppointmentsPage);
        Button btnCreateAppointment = (Button) findViewById(R.id.btnCreateAppointment);

        SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("standardMessage", "No message added");
        editor.putString("smsTime", "0500");
        editor.apply();

        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();
        startService(new View(this));


        btnRegistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityAddContacts();
            }
        });
        btnShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityKontaktoversikt();
            }
        });
        btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityAddAppointment();
            }
        });

        btnShowAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityAvtaleoversikt();
            }
        });
    }
    /*private void sendSMSMessages(){
        List<Appointment> myAppointments = dbHelper.retrieveAllAppointments(db);
        List<Contact> myContacts = dbHelper.retrieveAllContacts(db);

        for (Appointment appointment : myAppointments){
            String phone;
            String message = appointment.getMessage();
            Contact c = new Contact();
            for(Contact contact : myContacts){
                if (contact.get_ID() == Long.parseLong(appointment.getParticipants())){
                    phone = contact.getPhone();
                    break;
                }
            }
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
                    ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
        }

    }*/

    public void startService (View v){
        Intent intent = new Intent();
        intent.setAction("com.example.service.MITTSIGNAL");
        sendBroadcast(intent);
    }

    private void activityAddContacts() {
        Intent myIntent = new Intent(this, CreateContactActivity.class);
        startActivity(myIntent);
    }
    private void activityKontaktoversikt() {
        Intent myIntent = new Intent(this, KontaktoversiktActivity.class);
        startActivity(myIntent);
    }
    private void activityAvtaleoversikt() {
        Intent myIntent = new Intent(this, AvtaleoversiktActivity.class);
        startActivity(myIntent);
    }
    private void activityAddAppointment() {
        Intent myIntent = new Intent(this, CreateAppointmentActivity.class);
        startActivity(myIntent);
    }
}