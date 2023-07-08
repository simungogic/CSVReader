package com.csv.controllers;
import java.util.List;

import com.csv.database.User;
import com.csv.database.UserDao;

public class UserController {
	
	public static void insert(User user) {
		UserDao.insert(user);
	}

	public static List<User> searchAll() {
		return UserDao.searchAll();
	}

}
