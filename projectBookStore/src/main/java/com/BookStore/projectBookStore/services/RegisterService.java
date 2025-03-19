package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Client;
import com.BookStore.projectBookStore.entities.Role;
import com.BookStore.projectBookStore.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegisterService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String name, String email, String password) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña

        // Asignar directamente el rol USER sin necesidad de buscar en la base de datos
        client.setRole(Role.USER);

        clientRepository.save(client);
    }

}
