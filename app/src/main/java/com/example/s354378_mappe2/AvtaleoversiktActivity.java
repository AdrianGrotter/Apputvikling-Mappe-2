package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class AvtaleoversiktActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtaleoversikt);

        Button btnMain = findViewById(R.id.btnMain);

        RecyclerView rvAppointments = findViewById(R.id.rvAppointments);
        List<Appointment> appointmentList = Utilities.buildAppointmentList(getApplicationContext());

        AppointmentAdapter adapter = new AppointmentAdapter(appointmentList);
        rvAppointments.setAdapter(adapter);
        rvAppointments.setLayoutManager(new LinearLayoutManager(this));

        btnMain.setOnClickListener(view -> activityMain());
    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
    public void tilCreateAppointment(View view) {
        Intent myIntent = new Intent(this, CreateAppointmentActivity.class);
        startActivity(myIntent);
    }
}
