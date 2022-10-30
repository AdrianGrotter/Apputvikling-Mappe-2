package com.example.s354378_mappe2.models;

public class Contact {
    long _ID;
    String first;
    String last;
    String phone;

    public Contact() {
    }

    public Contact(String first, String last, String phone) {
        this.first = first;
        this.last = last;
        this.phone = phone;
    }

    public Contact(long _ID, String first, String last, String phone) {
        this._ID = _ID;
        this.first = first;
        this.last = last;
        this.phone = phone;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
