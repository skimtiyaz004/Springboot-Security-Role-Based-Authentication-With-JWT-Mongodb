package com.jtw.jwtfinal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class UsersController {

    @RequestMapping("CheckAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    public ResponseEntity<?> checkRole(){
        System.out.println("Role is working");
        return ResponseEntity.ok("Role is working");
    }
}
