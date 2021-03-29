package com.bpal.feesmanagement.DBModel;

public class Student {

    private String uid, firstname, lastname, dob, phone, key, password, stream, course, year, total, paid, remaining, date;

    public Student(){}

    public Student(String uid, String firstname, String lastname, String dob, String phone, String key, String password,
                   String stream, String course, String year, String total, String paid, String remaining, String date){
        this.uid=uid;
        this.firstname=firstname;
        this.lastname=lastname;
        this.dob=dob;
        this.phone=phone;
        this.key=key;
        this.password=password;

        this.stream=stream;
        this.course=course;
        this.year=year;
        this.total=total;
        this.paid=paid;
        this.remaining=remaining;
        this.date=date;

    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
