package com.hospital_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

	private Connection connection;

	
	public Doctor(Connection connection) {
		
		this.connection=connection;
	}	
		public void viewDoctors() {
			String query="select * from doctors";
			try {
				PreparedStatement preparestm=connection.prepareStatement(query);
				ResultSet resultset=preparestm.executeQuery();
				System.out.println("Dcotor: ");
				System.out.println("+------------+--------------------+------------------+");
				System.out.println("| doctor Id  | Name               | specialization   |");
				System.out.println("+------------+--------------------+------------------+");
				
				while(resultset.next()) {
					int id=resultset.getInt("id");
					String name=resultset.getString("name");
					String specialization=resultset.getString("specialization");
					System.out.printf("|%-12s|%-20s%-19s|",id,name,specialization);
					System.out.println("+------------+--------------------+------------------+");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		public boolean getDoctorById(int id) {
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

