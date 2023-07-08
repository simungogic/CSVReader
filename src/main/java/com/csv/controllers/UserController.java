package com.csv.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
