package com.mycompany.opd_management_system;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "*")
public class BaseController {
    private static DbConnector dbConnector;
    public BaseController(){
        dbConnector = new DbConnector();
    }

    @RequestMapping(method=RequestMethod.GET, value="/getPatientIdFromEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getPatientIdFromEmail(String email){
        int patientId = DbConnector.getPatientIdFromEmail(email);
        return String.valueOf(patientId);
    }

    @RequestMapping(method=RequestMethod.GET, value="/setPatientData", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String addPatientAndAppointmentData(String appointmentObject){

        int patientId = -1;
        int doctorId = -1;
        int hospitalId = -1;
        String patientName = null;
        int patientAge = -1;
        String patientGender = null;
        String patientIllness = null;
        String emailId = null;
        String contactNumber = null;
        String timeslot = null;
        String date = null;

        try {
            JSONObject jsonObject = new JSONObject(appointmentObject);

            patientId = jsonObject.getInt("PatientId");
            hospitalId = jsonObject.getInt("HospitalId");
            doctorId = jsonObject.getInt("DoctorId");
            patientName = jsonObject.getString("PatientName");
            patientAge = jsonObject.getInt("PatientAge");
            patientGender = jsonObject.getString("PatientGender");
            patientIllness = jsonObject.getString("PatientIllness");
            emailId = jsonObject.getString("EmailId");
            contactNumber = jsonObject.getString("MobileNumber");
            timeslot = jsonObject.getString("Timeslot");
            date = jsonObject.getString("Date");

        }catch(Exception e){
            e.printStackTrace();
        }

        if(patientId != -1) {
            Patient patient = new Patient(patientId, patientName, patientAge, patientGender, patientIllness, emailId, contactNumber);
            dbConnector.updateToPatientTable(patient);
            Appointment appointment = new Appointment(-1,hospitalId, patientId, doctorId, timeslot);
            if(patientIllness.contains("fever") && patientIllness.contains("cough")){
                appointment.setCovidSuspect(true);
            }
            dbConnector.addToAppointmentTable(appointment);
        }else{
            Patient patient = new Patient(patientId, patientName, patientAge, patientGender, patientIllness, emailId, contactNumber);
            dbConnector.addToPatientTable(patient);
        }

        return "Data added successfully";
    }

    @RequestMapping(method=RequestMethod.GET, value="/addHospitalData", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String addHospitalData(String hospitalObj){

        String hospitalName;
        int hospitalId;
        String doctorName;
        String doctorSpeciality;
        String startTime;
        String endTime;
        int slotTimeInMin;
        int capacity;

        try {
            JSONObject jsonObject = new JSONObject(hospitalObj);
             hospitalName = jsonObject.getString("HospitalName");
             hospitalId = dbConnector.addToHospitalTable(new Hospital(-1, hospitalName));
             JSONArray jsonArray = jsonObject.getJSONArray("DoctorList");

             for(int i = 0; i < jsonArray.length(); i++){
                 JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                 doctorName = jsonObject1.getString("DoctorName");
                 doctorSpeciality = jsonObject1.getString("DoctorSpeciality");
                 startTime = jsonObject1.getString("StartTime");
                 endTime = jsonObject1.getString("EndTime");
                 slotTimeInMin = jsonObject1.getInt("SlotTimeInMin");
                 capacity = jsonObject1.getInt("Capacity");

                 dbConnector.addToDoctorTable(new Doctor(-1, hospitalId, doctorName, doctorSpeciality, startTime, endTime, slotTimeInMin, capacity));
             }

        }catch(Exception e){
            e.printStackTrace();
        }

        return "Data added successfully";
    }

    @RequestMapping(method=RequestMethod.GET, value="/getHospitalList", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getHospitalList(){
        ArrayList<Hospital>hospitals = dbConnector.getListFromHospTab();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < hospitals.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("HospitalId", hospitals.get(i).getHospitalId());
                jsonObject1.put("HospitalName", hospitals.get(i).getName());
                ArrayList<Doctor>doctors = dbConnector.getListWhereHospIdEqualsFromDocTab(hospitals.get(i).getHospitalId());
                JSONArray jsonArray2 = new JSONArray();
                for(int j = 0; j < doctors.size(); j++){
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("DoctorId", doctors.get(i).getDoctorId());
                    jsonObject2.put("DoctorName", doctors.get(i).getName());
                    jsonObject2.put("DoctorSpeciality", doctors.get(i).getSpeciality());
                    jsonObject2.put("StartTime", doctors.get(i).getStartTime());
                    jsonObject2.put("EndTime", doctors.get(i).getEndTime());
                    jsonObject2.put("SlotTimeInMin", doctors.get(i).getSlotTimeInMin());
                    jsonObject2.put("Capacity", doctors.get(i).getCapacity());
                    jsonArray2.put(jsonObject2);
                }
                jsonObject1.put("DoctorList", jsonArray2);
                jsonArray1.put(jsonObject1);
            }
            jsonObject.put("HospitalList", jsonArray1);
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @RequestMapping(method=RequestMethod.GET, value="/getVacantSlots", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getVacantSlots(int doctorId, String date){
        Doctor doctor = dbConnector.getAllWhereIdEqualsFromDocTab(doctorId);
        ArrayList<String>vacantSlots =  new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

        String startStr = date + " " + doctor.getStartTime();
        String endStr = date + " " + doctor.getEndTime();
        String retVal;

        try {

            Date start = (Date) dateFormat.parse(startStr);
            Date end = (Date) dateFormat.parse(endStr);

            Date slot = start;
            long slotTimeinMillis = TimeUnit.MINUTES.toMillis(doctor.getSlotTimeInMin());

            while (slot.before(end)) {
                if (BaseService.checkIfSlotAvailable(dateFormat.format(slot), doctorId, doctor.getCapacity())) {
                    vacantSlots.add(dateFormat.format(slot));
                }
                slot = new Date(slot.getTime() + slotTimeinMillis);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        retVal = "{";
        for(int i = 0; i < vacantSlots.size(); i++){
            retVal = retVal + "'" + vacantSlots.get(i) + "'" ;
            if(i < vacantSlots.size()-1){
                retVal = retVal + ", ";
            }
        }
        retVal = retVal + "}";

        return retVal;
    }

    @RequestMapping(method=RequestMethod.GET, value="/getDoctorList", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getDoctorList(int hospitalId){
        ArrayList<Doctor> doctorList = dbConnector.getListWhereHospIdEqualsFromDocTab(hospitalId);
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < doctorList.size(); i++){
            JSONObject object = new JSONObject();

            try {
                object.put("DoctorId", String.valueOf(doctorList.get(i).getDoctorId()));
                object.put("DoctorName", doctorList.get(i).getName());
                object.put("DoctorSpeciality", doctorList.get(i).getSpeciality());
                object.put("StartTime", doctorList.get(i).getStartTime());
                object.put("EndTime", doctorList.get(i).getEndTime());
                object.put("SlotTimeInMin", doctorList.get(i).getSlotTimeInMin());
                object.put("Capacity", doctorList.get(i).getCapacity());
                jsonArray.put(object);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getPatientList", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getPatientList(int doctorId, String timeslot){
        ArrayList<Appointment> appointmentList = dbConnector.getAppWhereDocIdAndSlotEqualsFromAppTab(doctorId, timeslot);
        ArrayList<Patient> patientList = new ArrayList<>();

        for(int i = 0; i < appointmentList.size(); i++){
            patientList.add(dbConnector.getAllWherePatIdEqualsFromPatTab(appointmentList.get(i).getPatientId()));
        }

        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < patientList.size(); i++){
            JSONObject object = new JSONObject();

            try {

                object.put("PatientId", patientList.get(i).getPatientId());
                object.put("PatientName", patientList.get(i).getName());
                object.put("PatientAge", patientList.get(i).getAge());
                object.put("PatientGender", patientList.get(i).getGender());
                object.put("PatientIllness", patientList.get(i).getIllness());

                jsonArray.put(object);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return jsonArray.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pushAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String pushAppointments(int doctorId, String date, String fromStr, String toStr){

        dbConnector.addToEmergencyTable(new Emergency(doctorId, date, fromStr, toStr));

        Doctor doctor = dbConnector.getAllWhereIdEqualsFromDocTab(doctorId);

        fromStr = date+" "+fromStr;
        toStr = date+" "+toStr;

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            Date fromDate = (Date) dateFormat.parse(fromStr);
            Date toDate = (Date) dateFormat.parse(toStr);

            Date slot = fromDate;
            long slotTimeinMillis = TimeUnit.MINUTES.toMillis(doctor.getSlotTimeInMin());
            Date toDatePlusSlotTime = new Date(toDate.getTime()+slotTimeinMillis);

            ArrayList<Appointment> appointmentList;

            while(slot.before(toDate)){
                slot = new Date(slot.getTime()+slotTimeinMillis);
                //System.out.println(dateFormat.format(slot));
                appointmentList = dbConnector.getAppWhereDocIdAndSlotEqualsFromAppTab(doctor.getDoctorId(), dateFormat.format(slot));
                BaseService.rescheduleAppointmentsAndNotify(appointmentList, dateFormat.format(toDatePlusSlotTime), doctor);
            }

            appointmentList = dbConnector.getAppWhereDocIdAndSlotEqualsFromAppTab(doctor.getDoctorId(), dateFormat.format(slot));

            for(int i = 0; i < appointmentList.size(); i++) {
                appointmentList.get(i).setTimeslot(dateFormat.format(new Date(fromDate.getTime()+slotTimeinMillis)));
                DbConnector.updateAppointmentTab(appointmentList.get(i));
            }

        }catch(Exception e){
            System.out.println(e);//log exception
        }

        return "Data stored successfully";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getNextAvailableSlot", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getNextAvailableSlot(int doctorId){

        String availableTimeslot="";
        Doctor doctor = dbConnector.getAllWhereIdEqualsFromDocTab(doctorId);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date fromTomorrow = cal.getTime();
        String fromTomorrowStr = dateFormat.format(fromTomorrow) + " " + doctor.getStartTime();

        availableTimeslot =  BaseService.getAvailableTimeSlot(doctor, fromTomorrowStr);

        if(availableTimeslot.isEmpty()){
            return "All slots for booked for a week. Please try again later";
        }

        return availableTimeslot;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getAppointmentList(int patientId){
        ArrayList<Appointment> appointmentList = dbConnector.getListWherePatIdEqualsFromAppTab(patientId);

        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < appointmentList.size(); i++){
            JSONObject object = new JSONObject();

            try {

                object.put("AppointmentId", appointmentList.get(i).getAppointmentId());
                object.put("Timeslot", appointmentList.get(i).getTimeslot());

                jsonArray.put(object);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return jsonArray.toString();

    }

}
