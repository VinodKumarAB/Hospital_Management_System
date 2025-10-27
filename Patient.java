package com.hospital_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	
	private Connection connection;
	private Scanner s;
	
	public Patient(Connection connection,Scanner s) {
		
		this.connection=connection;
		this.s=s;
	}

	public void addPatient() {
		System.out.println("Enter patient Name: ");
		String name=s.next();
		System.out.println("Enter patient Age: ");
		int age=s.nextInt();
		System.out.println("Enter patient Gender: ");
		String gender=s.next();
		try {
			String query="insert into patients(name,age,gender) values(?,?,?) ";
			PreparedStatement preparestm=connection.prepareStatement(query);
			preparestm.setString(1, name);
			preparestm.setInt(2, age);
			preparestm.setString(3, gender);
			
			int affectrows=preparestm.executeUpdate();
			if(affectrows>0) {
				System.out.println("patient Added successfully: ");
				
			}
			else {
				System.out.println("Failed to add patient: ");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}	
		public void viewPatients() {
			String query="select * from patients";
			try {
				PreparedStatement preparestm=connection.prepareStatement(query);
				ResultSet resultset=preparestm.executeQuery();
				System.out.println("Patients: ");
				System.out.println("+------------+--------------------+---------+------------+");
				System.out.println("| patient Id | Name               | Age     | Gender     |");
				System.out.println("+------------+--------------------+---------+------------+");
				
				while(resultset.next()) {
					int id=resultset.getInt("id");
					String name=resultset.getString("name");
					int age=resultset.getInt("age");
					String gender=resultset.getString("gender");
					System.out.printf("|%-12s|%-20s|%-10s|%-12s|\n",id,name,age,gender);
					System.out.println("+------------+--------------------+---------+------------+");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		public boolean getPatientById(int id) {
			String query="select * from patients where id=?";
			try {
				
				PreparedStatement preparestm=connection.prepareStatement(query);
				preparestm.setInt(1, id);
				ResultSet resultset=preparestm.executeQuery();
				if(resultset.next()) {
		 			return true;
				}
				else {
					return false;
				}
				
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}	
		
	}

