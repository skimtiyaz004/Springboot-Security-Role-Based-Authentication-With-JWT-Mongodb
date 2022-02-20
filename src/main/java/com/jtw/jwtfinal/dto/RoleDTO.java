package com.jtw.jwtfinal.dto;

import com.jtw.jwtfinal.model.ERole;
import com.mongodb.lang.NonNull;

import java.util.HashSet;
import java.util.Set;

public class RoleDTO {
    private ERole roleName;
    @NonNull
    public ERole getRoleName() {
        return roleName;
    }

    public void setRoleName(ERole roleName) {
        this.roleName = roleName;
    }
}
