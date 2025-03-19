package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.services.RegisterService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    private final RegisterService registerService;

    public ClientController(RegisterService registerService) {
        this.registerService = registerService;
    }

    // Página de login
    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home"; // Si ya está autenticado, redirigir a home
        }
        return "login";
    }

    // Página de registro
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Procesar registro
    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password) {
        registerService.registerUser(name, email, password);
        return "redirect:/login?success"; // Redirige al login después de registrar
    }
}
