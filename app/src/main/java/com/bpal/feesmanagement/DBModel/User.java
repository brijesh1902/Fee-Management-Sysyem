package com.bpal.feesmanagement.DBModel;

public class User {

    private String firstName, lastname, email, password, phone ;

    public User() {}


    public User(String firstName, String lastname, String email, String pass, String phone) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = pass;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
