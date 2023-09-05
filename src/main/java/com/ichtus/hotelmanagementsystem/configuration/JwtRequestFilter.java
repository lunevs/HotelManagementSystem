package com.ichtus.hotelmanagementsystem.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import com.ichtus.hotelmanagementsystem.services.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AccountService accountService;

    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    public JwtRequestFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal: " + request);

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String jwtToken = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                username = jwtTokenService.validateTokenAndGetUsername(jwtToken);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails jwtUserDetails = accountService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        jwtUserDetails,
                        null,
                        jwtUserDetails.getAuthorities()
//                    jwtTokenService.getRoles(jwtToken).stream().map(SimpleGrantedAuthority::new).toList()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            log.warn(ex.getClass().getName());
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
