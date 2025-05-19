package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.dto.GoogleBookDTO;
import com.BookStore.projectBookStore.services.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/googlebooks")
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    @Autowired
    public GoogleBooksController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    // Muestra el formulario de búsqueda
    @GetMapping("/form")
    public String showSearchForm() {
        return "books/searchForm"; // Ruta a la vista del formulario
    }

    // Procesa la búsqueda y muestra resultados
    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<GoogleBookDTO> books = googleBooksService.searchBooks(query);
        model.addAttribute("books", books);
        model.addAttribute("query", query);
        return "books/searchResults"; // Vista con los resultados
    }
}
