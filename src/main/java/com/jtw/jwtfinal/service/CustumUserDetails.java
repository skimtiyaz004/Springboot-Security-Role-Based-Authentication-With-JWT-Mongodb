package com.jtw.jwtfinal.service;

import com.jtw.jwtfinal.dao.UsersDAO;
import com.jtw.jwtfinal.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustumUserDetails implements UserDetailsService {
    @Autowired
    private UsersDAO usersDAO;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersDAO.findByUsername(username);
        if(users==null){
            throw new UsernameNotFoundException("No User find");
        }
        Collection<SimpleGrantedAuthority> authorities=users.getRole().
                stream().map(role->new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());

        return new User(users.getUsername(),users.getPassword(),authorities);
    }
}
