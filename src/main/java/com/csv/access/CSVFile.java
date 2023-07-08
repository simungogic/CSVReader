package com.csv.access;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csv.database.User;

public class CSVFile {
	
	String[] csvFields;
	private String csvPath;
	private BufferedReader br;
	private int rowCount = 0;
	List<User> users;
	private HashMap<String, Integer> columnNamesIndices;
	
	public CSVFile(String csvPath) {
		super();
		this.csvPath = csvPath;
		this.users = new ArrayList<User>();
		this.columnNamesIndices = new HashMap<String, Integer>(){{
			put("Ime", 0);
			put("Prezime", 1);
			put("DatumRodjenja", 2);
		}};
		try {
			this.br = new BufferedReader(new FileReader(csvPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<User> readCSVFile() {
		String headerLine;
		try {
			headerLine = this.br.readLine();
			String[] headers = headerLine.split(",");
			
			if(!checkExtension(this.csvPath)) {
				System.out.println("Uploaded file is not CSV!");
				return null;
			}
			else if(isEmpty(headerLine)) {
		    	System.out.println("CSV file is empty!");
		    	return null;
		    }
			else if(!checkHeaderNames(headers, this.columnNamesIndices)) return null;	
			
			this.columnNamesIndices = rearrangeColumnIndices(headers, this.columnNamesIndices);
			
			//read rows
			String row = br.readLine();
			while(row != null) {
				rowCount++;
				this.csvFields = row.split(",");
				try {
					String firstName = this.csvFields[this.columnNamesIndices.get("Ime")].trim();
					String lastName = this.csvFields[this.columnNamesIndices.get("Prezime")].trim();
					String dateOfBirth = this.csvFields[this.columnNamesIndices.get("DatumRodjenja")].trim();
					if(isEmpty(firstName) || isEmpty(lastName) || isEmpty(dateOfBirth)) {
						System.out.println("In row " + rowCount + " some of the fields are empty! This row is not inserted...");
						row = br.readLine();
						continue;
					}
					User user = new User(firstName, lastName, dateOfBirth);
					users.add(user);
				}catch(ArrayIndexOutOfBoundsException ex) {
					System.out.println();
					System.out.println("In row " + rowCount + " some of the fields are missing! This row is not inserted...");
				}
				
				row = br.readLine();
			}
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	private static boolean isEmpty(String string) {
		if(string == null || string.isBlank()) {
			return true;
		}
		
		return false;
	}
	
	private static boolean checkHeaderNames(String[] headers, HashMap<String, Integer> columnNamesIndices) {
		boolean result = true;
		if(headers.length != 3) result = false;
		
		for(int i = 0; i < headers.length; i++) {
			if(!columnNamesIndices.containsKey(headers[i].trim())) {
				System.out.print(headers[i]);
				result = false;
			}
		}
		
		if(!result) {
			 System.out.println("CSV has to have 3 columns with header names: " + String.join(", ", columnNamesIndices.keySet()));
		}
		
		return result;
	}
	
	public static boolean checkExtension(String path) {
		String extension = "";
		int i = path.lastIndexOf('.');
		
		if (i > 0) extension = path.substring(i+1);
		
		if(extension.isEmpty() || !extension.equals("csv")) return false;
		
		return true;
		
	}
	
	private static HashMap<String, Integer> rearrangeColumnIndices(String[] headers, HashMap<String, Integer> columnNamesIndices) {
		for(int i = 0; i < headers.length; i++) {
			columnNamesIndices.put(headers[i], i);
		}
		
		return columnNamesIndices;
	}
}
