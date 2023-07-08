package com.csv.access;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.csv.controllers.UserController;
import com.csv.database.User;

@WebServlet("/listing")
public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UsersList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> retrievedUsers = UserController.searchAll();
	    request.setAttribute("userList", retrievedUsers);
		request.getRequestDispatcher("/user_list.jsp").forward(request, response);
	}
}
