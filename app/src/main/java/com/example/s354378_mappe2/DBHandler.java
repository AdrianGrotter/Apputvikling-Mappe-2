package com.example.s354378_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_CONTACTS = "Contacts";
    static String TABLE_APPOINTMENTS = "Appointments";
    static String KEY_NAME = "Name";
    static String KEY_TIME = "Time";
    static String KEY_PARTICIPANTS = "Participants";
    static String KEY_ID = "_ID";
    static String KEY_FIRST = "FirstName";
    static String KEY_LAST = "LastName";
    static String KEY_PHONE = "Phone";
    static int DATABASE_VERSION = 3;
    static String DATABASE_NAME = "Contacts";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST + " TEXT," + KEY_LAST + " TEXT," + KEY_PHONE + " TEXT" + ")";
        String CREATE_TABLE_APPOINTMENT = "CREATE TABLE " + TABLE_APPOINTMENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_TIME + " TEXT," + KEY_PARTICIPANTS + " TEXT" + ")";
        Log.d("SQL", CREATE_TABLE);
        Log.d("SQL", CREATE_TABLE_APPOINTMENT);
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_APPOINTMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(SQLiteDatabase db, Contact contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, contact.getFirst());
        values.put(KEY_LAST, contact.getLast());
        values.put(KEY_PHONE, contact.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
    }

    public List<Appointment> retrieveAllAppointments(SQLiteDatabase db){
        List<Appointment> appointmentList = new ArrayList<Appointment>();
        String sql = "SELECT * FROM "+TABLE_APPOINTMENTS;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                Appointment appointment = new Appointment();
                appointment.setName(cursor.getString(0));
                appointment.setTime(cursor.getLong(1));
                appointment.setParticipants(cursor.getString(2));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return appointmentList;
    }

    public List<Contact> retrieveAllContacts(SQLiteDatabase db){
        List<Contact> contactList = new ArrayList<Contact>();
        String sql = "SELECT * FROM "+TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.set_ID(cursor.getLong(0));
                contact.setFirst(cursor.getString(1));
                contact.setLast(cursor.getString(2));
                contact.setPhone(cursor.getString(3));
                contactList.add(contact);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return contactList;
    }

}
