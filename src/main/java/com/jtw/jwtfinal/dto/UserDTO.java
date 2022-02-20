package com.jtw.jwtfinal.dto;

import com.jtw.jwtfinal.model.ERole;
import com.jtw.jwtfinal.model.Role;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private String username;
    private String name;
    private String password;
    private Set<ERole> role=new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ERole> getRole() {
        return role;
    }

    public void setRole(Set<ERole> role) {
        this.role = role;
    }
}
