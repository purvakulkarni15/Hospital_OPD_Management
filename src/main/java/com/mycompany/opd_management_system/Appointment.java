package com.mycompany.opd_management_system;

import java.util.Date;

public class Appointment {
    private static int hospitalId;
    private static int patientId;
    private static int doctorId;
    private static String timeslot;
    private static String status;

    Appointment(int hospitalId, int patientId, int doctorId, String timeSlot){
        this.hospitalId = hospitalId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timeslot = timeSlot;
    }

    public static int getHospitalId() {
        return hospitalId;
    }

    public static int getPatientId() {
        return patientId;
    }

    public static int getDoctorId() {
        return doctorId;
    }

    public static String getTimeslot() {
        return timeslot;
    }

    public static void setTimeslot(String slot) {
        timeslot = slot;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Appointment.status = status;
    }
}
