package com.csv.controllers;
import java.util.ArrayList;
import java.util.List;

import com.csv.database.User;
import com.csv.database.UserDao;

public class UserController {
	
	private static UserController userController;
	private List<User> users;
	
	private UserController() {
		users = new ArrayList<User>();
	}
	
	public static UserController getUserController()
    {
        if (userController == null) userController = new UserController();
        
        return userController;
    }
	
	
	public List<User> getUsers() {
		return users;
	}

	public void clearUsers() {
		this.users.clear();;
	}

	public void addUser(User user) {
		this.users.add(user);
	}
	
	public static void insert(User user) {
		UserDao.insert(user);
	}

	public static List<User> searchAll() {
		return UserDao.searchAll();
	}

}
