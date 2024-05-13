package com.example.ecomerce_bookstore.jwt;

import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {
    Claims extractClaims(String token);
    boolean invalidToken(String token);
    String generateToken(String email);
    Key accessKey(String keyString);

}
