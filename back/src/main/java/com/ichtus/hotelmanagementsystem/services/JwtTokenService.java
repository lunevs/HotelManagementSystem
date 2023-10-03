package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.DefaultBadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

/**
 * Defines services to interact with authentication token
 * @author smlunev
 */
@Service
@Slf4j
public class JwtTokenService {

    @Value("${custom.jwt.lifetime}")
    private Duration jwtLifetime;

    @Value("${custom.jwt.secret}")
    private String jwtSecret;

    /**
     * Internal method for creating HMAC Key for given JWT secret
     * @return Key
     */
    private Key getKey() {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
            return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generate JWT token for given user
     * @param userDetails user information
     * @return string with token
     */
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Check token's validity and return username if token correct
     * @param token string with token
     * @return string with username
     */
    @Generated
    public String validateTokenAndGetUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException ex) {
            throw new DefaultBadRequestException("Invalid token: " + token);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), token);
        }
    }
}
