package com.example.s354378_mappe2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;


import com.example.s354378_mappe2.models.Appointment;
import com.example.s354378_mappe2.models.Contact;

import java.util.List;

public class Utilities {


    public static String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public static String makeDateString(int day, int month, int year)
    {
        String formattedMonth = "";
        switch (month){
            case 1:
                formattedMonth = "JAN";
                break;
            case 2:
                formattedMonth = "FEB";
                break;
            case 3:
                formattedMonth = "MAR";
                break;
            case 4:
                formattedMonth = "APR";
                break;
            case 5:
                formattedMonth = "MAY";
                break;
            case 6:
                formattedMonth = "JUN";
                break;
            case 7:
                formattedMonth = "JUL";
                break;
            case 8:
                formattedMonth = "AUG";
                break;
            case 9:
                formattedMonth = "SEP";
                break;
            case 10:
                formattedMonth = "OCT";
                break;
            case 11:
                formattedMonth = "NOV";
                break;
            case 12:
                formattedMonth = "DEC";
                break;
            default:
                formattedMonth = "JAN";
        }

        return day + " " + formattedMonth + " " + year;

    }

    public static List<Appointment> buildAppointmentList(Context context){

        DBHandler dbHelper;
        SQLiteDatabase db;
        dbHelper = new DBHandler(context);
        db = dbHelper.getWritableDatabase();

        List<Appointment> appointmentList = dbHelper.retrieveAllAppointments(db);
        List<Contact> contactList = dbHelper.retrieveAllContacts(db);

        for(Appointment a : appointmentList){
            for(Contact c : contactList){
                if(Long.parseLong(a.getParticipants()) == c.get_ID()){
                    a.setParticipants(c.getFirst());
                    break;
                }
            }
        }
        return appointmentList;
    }
}
