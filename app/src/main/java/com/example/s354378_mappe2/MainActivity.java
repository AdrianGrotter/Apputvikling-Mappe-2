package com.example.s354378_mappe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHelper;
    SQLiteDatabase db;
    String CHANNEL_ID = "MinKanal";

    public static String PROVIDER = "com.example.contentprovidercontact" ;
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER + "/contact");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

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

        for (Contact c : dbHelper.retrieveAllContacts(db)) System.out.println(c.get_ID());


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
        Intent i = new Intent(this, MinSendService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(alarm != null){
            alarm.cancel(pintent);
        }
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