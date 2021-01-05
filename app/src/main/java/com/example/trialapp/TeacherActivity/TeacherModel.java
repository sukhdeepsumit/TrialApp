package com.example.trialapp.TeacherActivity;

public class TeacherModel {

    String firstName, lastName,  myEmail, myContact, mySubject, myStandard;

    public TeacherModel() {
    }

    public TeacherModel(String firstName, String lastName, String myEmail, String myContact, String mySubject, String myStandard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.myEmail = myEmail;
        this.myContact = myContact;
        this.mySubject = mySubject;
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

    public String getMySubject() {
        return mySubject;
    }

    public void setMySubject(String mySubject) {
        this.mySubject = mySubject;
    }

    public String getMyStandard() {
        return myStandard;
    }

    public void setMyStandard(String myStandard) {
        this.myStandard = myStandard;
    }
}
