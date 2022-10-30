package com.example.s354378_mappe2.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class MinPeriodisk extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
   @Override
   public int onStartCommand(Intent intent, int flags, int startId){
        java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, MinSendService.class);
       PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
       AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

       //Sjekker avtaler en gang om dagen. smsTime er klokken 07:00
       SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
       alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, sp.getInt("smsTime", 0), AlarmManager.INTERVAL_DAY, pintent);
       return super.onStartCommand(intent, flags, startId);

   }
}
