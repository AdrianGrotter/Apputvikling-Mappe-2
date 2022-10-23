package com.example.s354378_mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MinBroadcastReceiver extends BroadcastReceiver {
    public MinBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, SettPeriodiskService.class);
        context.startService(i);
    }
}
