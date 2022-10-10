package com.example.s354378_mappe2;

public class Appointment {
    String name;
    long time;
    String participants;

    public Appointment() {
    }

    public Appointment(String name, long time, String participants) {
        this.name = name;
        this.time = time;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
