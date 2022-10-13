package com.example.s354378_mappe2;

public class Appointment {
    long _ID;
    String name;
    String time;
    String participants;

    public Appointment() {
    }

    public Appointment(String name, String time, String participants) {
        this.name = name;
        this.time = time;
        this.participants = participants;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
