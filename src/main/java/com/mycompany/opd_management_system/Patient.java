package com.mycompany.opd_management_system;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String illness;
    private String emailId;
    private String contactNumber;

    public Patient(int patientId, String name, int age, String gender, String illness, String emailId, String contactNumber) {
        this.patientId = patientId;
        this.name = name;
        this.age  = age;
        this.gender = gender;
        this.illness = illness;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int id) {
        patientId = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getIllness() {
        return illness;
    }

    public String getEMailId() {
        return emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

}
