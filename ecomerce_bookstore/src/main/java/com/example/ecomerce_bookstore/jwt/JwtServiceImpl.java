package com.example.ecomerce_bookstore.jwt;

import com.example.ecomerce_bookstore.exceptions.BaseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{
    private final UserDetailsService userDetailsService;
    @Value("${jwt_secret}")
    private String secret;
    @Override
    public Key accessKey(String keyString){
        byte[] key = Decoders.BASE64.decode(keyString);
        return Keys.hmacShaKeyFor(key);
    }
    @Override
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extracAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    private Claims extracAllClaims(String token){
        Claims claims = null;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(accessKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"Token expiration");
        }catch (UnsupportedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token is not support");
        }catch (MalformedJwtException e){
            throw  new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "invalid format 3 part of token");
        }catch (SignatureException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid token");
        }catch (Exception e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), e.getLocalizedMessage());
        }
        return claims;
    }
    private String extractEmail(String token){
        return extractClaims(token,Claims::getSubject);
    }

    @Override
    public boolean invalidToken(String token) {
        final String email = extractEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return !ObjectUtils.isEmpty(userDetails);
    }

    @Override
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(5*60*60)))
                .signWith(accessKey(secret),SignatureAlgorithm.HS256)
                .compact();
    }
}
