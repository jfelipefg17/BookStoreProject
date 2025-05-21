package com.BookStore.projectBookStore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controlador para manejar redirecciones entre rutas antiguas y nuevas
 */
@Controller
public class RedirectController {

    /**
     * Redirecciona desde la vista antigua de libro a la nueva
     * Usamos una ruta diferente para evitar conflictos con el controlador existente
     */
    @GetMapping("/redirect/book")
    public RedirectView redirectToNewBookView(@RequestParam Integer id) {
        return new RedirectView("/books/" + id);
    }
}
