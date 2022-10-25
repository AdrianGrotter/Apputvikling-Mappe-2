package com.example.s354378_mappe2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class KontaktoversiktActivity extends AppCompatActivity {

    DBHandler dbHelper;
    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontaktoversikt);

        Button btnMain = findViewById(R.id.btnMain);

        dbHelper = new DBHandler(this);
        db = dbHelper.getWritableDatabase();

        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        List<Contact> contactList = dbHelper.retrieveAllContacts(db);
        ContactsAdapter adapter = new ContactsAdapter(contactList);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        btnMain.setOnClickListener(view -> mainActivity());

    }

    private void mainActivity() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
