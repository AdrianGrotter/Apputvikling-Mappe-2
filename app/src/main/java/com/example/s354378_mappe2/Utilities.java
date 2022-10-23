package com.example.s354378_mappe2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;


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
        return  day + " " + getMonthFormat(month) + " " + year;
    }

    public static String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
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
                    a.setParticipants(c.first);
                    break;
                }
            }
        }
        return appointmentList;
    }
}
