package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.User;
import com.jopadevi.logistics.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* ================================
       CREATE USER
    ================================= */

    public User createUser(
            String username,
            String password,
            String role) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException(
                    "Username already exists"
            );
        }

        User user = new User();

        user.setUsername(username);

        /*
         * IMPORTANT:
         * Never save the plain password.
         */
        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole(
                role == null || role.isBlank()
                        ? "USER"
                        : role
        );

        return userRepository.save(user);
    }


    /* ================================
       LOGIN
    ================================= */

    public User login(
            String username,
            String password) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid username or password"
                                )
                        );

        /*
         * Compare the entered password
         * with the encrypted password.
         */

        if (!passwordEncoder.matches(
                password,
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid username or password"
            );
        }

        return user;
    }
}