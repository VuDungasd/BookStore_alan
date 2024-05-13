package com.example.ecomerce_bookstore.services;

import java.util.Optional;

import com.example.ecomerce_bookstore.config.UserInfoConfig;
import com.example.ecomerce_bookstore.entities.User;
import com.example.ecomerce_bookstore.exceptions.ResourceNotFoundException;
import com.example.ecomerce_bookstore.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    // tìm kiếm ngừoi dùng bằng email và trả về UserDetails tương ứng
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(username);

        return user.map(UserInfoConfig::new).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
    }
}