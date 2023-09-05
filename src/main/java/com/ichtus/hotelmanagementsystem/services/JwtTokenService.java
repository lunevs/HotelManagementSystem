package com.ichtus.hotelmanagementsystem.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;

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
////            byte[] keyBytes = Decoders.BASE64.decode("...secret...");
////            Key k = Keys.hmacShaKeyFor(keyBytes);
//            Jwts.parserBuilder().setSigningKey(k).build().parseClaimsJws(token);
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException jwtVerificationException) {
            throw new JWTVerificationException(token);
        }
    }
}
