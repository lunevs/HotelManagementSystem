package com.ichtus.hotelmanagementsystem.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Logging filter configuration file. Write each request to LOGGER.
 * Logger file defines in application.properties
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "RequestCachingFilter", urlPatterns = "/*")
public class RequestCachingFilter extends OncePerRequestFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestCachingFilter.class);

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/upload")) {
            filterChain.doFilter(request, response);
            return;
        }

        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
        String s = new String(cachedHttpServletRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        LOGGER.info(request.getMethod() + " " + request.getRequestURI() + " " + s.replaceAll("\n", ""));
        filterChain.doFilter(cachedHttpServletRequest, response);
    }
}
