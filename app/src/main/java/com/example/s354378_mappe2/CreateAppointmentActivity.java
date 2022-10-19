package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.stream.Collectors;

public class CreateAppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView feedback;
    EditText name;
    EditText date;
    EditText time;
    EditText location;
    Spinner participants;
    EditText message;


    DBHandler dbHelper;
    SQLiteDatabase db;

    int selectedIndex;

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
        participants = (Spinner) findViewById(R.id.inputParticipants);
        message = (EditText) findViewById(R.id.inputMessage);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        List<Contact> contactList = dbHelper.retrieveAllContacts(db);

        List<String> ddlItems = contactList.stream().map(Contact::getFirst).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateAppointmentActivity.this, R.layout.spinner_item, ddlItems);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        participants.setAdapter(adapter);
        participants.setOnItemSelectedListener(this);



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
                newApmnt.participants = contactList.get(selectedIndex).get_ID();
                newApmnt.message = message.getText().toString();
                System.out.println(newApmnt.name + " " + newApmnt.date + " " + newApmnt.time + " " + newApmnt.location + " " + newApmnt.participants + " " + newApmnt.message);

                dbHelper.addAppointment(db, newApmnt);
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        selectedIndex = pos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }


}
