package com.jtw.jwtfinal.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jtw.jwtfinal.model.Role;

import java.util.Optional;

public interface RoleDAO extends MongoRepository<Role, String> {

    Optional<Role> findByRoleName(String role);
}
