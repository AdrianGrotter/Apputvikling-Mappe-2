package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button btnMain = (Button) findViewById(R.id.btnMain);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);

        input_name = (EditText) findViewById(R.id.inputName);
        time = (EditText) findViewById(R.id.inputTime);
        participants = (EditText) findViewById(R.id.inputParticipants);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();


        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMain();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment newApmnt = new Appointment();
                newApmnt.name = input_name.getText().toString();
                newApmnt.time = time.getText().toString();
                newApmnt.participants = participants.getText().toString();

                System.out.println(newApmnt.name + " " + newApmnt.time + " " + newApmnt.participants);

                dbHelper.addAppointment(db, newApmnt);
            }
        });

    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
