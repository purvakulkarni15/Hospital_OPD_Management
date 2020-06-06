package com.mycompany.opd_management_system;

public class Hospital {
    private static int hospitalId;
    private static String name;

    public Hospital(int hospitalId, String name){
        this.hospitalId = hospitalId;
        this.name = name;
    }

    public static int getHospitalId() {
        return hospitalId;
    }

    public static void setHospitalId(int id) {
        hospitalId = id;
    }

    public static String getName() {
        return name;
    }

}
