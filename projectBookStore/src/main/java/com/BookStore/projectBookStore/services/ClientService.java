package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Client;
import com.BookStore.projectBookStore.repositories.ClientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByEmail(email) // Buscar por email
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + client.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                client.getEmail(),  // Usar email como username
                client.getPassword(),
                Set.of(authority)
        );
    }
}
