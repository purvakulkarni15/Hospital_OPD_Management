package com.mycompany.opd_management_system;

public class Emergency {

    int emergencyId;
    int hospitalId;
    int doctorId;
    String from;
    String to;
    String date;

    public Emergency(int doctorId, int hospitalId, String from, String to, String date){
        this.doctorId = doctorId;
        this.hospitalId = hospitalId;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public int getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(int emergencyId) {
        this.emergencyId = emergencyId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
