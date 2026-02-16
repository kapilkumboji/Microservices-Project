package com.example.AuthService.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.security.*;
import java.util.*;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.contains("/login")
                || path.contains("/register");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException, java.io.IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("Token missing");
            return;
        }

        String token = header.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.setStatus(401);
            response.getWriter().write("Invalid token");
            return;
        }

        String role = jwtUtil.extractRole(token);

        if (request.getMethod().equals("DELETE")
                && !role.equals("ADMIN")) {

            response.setStatus(403);
            response.getWriter().write("Only ADMIN can delete");
            return;
        }

        chain.doFilter(request, response);
    }
}