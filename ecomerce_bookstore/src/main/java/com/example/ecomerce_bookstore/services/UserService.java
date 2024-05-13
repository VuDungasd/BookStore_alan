package com.example.ecomerce_bookstore.services;


import com.example.ecomerce_bookstore.entities.User;
import com.example.ecomerce_bookstore.payloads.UserDTO;
import com.example.ecomerce_bookstore.payloads.UserResponse;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    String deleteUser(Long userId);

    User find(String username);
}
