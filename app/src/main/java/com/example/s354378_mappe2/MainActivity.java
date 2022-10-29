package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String CHANNEL_ID = "MinKanal";

    public static String PROVIDER = "com.example.contentprovidercontact" ;
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER + "/contact");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        Button btnRegistrer = findViewById(R.id.btnCreateContact);
        Button btnShowContacts = findViewById(R.id.btnShowContactsPage);
        Button btnShowAppointments = findViewById(R.id.btnShowAppointmentsPage);
        Button btnCreateAppointment = findViewById(R.id.btnCreateAppointment);
        Button btnToggleSMS = findViewById(R.id.toggleSMS);
        Button btnToggleService = findViewById(R.id.toggleService);

        SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("sjekkSMS", true);
        editor.putString("standardMessage", "No message added");
        editor.putString("smsTime", "0500");
        editor.putBoolean("sjekkNotifikasjon", true);
        editor.apply();

        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);
        startService(new View(this));

        btnRegistrer.setOnClickListener(view -> activityAddContacts());
        btnShowContacts.setOnClickListener(view -> activityKontaktoversikt());
        btnCreateAppointment.setOnClickListener(view -> activityAddAppointment());
        btnShowAppointments.setOnClickListener(view -> activityAvtaleoversikt());
    }

    public void toggleSMS(View v){
        SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
        boolean sjekkSMS = sp.getBoolean("sjekkSMS", false);
        SharedPreferences.Editor editor = sp.edit();
        Button btnToggleSMS = findViewById(R.id.toggleSMS);
        if(sjekkSMS){
            editor.putBoolean("sjekkSMS", false);
            Toast.makeText(MainActivity.this, "SMS ble deaktivert!", Toast.LENGTH_SHORT).show();
            String newText = "Skru sms på";
            btnToggleSMS.setText(newText);
        }else{
            editor.putBoolean("sjekkSMS", true);
            Toast.makeText(MainActivity.this, "SMS ble aktivert!", Toast.LENGTH_SHORT).show();
            String newText = "Skru sms av";
            btnToggleSMS.setText(newText);
        }
        editor.apply();
    }

    public void startService (View v){
        Intent intent = new Intent();
        intent.setAction("com.example.service.MITTSIGNAL");
        sendBroadcast(intent);
    }

    private void createNotificationChannel(){
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    public void stoppPeriodisk(View v){
        SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
        Button btnToggleService = findViewById(R.id.toggleService);
        SharedPreferences.Editor editor = sp.edit();
        if(!sp.getBoolean("sjekkNotifikasjon", false)){
            editor.putBoolean("sjekkNotifikasjon", true);
            startService(v);
            Toast.makeText(this, "Notifikasjoner er aktivert!", Toast.LENGTH_SHORT).show();
            String newText = "Skru av notifikasjoner";
            btnToggleService.setText(newText);
            return;
        }
        if(sp.getBoolean("sjekkSMS", false)){
            toggleSMS(v);
        }
        editor.putBoolean("sjekkNotifikasjon", false);
        editor.apply();
        Intent i = new Intent(this, MinSendService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(alarm != null){
            alarm.cancel(pintent);
        }
        String newText = "Skru på notifikasjoner";
        btnToggleService.setText(newText);
        Toast.makeText(this, "Notifikasjoner og SMS er deaktivert!", Toast.LENGTH_SHORT).show();
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