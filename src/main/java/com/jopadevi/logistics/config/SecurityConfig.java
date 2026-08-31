package com.jopadevi.logistics.config;

import com.jopadevi.logistics.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }


    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http,

            JwtAuthenticationFilter jwtAuthenticationFilter,

            CorsConfigurationSource corsConfigurationSource

    ) throws Exception {

        http

            .csrf(csrf -> csrf.disable())

            .cors(cors ->
                    cors.configurationSource(
                            corsConfigurationSource
                    )
            )

            .httpBasic(httpBasic ->
                    httpBasic.disable()
            )

            .formLogin(form ->
                    form.disable()
            )

            .logout(logout ->
                    logout.disable()
            )

            .sessionManagement(session ->

                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(

                            "/api/auth/login",

                            "/api/auth/register"

                    )
                    .permitAll()

                    .requestMatchers(

                            org.springframework.http.HttpMethod.OPTIONS,

                            "/**"

                    )
                    .permitAll()

                    .anyRequest()

                    .authenticated()

            )

            .addFilterBefore(

                    jwtAuthenticationFilter,

                    UsernamePasswordAuthenticationFilter.class

            );


        return http.build();

    }

}