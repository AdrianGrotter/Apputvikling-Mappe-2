package com.example.s354378_mappe2.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MinBroadcastReceiver extends BroadcastReceiver {
    public MinBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, MinPeriodisk.class);
        context.startService(i);
    }
}
