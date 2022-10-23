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
            if (a.getDate().equals(getTodaysDate())){
                myCounter++;
            }
        }
        test.append(myCounter);
        /*int h = Integer.parseInt(myAppointments.get(0).getTime().substring(0,2));
        int m = Integer.parseInt(myAppointments.get(0).getTime().substring(3,5));
        System.out.println(h+""+m);*/

        Date date = new Date();

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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year)
    {
        return  day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
}

