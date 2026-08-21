package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.User;
import com.jopadevi.logistics.security.JwtService;
import com.jopadevi.logistics.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    private final JwtService jwtService;


    public AuthController(
            AuthService authService,
            JwtService jwtService) {

        this.authService = authService;

        this.jwtService = jwtService;
    }


    /* =================================
       REGISTER USER
    ================================= */

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        try {

            User user = authService.createUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getRole()
            );


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            new AuthResponse(
                                    user.getUsername(),
                                    user.getRole(),
                                    "User created successfully",
                                    null
                            )
                    );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    e.getMessage()
                            )
                    );
        }
    }


    /* =================================
       LOGIN
    ================================= */

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try {

            User user = authService.login(
                    request.getUsername(),
                    request.getPassword()
            );


            /*
             * Generate JWT token
             */

            String token =
                    jwtService.generateToken(
                            user.getUsername(),
                            user.getRole()
                    );


            return ResponseEntity.ok(

                    new AuthResponse(
                            user.getUsername(),
                            user.getRole(),
                            "Login successful",
                            token
                    )

            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ErrorResponse(
                                    e.getMessage()
                            )
                    );
        }
    }


    /* =================================
       LOGIN REQUEST
    ================================= */

    public static class LoginRequest {

        private String username;

        private String password;


        public LoginRequest() {
        }


        public String getUsername() {

            return username;
        }


        public void setUsername(
                String username) {

            this.username = username;
        }


        public String getPassword() {

            return password;
        }


        public void setPassword(
                String password) {

            this.password = password;
        }
    }


    /* =================================
       REGISTER REQUEST
    ================================= */

    public static class RegisterRequest {

        private String username;

        private String password;

        private String role;


        public RegisterRequest() {
        }


        public String getUsername() {

            return username;
        }


        public void setUsername(
                String username) {

            this.username = username;
        }


        public String getPassword() {

            return password;
        }


        public void setPassword(
                String password) {

            this.password = password;
        }


        public String getRole() {

            return role;
        }


        public void setRole(
                String role) {

            this.role = role;
        }
    }


    /* =================================
       AUTH RESPONSE
    ================================= */

    public static class AuthResponse {

        private String username;

        private String role;

        private String message;

        private String token;


        public AuthResponse(
                String username,
                String role,
                String message,
                String token) {

            this.username = username;

            this.role = role;

            this.message = message;

            this.token = token;
        }


        public String getUsername() {

            return username;
        }


        public String getRole() {

            return role;
        }


        public String getMessage() {

            return message;
        }


        public String getToken() {

            return token;
        }
    }


    /* =================================
       ERROR RESPONSE
    ================================= */

    public static class ErrorResponse {

        private String message;


        public ErrorResponse(
                String message) {

            this.message = message;
        }


        public String getMessage() {

            return message;
        }
    }
}