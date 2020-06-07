package com.mycompany.opd_management_system;

public class Hospital {
    private int hospitalId;
    private String name;

    public Hospital(int hospitalId, String name){
        this.hospitalId = hospitalId;
        this.name = name;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int id) {
        hospitalId = id;
    }

    public String getName() {
        return name;
    }

}
