package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Client;
import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.services.BookService;
import com.BookStore.projectBookStore.services.ClientService;
import com.BookStore.projectBookStore.services.LikeService;
import com.BookStore.projectBookStore.services.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private ReviewService reviewService;/**
     * Método para dar/quitar like a un libro
     */    @PostMapping("/books/{bookId}/like")
    @ResponseBody
    public ResponseEntity<?> toggleBookLike(@PathVariable Integer bookId, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername();
                Client client = clientService.getClientByEmail(email);
                
                if (client != null) {
                    // Toggle like (añadir o quitar)
                    boolean wasLiked = likeService.hasUserLikedBook(bookId, client);
                    likeService.toggleLikeBook(bookId, client);
                    
                    // Devolver el nuevo estado
                    boolean isNowLiked = !wasLiked;
                    int likeCount = likeService.countBookLikes(bookId);
                    
                    response.put("success", true);
                    response.put("liked", isNowLiked);
                    response.put("likeCount", likeCount);
                    
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("error", "Usuario no encontrado");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("success", false);
                response.put("error", "Debe iniciar sesión para dar like");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Método para dar/quitar like a una reseña
     */    @PostMapping("/reviews/{reviewId}/like")
    @ResponseBody
    public ResponseEntity<?> toggleReviewLike(@PathVariable Long reviewId, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername();
                Client client = clientService.getClientByEmail(email);
                
                if (client != null) {
                    // Toggle like (añadir o quitar)
                    boolean wasLiked = likeService.hasUserLikedReview(reviewId, client);
                    likeService.toggleLikeReview(reviewId, client);
                    
                    // Devolver el nuevo estado
                    boolean isNowLiked = !wasLiked;
                    int likeCount = likeService.countReviewLikes(reviewId);
                    
                    response.put("success", true);
                    response.put("liked", isNowLiked);
                    response.put("likeCount", likeCount);
                    
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("error", "Usuario no encontrado");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("success", false);
                response.put("error", "Debe iniciar sesión para dar like");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
