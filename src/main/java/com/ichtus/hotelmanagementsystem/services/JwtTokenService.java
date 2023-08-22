package com.ichtus.hotelmanagementsystem.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JwtTokenService {

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    private final Algorithm hmac256;
    private final JWTVerifier verifier;


    public JwtTokenService(@Value("${jwt.secret}") final String jwtSecret) {
        this.hmac256 = Algorithm.HMAC256(jwtSecret);
        this.verifier = JWT.require(this.hmac256).build();
    }

    public String generateToken(UserDetails userDetails) {
        log.info("generateToken");
//        Map<String, Object> claims = new HashMap<>();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .toList();
//        claims.put("roles", roles);
//        Date issuedDate = new Date();
//        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
//
//        Key mySecretKey = new SecretKeySpec(jwtSecret.getBytes(), "HS256");
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(issuedDate)
//                .setExpiration(expiredDate)
//                .signWith(mySecretKey, SignatureAlgorithm.HS256)
//                .compact();

        Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(jwtLifetime.toMillis()))
                .sign(this.hmac256);
    }

    public String validateTokenAndGetUsername(String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException jwtVerificationException) {
            log.warn("token invalid: {}", jwtVerificationException.getMessage());
            return null;
        }
    }

//    public String getUsername(String token) {
//        return getAllClaimsFromToken(token).getSubject();
//    }
//
//    public List<String> getRoles(String token) {
//        return getAllClaimsFromToken(token).get("roles", List.class);
//    }

//    private Claims getAllClaimsFromToken(String token) {
//        Key mySecretKey = new SecretKeySpec(jwtSecret.getBytes(), "HS256");
//        return Jwts.parserBuilder()
//                .setSigningKey(mySecretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }

}
