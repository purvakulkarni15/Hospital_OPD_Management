package com.mycompany.opd_management_system;

public class Doctor {
    private int doctorId;
    private int hospitalId;
    private String name;
    private String speciality;
    private int slotTimeInMin;
    private String startTime;
    private String endTime;
    private int capacity;

    public Doctor(int doctorId, int hospitalId, String name, String speciality, String startTime, String endTime, int slotTimeInMin, int capacity){
        this.doctorId = doctorId;
        this.hospitalId = hospitalId;
        this.name = name;
        this.speciality = speciality;
        this.startTime = startTime;
        this.endTime = endTime;
        this.slotTimeInMin = slotTimeInMin;
        this.capacity = capacity;
    }

    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int id) {
        doctorId = id;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }


    public int getSlotTimeInMin() {
        return slotTimeInMin;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getCapacity() {
        return capacity;
    }

}
