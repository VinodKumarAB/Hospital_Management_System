package com.hospital_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	
	private static final String username="root";
	private static final String password="root";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     try {
    	 Class.forName("com.mysql.cj.jdbc.Driver");
     
     }catch(ClassNotFoundException e) {
    	 e.printStackTrace();
     }
     Scanner scanner=new Scanner(System.in);
     
	 try {
		 Connection connection=DriverManager.getConnection(url,username,password);
		 Patient patients=new Patient(connection,scanner);
		 Doctor doctor=new Doctor(connection);
		 
		 while(true) {
				System.out.println("Hospital Mangement System:  ");
				System.out.println("1. Add patient");
				System.out.println("2. View patient");
				System.out.println("3. view Doctor");
				System.out.println("4. Book Appointment");
				System.out.println("5. View Appointments");
				System.out.println("6. Exit");
				System.out.println("Enter your choice: ");
				int choice=scanner.nextInt();
				
				switch(choice) {
				case 1->{
					patients.addPatient();
					System.out.println();
					
				}
				case 2->{
					patients.viewPatients();
					System.out.println();
				}
				case 3->{
					doctor.viewDoctors();
					System.out.println();
				}
				case 4->{
					bookAppointment(patients,doctor,connection,scanner);
					System.out.println();
				}
				case 5 -> viewAppointments(connection);
				case 6 -> {
				    System.out.println("Thank you for using the system.");
				    return;
				}
				
				default ->
				System.out.println("Enter valid choice!!!");
				}
				
		 }
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
     
	}
	
	public static void bookAppointment(Patient patient,Doctor doctor ,Connection connection,Scanner scanner) {
		System.out.println("Enter patient id: ");
		int patientId=scanner.nextInt();
		System.out.println("Enter Doctor Id: ");
		int doctorId=scanner.nextInt();
		System.out.println("Enter appointment date (yyy-mm-dd):");
		String appointmentDate=scanner.next();
		
		if(patient.getPatientById(patientId)&& doctor.getDoctorById(doctorId)) {
			
			if(checkDoctorAviable(doctorId,appointmentDate,connection)) {
			 
				String appointmentQuery="insert into appointments(patients_id,doctor_id,appointment_date) values(?,?,?)";
				try {
					PreparedStatement preparestm=connection.prepareStatement(appointmentQuery);
					preparestm.setInt(1, patientId);
					preparestm.setInt(2, doctorId);
					preparestm.setString(3, appointmentDate);
					int rowsAffect=preparestm.executeUpdate();
					if(rowsAffect>0) {
						System.out.println(" Appointment Boked");
						
					}else {
						System.out.println(" Failed to booked appintment");
					}
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println(" Doctor not avvailable on this date!!");
			}
			
		}else {
			System.out.println("Either doctor or patient doesn't exist!!");
		}
		
		
	}
	
	
	public static boolean checkDoctorAviable(int doctorId,String appointmentDate,Connection  connection) {
		String query="select count(*) from appointments where doctor_id=? and appointment_date=?";
	
		try {
			PreparedStatement preparestm=connection.prepareStatement(query);
			preparestm.setInt(1, doctorId);
			preparestm.setString(2, appointmentDate);
			ResultSet resultSet=preparestm.executeQuery();
			 
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count==0) {
					return true;
				}else {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static void viewAppointments(Connection connection) {
	    try {
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments");
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            //System.out.println("Appointment ID: " + rs.getInt("appointment_id"));
	            System.out.println("Patient ID: " + rs.getInt("patients_id"));
	            System.out.println("Doctor ID: " + rs.getInt("doctor_id"));
	            System.out.println("Appointment Date: " + rs.getString("appointment_date"));
	            System.out.println("-----------------------------");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
