package com.example.s354378_mappe2;

public class Friend {
    long ID;
    String first;
    String last;

    public Friend() {
    }

    public Friend(long ID, String first, String last) {
        this.ID = ID;
        this.first = first;
        this.last = last;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
