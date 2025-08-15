package com.ashokit.serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashokit.model.User;
import com.ashokit.repository.UserRepo;

//CustomUserDetailsService.java
@Service

public class CustomUserDetailsServiceImpl implements UserDetailsService {

 @Autowired
 private UserRepo userRepo;

 @Override
 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     User user = userRepo.findByEmail(email.trim())
             .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

     return org.springframework.security.core.userdetails.User.builder()
             .username(user.getEmail())
             .password(user.getPassword()) 
             .roles(user.getRole().name()) 
             .build();
 }
} 

