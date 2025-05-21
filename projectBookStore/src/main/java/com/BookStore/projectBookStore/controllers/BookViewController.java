package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Client;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.services.BookService;
import com.BookStore.projectBookStore.services.ClientService;
import com.BookStore.projectBookStore.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para manejar la visualización de detalles de libros con likes
 */
@Controller
@RequestMapping("/books")
public class BookViewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ClientService clientService;

    /**
     * Método para mostrar detalles de un libro incluyendo información de likes
     */
    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Integer id, Model model, Authentication authentication) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                model.addAttribute("error", "Libro no encontrado");
                return "error";
            }

            model.addAttribute("book", book);
            
            // Si el usuario está autenticado, verificar sus likes
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername();
                Client client = clientService.getClientByEmail(email);
                
                if (client != null) {
                    // Verificar si el usuario ha dado like al libro
                    boolean userLikedBook = likeService.hasUserLikedBook(id, client);
                    model.addAttribute("userLikedBook", userLikedBook);
                    
                    // Verificar qué reseñas ha dado like el usuario
                    List<Long> userLikedReviews = new ArrayList<>();
                    if (book.getReviews() != null) {
                        for (Review review : book.getReviews()) {
                            if (likeService.hasUserLikedReview(review.getId(), client)) {
                                userLikedReviews.add(review.getId());
                            }
                        }
                    }
                    model.addAttribute("userLikedReviews", userLikedReviews);
                }
            }

            return "book";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar detalles del libro: " + e.getMessage());
            return "error";
        }
    }
}
