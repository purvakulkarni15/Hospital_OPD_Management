package com.mycompany.opd_management_system;

public class Admin {
    private int adminId;
    private int hospitalId;
    private String emailId;
    private String password;

    public Admin(int adminId, int hospitalId, String emailId, String password){
        this.adminId = adminId;
        this.hospitalId = hospitalId;
        this.emailId = emailId;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
