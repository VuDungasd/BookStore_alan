package com.example.ecomerce_bookstore.services;

import java.util.Optional;
import java.util.stream.Collectors;

import com.example.ecomerce_bookstore.config.UserInfoConfig;
import com.example.ecomerce_bookstore.entities.User;
import com.example.ecomerce_bookstore.exceptions.ResourceNotFoundException;
import com.example.ecomerce_bookstore.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    // tìm kiếm ngừoi dùng bằng email và trả về UserDetails tương ứng
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(username);
        UserInfoConfig userInfoConfig = new UserInfoConfig();
        userInfoConfig.setEmail(user.getEmail());
        userInfoConfig.setPassword(user.getPassword());
        userInfoConfig.setAuthorities(user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList()));
        return userInfoConfig;
    }
}