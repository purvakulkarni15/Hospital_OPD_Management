package com.mycompany.opd_management_system;

public class Patient {
    private static int patientId;
    private static String name;
    private static int age;
    private static String gender;
    private static String illness;
    private static String emailId;
    private static String contactNumber;
    private static String isCovidSuspect;

    public Patient(int patientId, String name, int age, String gender, String illness, String emailId, String contactNumber) {
        this.patientId = patientId;
        this.name = name;
        this.age  = age;
        this.gender = gender;
        this.illness = illness;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
    }

    public static int getPatientId() {
        return patientId;
    }

    public static void setPatientId(int id) {
        patientId = id;
    }

    public static String getName() {
        return name;
    }

    public static int getAge() {
        return age;
    }

    public static String getGender() {
        return gender;
    }

    public static String getIllness() {
        return illness;
    }

    public static String getEMailId() {
        return emailId;
    }

    public static String getContactNumber() {
        return contactNumber;
    }

    public static String getIsCovidSuspect() {
        return isCovidSuspect;
    }

}
