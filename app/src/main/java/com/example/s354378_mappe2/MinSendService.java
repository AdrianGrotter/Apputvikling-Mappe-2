package com.example.s354378_mappe2;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.sql.SQLOutput;
import java.util.List;

public class MinSendService extends Service {
    DBHandler dbHelper;
    SQLiteDatabase db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        List<Appointment> myAppointments = dbHelper.retrieveAllAppointments(db);
        StringBuilder test = new StringBuilder();
        int myCounter = 0;
        for (Appointment a : myAppointments){
            if (a.getDate().equals(Utilities.getTodaysDate())){
                myCounter++;
                for(Contact c : dbHelper.retrieveAllContacts(db)){
                    if(c.get_ID() == Long.parseLong(a.getParticipants())){
                       sendSMSMessage(c.getPhone(), a.getMessage());
                       break;
                    }
                }
            }
        }
        test.append("You have ");
        test.append(myCounter);
        test.append(" appointments today");

        //Toast.makeText(getApplicationContext(), test.toString(), Toast.LENGTH_SHORT).show();
        NotificationManager  notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, AvtaleoversiktActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(this, 0, i, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this, "MinKanal")
                .setContentTitle("Du har avtaler i dag!")
                .setContentText("Åpne for å se avtalene dine")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pintent).build();

        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(88, notifikasjon);


        return super.onStartCommand(intent, flags, startId);
    }

    public void sendSMSMessage(String phoneNo, String message){
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        System.out.println(MY_PERMISSIONS_REQUEST_SEND_SMS + "    "+ MY_PHONE_STATE_PERMISSION);

        if(MY_PERMISSIONS_REQUEST_SEND_SMS >= 0 /*&& MY_PHONE_STATE_PERMISSION >= 0*/){
            SmsManager smsMan = SmsManager.getDefault();
            smsMan.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(this, "Har sendt sms til "+phoneNo, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "The app doesn't have SMS permissions", Toast.LENGTH_SHORT).show();
        }
    }
}

