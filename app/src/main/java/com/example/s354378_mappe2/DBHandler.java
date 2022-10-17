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
    static String KEY_ID = "_ID";
    static String KEY_FIRST = "FirstName";
    static String KEY_LAST = "LastName";
    static String KEY_PHONE = "Phone";

    static String TABLE_APPOINTMENTS = "Appointments";
    static String KEY_APPOINTMENTS_ID = "_ID";
    static String KEY_NAME = "Name";
    static String KEY_DATE = "Name";
    static String KEY_TIME = "Time";
    static String KEY_LOCATION = "Name";
    static String KEY_PARTICIPANTS = "Participants";
    static int DATABASE_VERSION = 3;
    static String DATABASE_NAME = "Appointments";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    KEY_FIRST + " TEXT," +
                    KEY_LAST + " TEXT," +
                    KEY_PHONE + " TEXT" +
                ")";
        String CREATE_TABLE_APPOINTMENT = "CREATE TABLE " + TABLE_APPOINTMENTS +
                "(" +
                    KEY_APPOINTMENTS_ID + " INTEGER PRIMARY KEY," +
                    KEY_NAME + " TEXT," +
                    KEY_DATE + " TEXT," +
                    KEY_TIME + " TEXT," +
                    KEY_LOCATION + " TEXT," +
                    KEY_PARTICIPANTS + " TEXT" +
                ")";
        Log.d("SQL", CREATE_TABLE);
        Log.d("SQL", CREATE_TABLE_APPOINTMENT);
        System.out.println("Creating table "+TABLE_APPOINTMENTS);
        db.execSQL(CREATE_TABLE_APPOINTMENT);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    public void addContact(SQLiteDatabase db, Contact contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, contact.getFirst());
        values.put(KEY_LAST, contact.getLast());
        values.put(KEY_PHONE, contact.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
    }

    public void editContact(SQLiteDatabase db, Contact contact){
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, contact.getFirst());
        values.put(KEY_LAST, contact.getLast());
        values.put(KEY_PHONE, contact.getPhone());
        db.update(TABLE_CONTACTS, values, "_id = ?", new String[]{String.valueOf(contact.get_ID())});
    }
    public void deleteContact(SQLiteDatabase db, Contact contact){
        db.delete(TABLE_CONTACTS, "_id = ?", new String[]{String.valueOf(contact.get_ID())});
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

    public void addAppointment(SQLiteDatabase db, Appointment apmnt){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, apmnt.getName());
        values.put(KEY_TIME, apmnt.getTime());
        values.put(KEY_PARTICIPANTS, apmnt.getParticipants());
        db.insert(TABLE_APPOINTMENTS, null, values);
    }

    public void deleteAppointment(SQLiteDatabase db, Appointment apmnt){
        db.delete(TABLE_APPOINTMENTS, "_id=?", new String[]{String.valueOf(apmnt.get_ID())});
    }

    public List<Appointment> retrieveAllAppointments(SQLiteDatabase db){
        List<Appointment> apmntList = new ArrayList<Appointment>();
        String sql = "SELECT * FROM "+TABLE_APPOINTMENTS;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                Appointment apmnt = new Appointment();
                apmnt.set_ID(cursor.getLong(0));
                apmnt.setName(cursor.getString(1));
                apmnt.setTime(cursor.getString(2));
                apmnt.setParticipants(cursor.getString(3));
                apmntList.add(apmnt);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return apmntList;
    }

}
