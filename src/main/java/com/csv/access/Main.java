package com.csv.access;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.csv.controllers.UserController;
import com.csv.database.User;

@WebServlet("/main")
@MultipartConfig(
			fileSizeThreshold = 1024 * 1024, // 1MB
			maxFileSize = 1024 * 1024 * 10,  // 10MB
			maxRequestSize = 1024 * 1024 * 11 // 11MB
		)
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Main() {
        super();
    }
        
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------");
		String uploadPath = getServletContext().getRealPath("");
		File uploadDir = new File(uploadPath);
	    Part part = request.getPart("csv_file");
	    String fullPath = uploadPath + File.separator + part.getSubmittedFileName();
	    part.write(fullPath);	    
	    
	    CSVFile csvFile = new CSVFile(fullPath);
	    List<User> users = csvFile.readCSVFile();
	    
	    if(users != null) { 
	    	System.out.println("These rows are inserted in table Users: ");
			users.forEach(user -> {
				UserController.insert(user);
				System.out.println(user.getFirstName().trim() + ", " + user.getLastName().trim() + ", " + user.getDateOfBirth().trim());
			});
	    }
	    
	    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.html");
	    dispatcher.forward(request, response);
	}

}
