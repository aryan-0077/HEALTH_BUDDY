package com.example.healthcare;

public class UserData {

    private String Name;
    private String Email;
    private String Contact;
    private String Address;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    private String  BloodGroup, Password;
    private int Gender, Division;

    public UserData() {

    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public int getDivision() {
        return Division;
    }

    public void setDivision(int division) {
        this.Division = division;
    }

    public String getName() {
        return Name;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.BloodGroup = bloodGroup;
    }

    public String getEmail() {
        return Email;
    }

    public int getGender() {
        return Gender;
    }

    public void setName(String name) { this.Name = name; }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setGender(int gender) {
        this.Gender = gender;
    }


}