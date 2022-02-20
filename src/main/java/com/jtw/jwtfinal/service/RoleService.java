package com.jtw.jwtfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jtw.jwtfinal.dao.RoleDAO;
import com.jtw.jwtfinal.model.Role;

@Service
public class RoleService {

	@Autowired
	RoleDAO roleDAO;
	public void addRole(Role role) {
		roleDAO.save(role);
	}


	public Role getRoleByRole(String role) {
		Role roles=roleDAO.findByRoleName(role).orElseThrow(()-> new UsernameNotFoundException("No Role Found"));
		return roles;
	}
}
