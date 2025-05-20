package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.dto.GoogleBookDTO;
import com.BookStore.projectBookStore.services.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    @Autowired
    public GoogleBooksController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @GetMapping("/searchBooks")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<GoogleBookDTO> books = googleBooksService.searchBooks(query);
        model.addAttribute("books", books);
        model.addAttribute("query", query);
        return "books/searchResults";  
    }
}

