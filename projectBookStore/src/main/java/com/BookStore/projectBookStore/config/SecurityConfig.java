package com.BookStore.projectBookStore.config;

import com.BookStore.projectBookStore.CustomAuthenticationProvider;
import com.BookStore.projectBookStore.services.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ClientService clientService;
    private final CustomAuthenticationProvider authenticationProvider;

    public SecurityConfig(ClientService clientService, CustomAuthenticationProvider authenticationProvider) {
        this.clientService = clientService;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/img/**", "/styles.css", "/error").permitAll() // 🔥 Permite imágenes, CSS y página de error
                        .requestMatchers("/pedidos/**").permitAll() // 🔥 Permitir acceso a pedidos para pruebas
                        .requestMatchers("/books/**").permitAll() // 🔥 Permitir acceso a libros
                        .requestMatchers("/reviews/**").permitAll() // 🔥 Permitir acceso a reseñas
                        .requestMatchers("/book/**").permitAll() // 🔥 Permitir acceso a libros (controlador original)
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/book/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .userDetailsService(clientService);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
