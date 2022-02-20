package com.jtw.jwtfinal.service;

import com.jtw.jwtfinal.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jtw.jwtfinal.dao.UsersDAO;

@Service
public class UsersService {

	@Autowired
	UsersDAO userDAO;

	public  void addUser(Users user){
		userDAO.save(user);
	}
	
}
