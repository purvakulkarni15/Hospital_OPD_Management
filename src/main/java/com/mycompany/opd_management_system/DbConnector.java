package com.mycompany.opd_management_system;

import java.util.ArrayList;

public class DbConnector {

    private static ArrayList<Doctor> doctorTable;
    private static ArrayList<Hospital>hospitalTable;
    private static ArrayList<Patient>patientTable;
    private static ArrayList<Appointment>appointmentTable;
    private static ArrayList<Emergency>emergencyTable;

    public DbConnector(){
        doctorTable = new ArrayList<>();
        hospitalTable = new ArrayList<>();
        patientTable = new ArrayList<>();
        appointmentTable =  new ArrayList<>();
        emergencyTable = new ArrayList<>();
    }

    public static ArrayList<Doctor> getDoctorTable() {
        return doctorTable;
    }

    public static ArrayList<Hospital> getHospitalTable() {
        return hospitalTable;
    }

    public static ArrayList<Patient> getPatientTable() {
        return patientTable;
    }

    public static ArrayList<Appointment> getAppointmentTable() {
        return appointmentTable;
    }

    public static ArrayList<Emergency> getEmergencyTable() { return emergencyTable; }

    /*
    * Insert Queries
    */

    public static int addToHospitalTable(Hospital hospital){

        int hospitalId;
        if(hospitalTable.isEmpty()){
            hospitalId = 0;
        }else {
            hospitalId = hospitalTable.size();
        }

        hospital.setHospitalId(hospitalId);
        hospitalTable.add(hospital);
        return hospitalId;
    }

    public static int addToDoctorTable(Doctor doctor){

        int doctorId;
        if(doctorTable.isEmpty()){
            doctorId = 0;
        }else {
            doctorId = doctorTable.size();
        }
        doctor.setDoctorId(doctorId);
        doctorTable.add(doctor);

        return doctorId;
    }

    public static int addToPatientTable(Patient patient){
        int patientId;
        if(patientTable.isEmpty()){
            patientId = 0;
        }else {
            patientId = patientTable.size();
        }
        patient.setPatientId(patientId);
        patientTable.add(patient);

        return patientId;
    }

    public static int addToAppointmentTable(Appointment appointment){
        int appointmentId;
        if(appointmentTable.isEmpty()){
            appointmentId = 0;
        }else {
            appointmentId = appointmentTable.size();
        }
        appointment.setAppointmentId(appointmentId);
        appointmentTable.add(appointment);
        return appointmentId;
    }

    public static int addToEmergencyTable(Emergency emergency){
        int emergencyId;
        if(emergencyTable.isEmpty()){
            emergencyId = 0;
        }else {
            emergencyId = emergencyTable.size();
        }
        emergency.setEmergencyId(emergencyId);
        emergencyTable.add(emergency);
        return emergencyId;
    }

    /*
     * Update Queries
     */

    public static void updateToPatientTable(Patient patient){
        for(int i = 0; i < patientTable.size(); i++){
            if(patientTable.get(i).getPatientId() == patient.getPatientId()){
                patientTable.remove(i);
                patientTable.add(i, patient);
            }
        }
    }

    public static void updateAppointmentTab(Appointment appointment){
        for(int i = 0; i < appointmentTable.size(); i++){
            if(appointmentTable.get(i).getAppointmentId() == appointment.getAppointmentId()){
                appointmentTable.remove(i);
                appointmentTable.add(i, appointment);
            }
        }
    }

    /*
    * Select Queries
    */

    public static ArrayList<Hospital> getListFromHospTab(){
        return getHospitalTable();
    }

    public static ArrayList<Doctor> getListWhereHospIdEqualsFromDocTab(int hospitalId){

        ArrayList<Doctor> doctorList = new ArrayList<>();

        for(int i = 0; i < doctorTable.size(); i++){

            if(doctorTable.get(i).getHospitalId() == hospitalId){
                doctorList.add(doctorTable.get(i));
            }
        }
        return doctorList;
    }

    public static int getCountWhereSlotAndIdEqualsFromAppTab(int doctorId, String timeslot){
        int count = 0;

        for(int i = 0; i < appointmentTable.size(); i++){
            if(appointmentTable.get(i).getTimeslot().equals(timeslot) && appointmentTable.get(i).getDoctorId() == doctorId){
                count++;
            }
        }
        return count;
    }

    public ArrayList<Appointment> getAppWhereDocIdAndSlotEqualsFromAppTab(int doctorId, String slot){

        ArrayList<Appointment> appointmentList = new ArrayList<>();

        for(int i = 0; i < appointmentTable.size(); i++ ){
            if(appointmentTable.get(i).getDoctorId() == doctorId && appointmentTable.get(i).getTimeslot().equals(slot)){
                appointmentList.add(appointmentTable.get(i));
            }
        }

        return appointmentList;
    }

    public static int getPatientIdFromEmail(String email){
        for(int i = 0; i < patientTable.size(); i++){
            if(patientTable.get(i).getEMailId().equals(email)){
                return patientTable.get(i).getPatientId();
            }
        }
        return -1;
    }

    public static Patient getAllWherePatIdEqualsFromPatTab(int patientId){
        return patientTable.get(patientId);
    }

    public static Doctor getAllWhereIdEqualsFromDocTab(int doctorId){
        return doctorTable.get(doctorId);
    }

}