package com.example.s354378_mappe2.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s354378_mappe2.models.Contact;
import com.example.s354378_mappe2.DBHandler;
import com.example.s354378_mappe2.R;

import java.util.Objects;

public class CreateContactActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText phone;

    DBHandler dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact);

        Button btnMain = (Button) findViewById(R.id.btnMain);
        firstName = (EditText) findViewById(R.id.inputFirstName);
        lastName = (EditText) findViewById(R.id.inputLastName);
        phone = (EditText) findViewById(R.id.inputPhone);

        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);



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
                Contact newContact = new Contact();
                newContact.setFirst(firstName.getText().toString());
                newContact.setLast(lastName.getText().toString());
                newContact.setPhone(phone.getText().toString());

                firstName.setText("");
                lastName.setText("");
                phone.setText("");

                Toast.makeText(getApplicationContext(), "Kontakten ble lagret!", Toast.LENGTH_SHORT).show();

                dbHelper.addContact(db, newContact);
            }
        });
    }

    @Override
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }

    private void activityMain() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
