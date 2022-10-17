package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAppointmentActivity extends AppCompatActivity {
    TextView feedback;
    EditText name;
    EditText date;
    EditText time;
    EditText location;
    EditText participants;
    EditText message;


    DBHandler dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment);

        Button btnMain = (Button) findViewById(R.id.btnMain);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);

        name = (EditText) findViewById(R.id.inputName);
        date = (EditText) findViewById(R.id.inputDate);
        time = (EditText) findViewById(R.id.inputTime);
        location = (EditText) findViewById(R.id.inputLocation);
        participants = (EditText) findViewById(R.id.inputParticipants);
        message = (EditText) findViewById(R.id.inputMessage);

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
                newApmnt.name = name.getText().toString();
                newApmnt.date = date.getText().toString();
                newApmnt.time = time.getText().toString();
                newApmnt.location = location.getText().toString();
                newApmnt.participants = participants.getText().toString();
                newApmnt.message = message.getText().toString();
                System.out.println(newApmnt.name + " " + newApmnt.date + " " + newApmnt.time + " " + newApmnt.location + " " + newApmnt.participants + " " + newApmnt.message);

                dbHelper.addAppointment(db, newApmnt);
            }
        });

    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
