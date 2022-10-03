package com.example.s354378_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_CONTACTS = "Contacts";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Name";
    static String KEY_PHONE = "Phone";
    static int DATABASE_VERSION = 3;
    static String DATABASE_NAME = "Contacts";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_TABELL = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT" + ")";
        Log.d("SQL", LAG_TABELL);
        db.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(SQLiteDatabase db, Contact Contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Contact.getFirst());
        values.put(KEY_PHONE, Contact.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
    }
}
