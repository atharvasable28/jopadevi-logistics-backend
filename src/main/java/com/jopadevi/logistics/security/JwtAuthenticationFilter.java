package com.jopadevi.logistics.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {


    private final JwtService jwtService;


    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException, IOException {


        /*
         * =========================================
         * GET AUTHORIZATION HEADER
         * =========================================
         */

        String authorizationHeader =
                request.getHeader("Authorization");


        /*
         * No Authorization header.
         *
         * Login/register can continue without JWT.
         * Other protected endpoints will be rejected
         * by Spring Security.
         */

        if (authorizationHeader == null ||
                authorizationHeader.isBlank() ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }


        /*
         * =========================================
         * EXTRACT TOKEN
         * =========================================
         */

        String token =
                authorizationHeader.substring(7).trim();


        if (token.isEmpty()) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }


        try {

            /*
             * =====================================
             * VALIDATE TOKEN
             * =====================================
             */

            if (!jwtService.isTokenValid(token)) {

                SecurityContextHolder
                        .clearContext();

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }


            /*
             * =====================================
             * EXTRACT USERNAME
             * =====================================
             */

            String username =
                    jwtService.extractUsername(token);


            /*
             * =====================================
             * EXTRACT ROLE
             * =====================================
             */

            String role =
                    jwtService.extractRole(token);


            /*
             * =====================================
             * CREATE AUTHORITY
             * =====================================
             */

            String authorityRole = role;

            if (authorityRole == null ||
                    authorityRole.isBlank()) {

                authorityRole = "USER";
            }


            /*
             * Avoid ROLE_ROLE_USER
             */

            if (!authorityRole.startsWith("ROLE_")) {

                authorityRole =
                        "ROLE_" + authorityRole;
            }


            /*
             * =====================================
             * CREATE AUTHENTICATION
             * =====================================
             */

            UsernamePasswordAuthenticationToken authentication =

                    new UsernamePasswordAuthenticationToken(

                            username,

                            null,

                            List.of(
                                    new SimpleGrantedAuthority(
                                            authorityRole
                                    )
                            )
                    );


            /*
             * =====================================
             * STORE AUTHENTICATION
             * =====================================
             */

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                            authentication
                    );


        } catch (Exception e) {

            SecurityContextHolder.clearContext();

            System.out.println("JWT ERROR: " + e.getMessage());

            e.printStackTrace();
        }


        /*
         * =========================================
         * CONTINUE REQUEST
         * =========================================
         */

        filterChain.doFilter(
                request,
                response
        );
    }
}