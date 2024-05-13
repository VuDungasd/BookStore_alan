package com.example.ecomerce_bookstore.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTUtil {

    // chuỗi key này được lấy trong file properties (file cấu hình)
    // chuỗi này chỉ server mới biết!
    @Value("${jwt_secret}")
    private String secret;

    // tạo ra 1 token mới
    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        // bắt đầu tạo 1 jwt mới
        return JWT.create()
                .withSubject("User Details") // chủ đề của JWT
                .withClaim("email", email) // thêm một claim vào jwt // thêm email vào jwt
                .withIssuedAt(new Date()) // thêm thời điểm khởi tạo jwt
                .withIssuer("Quan") // thiet lập ngừoi phát hành jwt
                .sign(Algorithm.HMAC256(secret)); // ký jwt sử dụng thuật toán HMAC256 sử dụng thuật scretKey.
    }

    // xác thực token và xác minh tính hợp lệ của token
    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
//        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJRdWFuIiwiaWF0IjoxNzE1NTYzMzY5LCJlbWFpbCI6ImxvZGFAZ21haWwuY29tIn0.Vf4RJ55mk_7kUe5XuPgzmkSLkofbsvgvQtqsUTmUpIw";
        // tạo một đối tượng
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Event Scheduler").build();

        DecodedJWT jwt = verifier.verify(token); // xác minh tính hợp lệ của jwt
        System.out.println("token loi: " + jwt);
        // nếu token không hợp lệ thì ngoại lệ JWTVeriticationException được trả ra!
        System.out.println("email tra lai: " + jwt.getClaim("email").asString());

        return jwt.getClaim("email").asString(); // trả về email nếu xác minh thành công!
    }
}