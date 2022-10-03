package com.example.s354378_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_CONTACTS = "Kontakter";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Navn";
    static String KEY_PHONE = "Telefon";
    static int DATABASE_VERSION = 3;
    static String DATABASE_NAME = "Telefonkontakt";

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

    public void leggTilKontakt(SQLiteDatabase db, Kontakt kontakt) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.getFirst());
        values.put(KEY_PHONE, kontakt.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
    }
}
