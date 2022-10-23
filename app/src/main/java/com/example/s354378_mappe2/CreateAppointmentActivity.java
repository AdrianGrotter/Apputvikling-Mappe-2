package com.example.s354378_mappe2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.stream.Collectors;

public class CreateAppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name;
    EditText time;
    EditText location;
    Spinner participants;
    EditText message;


    DBHandler dbHelper;
    SQLiteDatabase db;
    private DatePickerDialog datePickerDialog;
    Button dateButton;

    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment);
        initDatePicker();

        Button btnMain = (Button) findViewById(R.id.btnMain);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        dateButton = (Button) findViewById(R.id.datePickerButton);
        dateButton.setText(TimeData.getTodaysDate());

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
                Appointment newApmnt = new Appointment();
                newApmnt.name = name.getText().toString();
                newApmnt.date = dateButton.getText().toString();
                newApmnt.time = time.getText().toString();
                newApmnt.location = location.getText().toString();
                newApmnt.participants = Long.toString(contactList.get(selectedIndex).get_ID());
                newApmnt.message = message.getText().toString();

                if(message.getText().toString().equals("")){
                    SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
                    newApmnt.message = sp.getString("standardMessage", "");
                }

                System.out.println(newApmnt.name + " " + newApmnt.date + " " + newApmnt.time + " " + newApmnt.location + " " + newApmnt.participants + " " + newApmnt.message);
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
                month = month + 1;
                String date = TimeData.makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_POSITIVE;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

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
