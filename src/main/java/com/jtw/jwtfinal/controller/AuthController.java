package com.jtw.jwtfinal.controller;

import com.jtw.jwtfinal.dto.LoginDTO;
import com.jtw.jwtfinal.dto.RoleDTO;
import com.jtw.jwtfinal.dto.UserDTO;
import com.jtw.jwtfinal.model.Users;
import com.jtw.jwtfinal.service.UsersService;
import com.jtw.jwtfinal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jtw.jwtfinal.model.Role;
import com.jtw.jwtfinal.service.RoleService;
import com.jtw.jwtfinal.service.CustumUserDetails;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private CustumUserDetails CustumUserDetails;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsersService usersService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private JavaMailSender javaMailSender;
	@PostMapping("/insertRole")
	public ResponseEntity<?> addRole(@RequestBody RoleDTO role){
		Role userRole=new Role();
		System.out.println("getting role"+role.getRoleName());
		userRole.setRoleName(role.getRoleName());
		System.out.println(userRole);
		roleService.addRole(userRole);
		return ResponseEntity.ok("Role Added");
		
	}

	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody UserDTO req){
		Users users=new Users();
		users.setName(req.getName());
		users.setUsername(req.getUsername());
		Set<Role> roles=new HashSet<>();
		Iterator i=req.getRole().iterator();
		while (i.hasNext()){
			Role getRole=roleService.getRoleByRole(i.next().toString());
			roles.add(getRole);
		}

		users.setRole(roles);
 		users.setPassword(passwordEncoder.encode(req.getPassword()));
		usersService.addUser(users); 
		return ResponseEntity.ok("User Added");
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginDTO req) throws Exception{
		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
			final UserDetails userDetails= CustumUserDetails.loadUserByUsername(req.getUsername());
			String newGeneratedToken=jwtUtil.generateToken(userDetails);
			Map<String,String> res=new HashMap<>();
			res.put("Token",newGeneratedToken);
			return new ResponseEntity<Map<String,String>>(res, HttpStatus.OK);
		}catch (DisabledException e){
			throw new Exception("User is disabled");
		}catch(BadCredentialsException e){
			throw new Exception("Bad Credential from user");
		}


	}

}
