package com.mycompany.opd_management_system;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseService {

    private static DbConnector dbConnector;

    BaseService(){
        dbConnector = new DbConnector();
    }

    public static boolean checkIfSlotAvailable(String timeslot, int doctorId, int capacity){

        int count = dbConnector.getCountWhereSlotAndIdEqualsFromAppTab(doctorId, timeslot);
        if(count+1 > capacity){
            return false;
        }
        return true;
    }

    public static String getAvailableTimeSlot(Doctor doctor,String fromTimeStr){
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");

            Date fromDate = dateFormat.parse(fromTimeStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            cal.add(Calendar.DATE, 7);
            Date dateAfterAweek = cal.getTime();

            String startStr = dateFormat.format(fromDate);
            String endStr  = dateFormat3.format(fromDate) + " " +doctor.getEndTime();

            Date startTime = (Date) dateFormat.parse(startStr);
            Date endTime = (Date) dateFormat.parse(endStr);

            Date temp = fromDate;

            while(!temp.equals(dateAfterAweek)) {
                Date slot = startTime;
                long slotTimeinMillis = TimeUnit.MINUTES.toMillis(doctor.getSlotTimeInMin());

                while (slot.before(endTime)) {
                    if (!slot.before(fromDate) && checkIfSlotAvailable(dateFormat.format(slot), doctor.getDoctorId(), doctor.getCapacity())) {
                        return dateFormat.format(slot);
                    }
                    slot = new Date(slot.getTime() + slotTimeinMillis);
                }

                cal.setTime(temp);
                cal.add(Calendar.DATE, 1);
                temp = cal.getTime();

                startTime = (Date) dateFormat.parse(dateFormat3.format(temp) + " " + doctor.getStartTime());
                endTime = (Date) dateFormat.parse(dateFormat3.format(temp) + " " + doctor.getEndTime());


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void rescheduleAppointmentsAndNotify(ArrayList<Appointment> appointmentList, String from, Doctor doctor){

        for(int i = 0; i < appointmentList.size(); i++){
            String message;

            String timeslot = getAvailableTimeSlot(doctor, from);
            Patient patient = dbConnector.getAllWherePatIdEqualsFromPatTab(appointmentList.get(i).getPatientId());

            notifyPatientByEmail(patient.getEMailId(), patient.getName(), appointmentList.get(i).getTimeslot(), timeslot);

            appointmentList.get(i).setTimeslot(timeslot);
            if(timeslot.isEmpty()){
                appointmentList.get(i).setAppointmentCancelled(true);
            }else{
                appointmentList.get(i).setAppointmentPushed(true);
            }

            dbConnector.updateAppointmentTab(appointmentList.get(i));
        }
    }

    public static void notifyPatientByEmail(String email, String name, String oldTimeSlot, String newTimeslot){

        /*System.out.println(name + " " + oldTimeSlot + " " + newTimeslot);

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "localhost");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("opdManagementSystem@gmail.com", "opdSystem123");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("opdManagementSystem@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("purva.kulkarni145@gmail.com"));
            message.setSubject("Hello");
            message.setContent("htmlContent","text/html" );
            Transport.send(message);

        } catch (MessagingException mex) {
            System.out.println(mex.getMessage());
        }*/

    }


}
