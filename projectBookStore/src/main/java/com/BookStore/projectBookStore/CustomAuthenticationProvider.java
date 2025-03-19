package com.BookStore.projectBookStore;

import com.BookStore.projectBookStore.services.ClientService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final ClientService clientService;

    public CustomAuthenticationProvider(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("🔑 Clave ingresada en login: " + password);

        var userDetails = clientService.loadUserByUsername(email);

        System.out.println("🔒 Clave en la base de datos (hash): " + userDetails.getPassword());

        // Aquí Spring Security automáticamente compara el hash con la ingresada
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
