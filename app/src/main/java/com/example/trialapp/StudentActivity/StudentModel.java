package com.example.trialapp.StudentActivity;

public class StudentModel {
    String firstName, lastName,  myEmail, myContact, myStandard;
    public StudentModel()
    {

    }
    public StudentModel(String firstName, String lastName, String myEmail, String myContact, String myStandard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.myEmail = myEmail;
        this.myContact = myContact;
        this.myStandard = myStandard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getMyContact() {
        return myContact;
    }

    public void setMyContact(String myContact) {
        this.myContact = myContact;
    }

    public String getMyStandard() {
        return myStandard;
    }

    public void setMyStandard(String myStandard) {
        this.myStandard = myStandard;
    }
}
