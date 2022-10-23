package com.example.s354378_mappe2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.Provider;

public class MinService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }
}

