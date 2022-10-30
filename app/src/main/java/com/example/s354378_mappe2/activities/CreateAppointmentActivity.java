package com.example.s354378_mappe2.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.s354378_mappe2.models.Appointment;
import com.example.s354378_mappe2.models.Contact;
import com.example.s354378_mappe2.DBHandler;
import com.example.s354378_mappe2.R;
import com.example.s354378_mappe2.Utilities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateAppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name;
    EditText time;
    EditText location;
    EditText message;
    Spinner participants;
    Button dateButton;


    DBHandler dbHelper;
    SQLiteDatabase db;
    private DatePickerDialog datePickerDialog;

    //selectedIndex lagrer hvilken kontakt som ble valgt
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment);
        initDatePicker();

        Button btnMain = (Button) findViewById(R.id.btnMain);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        dateButton = (Button) findViewById(R.id.datePickerButton);
        dateButton.setText(Utilities.getTodaysDate());

        name = (EditText) findViewById(R.id.inputName);
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

                //Bygger Appointment-objekt med data fra input-boksene
                Appointment newApmnt = new Appointment();
                newApmnt.setName(name.getText().toString());
                newApmnt.setDate(dateButton.getText().toString());
                newApmnt.setTime(time.getText().toString());
                newApmnt.setLocation(location.getText().toString());
                newApmnt.setParticipants(Long.toString(contactList.get(selectedIndex).get_ID()));
                newApmnt.setMessage(message.getText().toString());

                //Setter egen melding fra SharedPreferences om message er tom
                if(message.getText().toString().equals("")){
                    SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
                    newApmnt.setMessage(sp.getString("standardMessage", ""));
                }
                name.setText("");
                dateButton.setText(Utilities.getTodaysDate());
                time.setText("");
                location.setText("");
                message.setText("");

                Toast.makeText(getApplicationContext(), "Avtalen ble lagret!", Toast.LENGTH_SHORT).show();
                dbHelper.addAppointment(db, newApmnt);
            }
        });

    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                dateButton.setText(Utilities.makeDateString(day, month + 1, year));
            }
        };

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_POSITIVE;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
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
