package com.example.s354378_mappe2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAppointmentActivity extends AppCompatActivity {
    TextView feedback;
    EditText input_name;
    EditText time;
    EditText participants;


    DBHandler dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment);

    }
}
