package com.example.ecomerce_bookstore.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.ecomerce_bookstore.entities.User;
import com.example.ecomerce_bookstore.exceptions.UserNotFoundException;
import com.example.ecomerce_bookstore.payloads.LoginCredentials;
import com.example.ecomerce_bookstore.payloads.UserDTO;
import com.example.ecomerce_bookstore.security.JWTUtil;
import com.example.ecomerce_bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserDTO user) throws UserNotFoundException {
        // set mật khẩu khi được mã hoá bằng passwordEncoder (chưa sử dụng)
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
//        user.setPassword(user.getPassword());


        UserDTO userDTO = userService.registerUser(user);

        String token = jwtUtil.generateToken(userDTO.getEmail());

        return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("jwt-token", token),
                HttpStatus.CREATED);
    }

    // LoginCredentials dùng để nhân dữ liệu đầu vào từ người dùng
    // đóng gói thông tin đăng nhập từ người dùng thành một đối tượng dữ liệu có cấu trúc.
    // khởi tạo đối tượng LoginCredentials để nhận dữ liệu từ người dùng nhập vào.

//    @PostMapping("/login")
//    public Map<String, Object> loginHandler(@Valid @RequestBody LoginCredentials credentials) {
//
//        User user = userService.find(credentials.getEmail());
//
//        List<SimpleGrantedAuthority> grantedAuthorityList = user.getRoles().stream().map(
//                role -> new SimpleGrantedAuthority(role.getRoleName())
//        ).toList();
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                new UsernamePasswordAuthenticationToken(
//                        credentials.getEmail(), user.getPassword(), grantedAuthorityList
//                );
//
//        String token = jwtUtil.generateToken(credentials.getEmail());
//
//        System.out.println("token ow day: " + token);
//        return Collections.singletonMap("jwt-token", token);
//    }


    @PostMapping("/login")
    public Map<String, Object> loginHandler(@Valid @RequestBody LoginCredentials credentials) {
        //        User user = userService.find(credentials.getEmail());
//
//        // Kiểm tra mật khẩu của người dùng
//        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        List<SimpleGrantedAuthority> grantedAuthorityList = user.getRoles().stream().map(
//                role -> new SimpleGrantedAuthority(role.getRoleName())
//        ).toList();
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                new UsernamePasswordAuthenticationToken(
//                        credentials.getEmail(), credentials.getPassword(), grantedAuthorityList
//                );
//
//        String token = jwtUtil.generateToken(credentials.getEmail());
//
//        System.out.println("token ow day: " + token);
//        return Collections.singletonMap("jwt-token", token);
//    }

        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(), credentials.getPassword());

        authenticationManager.authenticate(authCredentials);

        String token = jwtUtil.generateToken(credentials.getEmail());

        return Collections.singletonMap("jwt-token", token);
    }
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> loginHandler(@Valid @RequestBody LoginCredentials credentials) {
//        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
//                credentials.getEmail(), credentials.getPassword());
//
//        try {
//            authenticationManager.authenticate(authCredentials);
//            String token = jwtUtil.generateToken(credentials.getEmail());
//            return new ResponseEntity<>(Collections.singletonMap("jwt-token", token), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(Collections.singletonMap("message", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
//        }
//    }
}