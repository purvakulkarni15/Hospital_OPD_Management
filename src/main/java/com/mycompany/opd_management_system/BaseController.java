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

    @RequestMapping(method=RequestMethod.GET, value="/addDoctorData", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String addDoctorData(int hospitalId, String doctorName, String speciality, int slotTimeInMins, String startTime, String endTime, int capacity){
        Doctor doctor = new Doctor(0, hospitalId, doctorName, speciality, startTime, endTime, slotTimeInMins, capacity);
        DbConnector.addToDoctorTable(doctor);
        return "Data added successfully";
    }

    @RequestMapping(method=RequestMethod.GET, value="/getDoctorList", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String getDoctorList(int hospitalId){
        ArrayList<Doctor> doctorList = DbConnector.getListWhereHospIdEqualsFromDocTab(hospitalId);
       JSONArray jsonArray = new JSONArray();
       for(int i = 0; i < doctorList.size(); i++){
           JSONObject object = new JSONObject();

           try {
               object.put("doctorId", String.valueOf(doctorList.get(i).getDoctorId()));
               object.put("doctorName", doctorList.get(i).getName());
               object.put("doctorSpeciality", doctorList.get(i).getSpeciality());

               jsonArray.put(object);

           }catch (Exception e){

           }
       }

       return jsonArray.toString();
    }



    @RequestMapping(method=RequestMethod.GET, value="/addHospitalData", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String addHospitalData(String name){
        Hospital hospital = new Hospital(0, name);
        DbConnector.addToHospitalTable(hospital);
        return "Data added successfully";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/setAppointmentData", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String setAppointmentData(String patientName, String hospitalName, String doctorName, int patientAge, String patientGender, String patientIllness, String emailId, String contactNumber, String timeslot, Date date){

        String retVal = null;
        int hospitalId = DbConnector.getIdWhereNameEqualsFromHospTab(hospitalName);

        //Add logger

        Doctor doctor = DbConnector.getAllWhereNameAndHospIdEqualsFromDocTab(doctorName, hospitalId);

        if(!timeslot.isEmpty()){
            if(checkIfSlotAvailable(timeslot, doctor.getDoctorId(), doctor.getCapacity())){
                retVal = "Slot not available";
            }
        }else{
            String availableTimeslot =  getAvailableTimeSlot(doctor.getStartTime(), doctor.getEndTime(), doctor.getStartTime(), doctor.getSlotTimeInMin(), doctor.getDoctorId(), doctor.getCapacity());
            retVal = availableTimeslot;
        }

        Patient patient = new Patient(0, patientName, patientAge, patientGender, patientIllness, emailId, contactNumber);
        DbConnector.addToPatientTable(patient);
        Appointment appointment = new Appointment(hospitalId, patient.getPatientId(), doctor.getDoctorId(), timeslot);

        DbConnector.addToAppointmentTable(appointment);

        return retVal;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pushAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String pushAppointments(String doctorName, int hospitalId, Date date, String from, String to){

        from = from+":00";
        to = to+":00";

        Doctor doctor = DbConnector.getAllWhereNameAndHospIdEqualsFromDocTab(doctorName, hospitalId);

        try {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date fromDate = (Date) dateFormat.parse(from);
            Date toDate = (Date) dateFormat.parse(to);

            Date slot = fromDate;
            long slotTimeinMillis = TimeUnit.MINUTES.toMillis(doctor.getSlotTimeInMin());
            Date toDatePlusSlotTime = new Date(toDate.getTime()+slotTimeinMillis);

            while(!slot.equals(toDatePlusSlotTime)){
                slot = new Date(slot.getTime()+slotTimeinMillis);
                ArrayList<Appointment> appointmentList = DbConnector.getAllWhereSlotEquals(dateFormat.format(slot));
                rescheduleAppointmentsAndNotify(appointmentList, dateFormat.format(toDatePlusSlotTime), doctor);
            }


        }catch(Exception e){
            System.out.println(e);//log exception
        }

        return "Data stored successfully";
    }

    public static String getAvailableTimeSlot(String doctorStartTimeStr, String doctorEndTimeStr, String fromTimeStr, int slotTimeInMin, int doctorId, int capacity){
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            DateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
            DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");

            Date date = dateFormat.parse(fromTimeStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 7);
            Date dateAfterAweek = cal.getTime();

            Date startTime = (Date) dateFormat.parse(doctorStartTimeStr);
            Date endTime = (Date) dateFormat.parse(doctorEndTimeStr);

            String strt = dateFormat2.format(startTime);
            String end  = dateFormat2.format(endTime);

            Date temp = date;

            while(!temp.equals(dateAfterAweek)) {

                Date slot = startTime;
                long slotTimeinMillis = TimeUnit.MINUTES.toMillis(slotTimeInMin);

                while (!slot.equals(endTime)) {

                    if (slot.after(date) && checkIfSlotAvailable(dateFormat.format(slot), doctorId, capacity)) {
                        return dateFormat.format(slot);
                    }
                    slot = new Date(slot.getTime() + slotTimeinMillis);
                }

                cal.setTime(temp);
                cal.add(Calendar.DATE, 1);
                temp = cal.getTime();

                startTime = (Date) dateFormat.parse(dateFormat3.format(temp) + strt);
                endTime = (Date) dateFormat.parse(dateFormat3.format(temp) + end);

            }

        }catch (Exception e){
            //Add logger
        }
        return null;
    }

    public static boolean checkIfSlotAvailable(String timeslot, int doctorId, int capacity){

        int count = DbConnector.getCountWhereSlotEqualsFromAppTab(timeslot);
        if(count+1 > capacity){
            return false;
        }

        return true;
    }

    public static void rescheduleAppointmentsAndNotify(ArrayList<Appointment> appointmentList, String from, Doctor doctor){
        for(int i = 0; i < appointmentList.size(); i++){
            String message;

            String timeslot = getAvailableTimeSlot(doctor.getStartTime(), doctor.getEndTime(), from, doctor.getSlotTimeInMin(),doctor.getDoctorId(), doctor.getCapacity());
            appointmentList.get(i).setTimeslot(timeslot);
            Patient patient = DbConnector.getAllWherePatIdEqualsFromPatTable(appointmentList.get(i).getPatientId());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            if(timeslot.isEmpty()){
                message = "Sorry, your appointment scheduled on " + appointmentList.get(i).getTimeslot()+ "is cancelled. Please try booking again tomorrow.";
            }else{
                message = "Your appointment has been postponed to "+ appointmentList.get(i).getTimeslot();
            }
            DbConnector.updateAppointmentTab(appointmentList.get(i).getPatientId(), appointmentList.get(i).getHospitalId(), appointmentList.get(i).getPatientId(), appointmentList.get(i));
            notifyPatientByEmail(patient.getEMailId(), message);
        }
    }

    public static void notifyPatientByEmail(String email, String message){
        System.out.println(message);
    }

}
