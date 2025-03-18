package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.repositories.BookRepository;
import com.BookStore.projectBookStore.repositories.LikeRepository;
import com.BookStore.projectBookStore.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LikeRepository likeRepository;

    // Agregar una reseña al libro
    @PostMapping
    public String addReview(@PathVariable Integer bookId, @RequestParam String name, @RequestParam String description) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Review review = new Review(name, description, book);
            // No es necesario establecer likes a 0, ya que la lista ya está inicializada vacía.
            reviewRepository.save(review);
        }
        return "redirect:/books/" + bookId;
    }


    @GetMapping("/{reviewId}/edit")
    public String editReviewForm(@PathVariable Integer bookId, @PathVariable Long reviewId, Model model) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        model.addAttribute("review", review);
        model.addAttribute("bookId", bookId);
        return "review/edit";
    }

    @PostMapping("/{reviewId}/update")
    public String updateReview(@PathVariable Integer bookId, @PathVariable Long reviewId,
                               @RequestParam("name") String name,
                               @RequestParam("description") String description) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setName(name);
        review.setDescription(description);
        reviewRepository.save(review);
        return "redirect:/books/" + bookId;
    }

    // Endpoint para eliminar una reseña
    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Integer bookId, @PathVariable Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return "redirect:/books/" + bookId;
    }

    // Endpoint para dar like a una reseña
    @PostMapping("/{reviewId}/like")
    public String likeReview(@PathVariable Integer bookId, @PathVariable Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        // Crear y guardar el like asociado a la reseña (sin asignar un usuario)
        Like like = new Like(review);
        likeRepository.save(like);
        // Agregar el like a la lista de likes de la reseña
        review.getLikes().add(like);
        reviewRepository.save(review);
        return "redirect:/books/" + bookId;
    }

    

}
