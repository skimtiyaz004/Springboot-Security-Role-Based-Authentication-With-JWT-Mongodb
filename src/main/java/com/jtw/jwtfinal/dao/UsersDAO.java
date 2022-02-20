package com.jtw.jwtfinal.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jtw.jwtfinal.model.Users;

public interface UsersDAO extends MongoRepository<Users, String> {

    Users findByUsername(String username);
}
