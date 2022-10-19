package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class KontaktoversiktActivity extends AppCompatActivity {
    TextView contactOutput;

    DBHandler dbHelper;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontaktoversikt);

        contactOutput = (TextView) findViewById(R.id.contactOutput);

        Button btnMain = (Button) findViewById(R.id.btnMain);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMain();
            }
        });

        String output = "";
        List<Contact> contactList = dbHelper.retrieveAllContacts(db);
        for (Contact myContact : contactList){
            output += myContact.get_ID() + ", "+ myContact.getFirst() + " " + myContact.getLast() + ", " + myContact.getPhone() + "\n";
        }
        contactOutput.setText(output);

    }
    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
