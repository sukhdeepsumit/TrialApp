package com.example.trialapp.AllTeacherRecord;

public class ModelTeacherRecord {
    String name, email, phone, subject, standard;

    public ModelTeacherRecord() {
    }

    public ModelTeacherRecord(String name, String email, String phone, String subject, String standard) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.standard = standard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
