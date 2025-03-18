package com.BookStore.projectBookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Permite todas las solicitudes sin autenticación
                )
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF si no lo necesitas
                .formLogin(login -> login.disable()) // Desactiva el formulario de login
                .httpBasic(basic -> basic.disable()); // Desactiva la autenticación básica

        return http.build();
    }
}
