package com.example.s354378_mappe2;

public class Contact {
    long ID;
    String first;
    String last;
    String phone;

    public Contact() {
    }

    public Contact(long ID, String first, String last, String phone) {
        this.ID = ID;
        this.first = first;
        this.last = last;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
