package com.mycompany.opd_management_system;

public class Emergency {

    private int emergencyId;
    private int hospitalId;
    private int doctorId;
    private String doctorName;
    private String from;
    private String to;
    private String date;

    public Emergency(int doctorId, int hospitalId, String doctorName, String from, String to, String date){
        this.doctorId = doctorId;
        this.hospitalId = hospitalId;
        this.doctorName = doctorName;
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

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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
