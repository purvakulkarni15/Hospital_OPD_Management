import com.mycompany.opd_management_system.*;
import org.junit.Test;

import javax.print.Doc;
import java.util.ArrayList;

public class TestBaseController {

    BaseController baseController;

    public TestBaseController(){

        baseController = new BaseController();

    }

    @Test
    public void testSetPatientAndAppointmentData(){

        String json_a = "{'PatientId':-1,'HospitalId':-1,'DoctorId':-1,'PatientName': '','PatientAge': -1,'PatientGender':'', 'PatientIllness':'','MobileNumber':'','EmailId':'purva.1497@gmail.com','Timeslot':'','Date':''}";
        String json_b = "{'PatientId':0,'HospitalId':1,'DoctorId':1,'PatientName': 'ABC1','PatientAge': 22,'PatientGender':'Female', 'PatientIllness':'fever,cough','MobileNumber':'9665921888','EmailId':'purva.1497@gmail.com','Timeslot':'11:00:00 AM','Date':'07/06/2020'}";

        baseController.addPatientAndAppointmentData(json_a);
        baseController.addPatientAndAppointmentData(json_b);

        ArrayList<Patient> patientTable = DbConnector.getPatientTable();
        ArrayList<Appointment> appointmentTable = DbConnector.getAppointmentTable();

        for(int i = 0; i < patientTable.size(); i++){
            System.out.println(patientTable.get(i).getPatientId() + ", " + patientTable.get(i).getName() + ", " + patientTable.get(i).getAge() + ", "+patientTable.get(i).getIllness());
        }

        for(int i = 0; i < appointmentTable.size(); i++){
            System.out.println(appointmentTable.get(i).getAppointmentId() + ", "+appointmentTable.get(i).getPatientId() + ", " + appointmentTable.get(i).getHospitalId() + ", "+ appointmentTable.get(i).getDoctorId() + ", " + appointmentTable.get(i).getTimeslot());
        }

    }

    @Test
    public void testAddHospitalData(){
        String json = "{'HospitalName':'ABC','DoctorsList':[{'DoctorName':'Dr. XYZ', 'DoctorSpeciality':'medicine', 'StartTime':'11:00:00 AM', 'EndTime':'12:00:00 PM', 'SlotTimeInMin':30, 'Capacity':1}, {'DoctorName':'Dr. ABC', 'DoctorSpeciality':'medicine', 'StartTime':'11:00:00 AM', 'EndTime':'12:00:00 PM', 'SlotTimeInMin':30, 'Capacity':1}, {'DoctorName':'Dr. PQR', 'DoctorSpeciality':'medicine', 'StartTime':'11:00:00 AM', 'EndTime':'12:00:00 PM', 'SlotTimeInMin':30, 'Capacity':1}]}";
        baseController.addHospitalData(json);

        ArrayList<Hospital> hospitalTable = DbConnector.getHospitalTable();
        ArrayList<Doctor> doctorTable = DbConnector.getDoctorTable();

        for(int i = 0; i < hospitalTable.size(); i++){
            System.out.println(hospitalTable.get(i).getHospitalId() + ", " + hospitalTable.get(i).getName());
        }

        for(int i = 0; i < doctorTable.size(); i++){
            System.out.println(doctorTable.get(i).getDoctorId() + ", "+doctorTable.get(i).getHospitalId() + ", " + doctorTable.get(i).getName() + ", "+ doctorTable.get(i).getSpeciality() + ", "+ doctorTable.get(i).getStartTime() + ", " + doctorTable.get(i).getEndTime());
        }

    }

    @Test
    public void testGetHospitalList(){
        DbConnector.addToHospitalTable(new Hospital(-1,"ABC"));
        DbConnector.addToHospitalTable(new Hospital(-1, "XYZ"));
        DbConnector.addToHospitalTable(new Hospital(-1,"LMN"));
        DbConnector.addToHospitalTable(new Hospital(-1,"OPQ"));

        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Sheldon Cooper", "Medicine", "11:00:00 AM", "12:00:00 PM", 15, 5));
        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Leonard Hofstadter", "Dermatology", "11:00:00 AM", "12:00:00 PM", 15,5));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Howard Wozowski", "Ophthalmology", "11:00:00 AM", "12:00:00 PM", 15,5));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Raj Koothrapali", "Medicine", "11:00:00 AM", "12:00:00 PM", 15,5));

        System.out.println(baseController.getHospitalList());

    }

    @Test
    public void testGetVacantSlotsList(){

        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Sheldon Cooper", "Medicine", "11:00:00 AM", "12:00:00 PM", 15, 2));
        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Leonard Hofstadter", "Dermatology", "11:00:00 AM", "12:00:00 PM", 15,2));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Howard Wozowski", "Ophthalmology", "11:00:00 AM", "12:00:00 PM", 15,2));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Raj Koothrapali", "Medicine", "11:00:00 AM", "12:00:00 PM", 15,2));

        DbConnector.addToAppointmentTable(new Appointment(0,0,0,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,0,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(2,0,0,1,"07/06/2020 11:15:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(0,0,0,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,0,1,"07/06/2020 11:30:00 AM"));

        System.out.println(baseController.getVacantSlots(1, "07/06/2020"));
    }

    @Test
    public void testGetDoctorList(){
        DbConnector.addToHospitalTable(new Hospital(-1,"ABC"));
        DbConnector.addToHospitalTable(new Hospital(-1, "XYZ"));
        DbConnector.addToHospitalTable(new Hospital(-1,"LMN"));
        DbConnector.addToHospitalTable(new Hospital(-1,"OPQ"));

        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Sheldon Cooper", "Medicine", "11:00:00 AM", "12:00:00 PM", 15, 5));
        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Leonard Hofstadter", "Dermatology", "11:00:00 AM", "12:00:00 PM", 15,5));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Howard Wozowski", "Ophthalmology", "11:00:00 AM", "12:00:00 PM", 15,5));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Raj Koothrapali", "Medicine", "11:00:00 AM", "12:00:00 PM", 15,5));

        System.out.println(baseController.getDoctorList(0));
    }

    @Test
    public void testGetPatientList(){

        DbConnector.addToPatientTable(new Patient(0,"XYZ", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(1,"XYZ", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(2,"XYZ", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(3,"XYZ", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(4,"XYZ", 22, "male", "fever", "ABC", "9665921888"));


        DbConnector.addToAppointmentTable(new Appointment(0,0,0,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,1,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(2,0,2,1,"07/06/2020 11:15:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(0,0,3,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,4,1,"07/06/2020 11:30:00 AM"));

        System.out.println(baseController.getPatientList(1, "07/06/2020 11:00:00 AM"));
    }

    @Test
    public void testPushAppointments(){

        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Sheldon Cooper", "Medicine", "11:00:00 AM", "12:00:00 PM", 15, 3));
        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Leonard Hofstadter", "Dermatology", "11:00:00 AM", "12:00:00 PM", 15,3));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Howard Wozowski", "Ophthalmology", "11:00:00 AM", "12:00:00 PM", 15,3));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Raj Koothrapali", "Medicine", "11:00:00 AM", "12:00:00 PM", 15,3));

        DbConnector.addToPatientTable(new Patient(0,"XYZ-1", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(1,"XYZ-2", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(2,"XYZ-3", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(3,"XYZ-4", 22, "male", "fever", "ABC", "9665921888"));
        DbConnector.addToPatientTable(new Patient(4,"XYZ-5", 22, "male", "fever", "ABC", "9665921888"));


        DbConnector.addToAppointmentTable(new Appointment(0,0,0,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,1,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(2,0,2,1,"07/06/2020 11:15:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(3,0,3,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(4,0,4,1,"07/06/2020 11:30:00 AM"));

        baseController.pushAppointments(1, "07/06/2020", "11:00:00 AM", "11:30:00 AM");

    }

    @Test
    public void testGetNextAvailableSlot(){

        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Sheldon Cooper", "Medicine", "11:00:00 AM", "12:00:00 PM", 30, 3));
        DbConnector.addToDoctorTable(new Doctor(-1,0,"Dr. Leonard Hofstadter", "Dermatology", "11:00:00 AM", "12:00:00 PM", 30,3));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Howard Wozowski", "Ophthalmology", "11:00:00 AM", "12:00:00 PM", 30,3));
        DbConnector.addToDoctorTable(new Doctor(-1,1,"Dr. Raj Koothrapali", "Medicine", "11:00:00 AM", "12:00:00 PM", 30,3));

        DbConnector.addToAppointmentTable(new Appointment(0,0,0,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,1,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(2,0,2,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(3,0,3,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(4,0,4,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(5,0,0,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(6,0,0,1,"08/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(7,0,1,1,"08/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(8,0,2,1,"08/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(9,0,3,1,"08/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(10,0,4,1,"08/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(11,0,0,1,"09/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(12,0,4,1,"09/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(13,0,0,1,"09/06/2020 11:00:00 AM"));


        System.out.println(baseController.getNextAvailableSlot(1));

    }

    @Test
    public void testGetAppointmentList(){
        DbConnector.addToAppointmentTable(new Appointment(0,0,0,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(1,0,1,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(2,0,2,1,"07/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(3,0,3,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(4,0,0,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(5,0,1,1,"07/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(6,0,2,1,"08/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(7,0,3,1,"08/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(8,0,0,1,"08/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(9,0,1,1,"08/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(10,0,2,1,"08/06/2020 11:30:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(11,0,3,1,"09/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(12,0,0,1,"09/06/2020 11:00:00 AM"));
        DbConnector.addToAppointmentTable(new Appointment(13,0,1,1,"09/06/2020 11:00:00 AM"));

        System.out.println(baseController.getAppointmentList(0));
    }
}
