package com.mycompany.opd_management_system;

public class Doctor {
    private static int doctorId;
    private static int hospitalId;
    private static String name;
    private static String speciality;
    private static int slotTimeInMin;
    private static String startTime;
    private static String endTime;
    private static int capacity;
    private static int currentCapacity;

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

    public static int getDoctorId() {
        return doctorId;
    }
    public static void setDoctorId(int id) {
        doctorId = id;
    }

    public static int getHospitalId() {
        return hospitalId;
    }

    public static String getName() {
        return name;
    }

    public static String getSpeciality() {
        return speciality;
    }


    public static int getSlotTimeInMin() {
        return slotTimeInMin;
    }


    public static String getStartTime() {
        return startTime;
    }

    public static String getEndTime() {
        return endTime;
    }

    public static int getCapacity() {
        return capacity;
    }

    public static int getCurrentCapacity() {
        return currentCapacity;
    }

    public static void setCurrentCapacity(int currentCapacity) {
        Doctor.currentCapacity = currentCapacity;
    }

}
