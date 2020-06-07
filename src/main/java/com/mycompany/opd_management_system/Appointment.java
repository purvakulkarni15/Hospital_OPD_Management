package com.mycompany.opd_management_system;

import java.util.Date;

public class Appointment {

    private int appointmentId;
    private int hospitalId;
    private int patientId;
    private int doctorId;
    private String timeslot;
    private String status;
    private Boolean isCovidSuspect;
    private Boolean isAppointmentPushed;
    private Boolean isAppointmentCancelled;

    public Appointment(int appointmentId, int hospitalId, int patientId, int doctorId, String timeSlot){
        this.appointmentId = appointmentId;
        this.hospitalId = hospitalId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timeslot = timeSlot;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String slot) {
        timeslot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCovidSuspect() {
        return isCovidSuspect;
    }

    public void setCovidSuspect(Boolean covidSuspect) {
        isCovidSuspect = covidSuspect;
    }

    public Boolean getAppointmentPushed() {
        return isAppointmentPushed;
    }

    public void setAppointmentPushed(Boolean appointmentPushed) {
        isAppointmentPushed = appointmentPushed;
    }

    public Boolean getAppointmentCancelled() {
        return isAppointmentCancelled;
    }

    public void setAppointmentCancelled(Boolean appointmentCancelled) {
        isAppointmentCancelled = appointmentCancelled;
    }

}
