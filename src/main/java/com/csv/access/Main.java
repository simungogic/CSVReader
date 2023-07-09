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
			maxFileSize = 1024  * 1024 * 10,  // 10MB
			maxRequestSize = 1024 * 1024 * 11 // 11MB
		)
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Main() {
        super();
    }
        
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadPath = getServletContext().getRealPath("");
		Part part = request.getPart("csv_file");
	    String fullPath = uploadPath + File.separator + part.getSubmittedFileName();
	    part.write(fullPath);	    
	    
	    UserController.getUserController().clearUsers();
	    CSVFile csvFile = new CSVFile(fullPath);
	    csvFile.readCSVFile();
	    List<User> users = UserController.getUserController().getUsers();
	    
	    if(users != null) { 
			users.forEach(user -> {
				UserController.insert(user);
			});
	    }
	    request.setAttribute("users", users);
	    request.setAttribute("errors", csvFile.getErrors());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
