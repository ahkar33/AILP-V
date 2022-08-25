package com.ace.ailpv.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.User;
import com.ace.ailpv.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        
        User user = userService.getUserById(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        // return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities);
        
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
        
            
    }
}
