package com.mycompany.opd_management_system;

import java.util.ArrayList;

public class DbConnector {

    private static ArrayList<Doctor> doctorTable;
    private static ArrayList<Hospital>hospitalTable;
    private static ArrayList<Patient>patientTable;
    private static ArrayList<Appointment>appointmentTable;

    static{
        doctorTable = new ArrayList<>();
        hospitalTable = new ArrayList<>();
        patientTable = new ArrayList<>();
        appointmentTable =  new ArrayList<>();
    }

    public static String addToHospitalTable(Hospital hospital){
        int hospitalId;
        if(hospitalTable.isEmpty()){
            hospitalId = 1;
        }else {
            hospitalId= hospitalTable.size()-1;
        }
        hospital.setHospitalId(hospitalId);
        hospitalTable.add(hospital);
        return "Data added successfully";
    }

    public static String addToDoctorTable(Doctor doctor){

        int doctorId;
        if(doctorTable.isEmpty()){
            doctorId = 1;
        }else {
            doctorId = doctorTable.size()-1;
        }
        doctor.setDoctorId(doctorId);
        doctorTable.add(doctor);

        return "Data added successfully";
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

    public static String addToPatientTable(Patient patient){
        int patientId;
        if(patientTable.isEmpty()){
            patientId = 1;
        }else {
            patientId = patientTable.size()-1;
        }
        patient.setPatientId(patientId);
        patientTable.add(patient);

        return "Data added successfully";
    }

    public static String addToAppointmentTable(Appointment appointment){
        appointmentTable.add(appointment);
        return "Data added successfully";
    }

    public static int getIdWhereNameEqualsFromHospTab(String hospitalName){
        for(int i = 0; i < hospitalTable.size(); i++){

            if(hospitalTable.get(i).getName().equals(hospitalName)){
               return hospitalTable.get(i).getHospitalId();
            }
        }
        return -1;
    }

    public static Doctor getAllWhereNameAndHospIdEqualsFromDocTab(String doctorName, int hospitalId){

        for(int i = 0; i < doctorTable.size(); i++){
            if(doctorTable.get(i).getName().equals(doctorName) && doctorTable.get(i).getHospitalId() == hospitalId){
                return doctorTable.get(i);
            }
        }
        return null;
    }

    public static int getCountWhereSlotEqualsFromAppTab(String timeslot){
        int count = 0;

        for(int i = 0; i < appointmentTable.size(); i++){
            if(appointmentTable.get(i).getTimeslot().equals(timeslot)){
                count++;
            }
        }

        return count;
    }

    public static ArrayList<Appointment> getAllWhereSlotEqualsFromAppTab(String timeslot){
        ArrayList<Appointment>appointmentList = new ArrayList<>();

        for(int i = 0; i < appointmentTable.size(); i++){
            if(appointmentTable.get(i).getTimeslot().equals(timeslot)){
                appointmentList.add(appointmentTable.get(i));
            }
        }
        return appointmentList;
    }

    public static String updateAppointmentTab(int hospitalId, int patientId, int doctorId, Appointment appointment){
        for(int i = 0; i < appointmentTable.size(); i++){
            if(appointmentTable.get(i).getPatientId() == patientId && appointmentTable.get(i).getHospitalId() == hospitalId && appointmentTable.get(i).getDoctorId() == doctorId){
                appointmentTable.remove(i);
                appointmentTable.add(i, appointment);
            }
        }

        return "Updated Appointment table successfully";
    }

    public static ArrayList<Appointment> getAppWhereDocIdAndSlotEqualsFromAppTab(int doctorId, String slot){

        ArrayList<Appointment> appointmentList = new ArrayList<>();

        for(int i = 0; i < appointmentTable.size(); i++ ){
            if(appointmentTable.get(i).getDoctorId()==doctorId && appointmentTable.get(i).getTimeslot().equals(slot)){
                appointmentList.add(appointmentTable.get(i));
            }
        }

        return appointmentList;
    }

    public static Patient getAllWherePatIdEqualsFromPatTab(int patientId){
        return patientTable.get(patientId);
    }

    public static Doctor getAllWhereIdEqualsFromDocTab(int doctorId){
        return doctorTable.get(doctorId);
    }
}