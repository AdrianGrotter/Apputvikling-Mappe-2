package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


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
        editor.commit();

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