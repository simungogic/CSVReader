package com.csv.access;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csv.controllers.UserController;
import com.csv.database.User;

public class CSVFile {
	
	String[] csvFields;
	private String csvPath;
	private BufferedReader br;
	private int rowCount = 0;
	private List<String> errors;
	private HashMap<String, Integer> columnNamesIndices;
	
	public CSVFile(String csvPath) {
		super();
		this.csvPath = csvPath;
		this.errors = new ArrayList<String>();
		this.columnNamesIndices = new HashMap<String, Integer>(){
			private static final long serialVersionUID = 1L;

		{
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
	
	public void readCSVFile() {
		String headerLine;
		try {
			headerLine = this.br.readLine();
				
			if(!checkExtension(this.csvPath)) {
				addError("Uploaded file is not CSV!");
				return;
			}
			else if(isEmpty(headerLine)) {
				addError("CSV file is empty!");
		    	return;
		    }
			
			String[] headers = headerLine.split(",");
			if(!checkHeaderNames(headers, this.columnNamesIndices)) 
			{
				addError("CSV has to have 3 columns with header names: " + String.join(", ", this.columnNamesIndices.keySet()));
				return;	
			}
			
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
						addError("In row " + rowCount + " some of the fields are empty!");
						row = br.readLine();
						continue;
					}
					else if(!isDateOfBirthValid(dateOfBirth)) {
						addError("In row " + rowCount + " date of birth is not in valid format!");
						row = br.readLine();
						continue;
					}
					
					UserController userController = UserController.getUserController();
					userController.addUser(new User(firstName, lastName, dateOfBirth));
				}catch(ArrayIndexOutOfBoundsException ex) {
					addError("In row " + rowCount + " some of the fields are missing!");
				}
				
				row = br.readLine();
			}
					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isDateOfBirthValid(String date) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		try {
            LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
		return true;
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

	public List<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}
}
