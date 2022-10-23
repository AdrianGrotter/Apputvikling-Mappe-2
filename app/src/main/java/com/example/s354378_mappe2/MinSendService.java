package com.example.s354378_mappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Date;
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
            if (a.getDate().equals(TimeData.getTodaysDate())){
                myCounter++;
            }
        }
        test.append("You have ");
        test.append(myCounter);
        test.append(" appointments today");

        Toast.makeText(getApplicationContext(), test.toString(), Toast.LENGTH_SHORT).show();
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
}

