package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Client;
import com.BookStore.projectBookStore.repositories.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findClientByEmail(email).orElse(null);
    }    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Buscando usuario con email: " + email);

        Client client = clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        System.out.println("✅ Usuario encontrado: " + client.getEmail());
        System.out.println("👤 Nombre del cliente: " + client.getName());
        System.out.println("🔐 Contraseña en BD (hash): " + client.getPassword());

        // Usar nuestra implementación personalizada de UserDetails que incluye el nombre del cliente
        return new com.BookStore.projectBookStore.security.ClientUserDetails(client);
    }
}
