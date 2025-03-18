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
    private LikeRepository likeRepository; // nuevo repositorio para Like

    @PostMapping
    public String addReview(@PathVariable Integer bookId, @RequestParam String name, @RequestParam String description) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Review review = new Review(name, description, book);
            review.setLikes(0);
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
    
    // Nuevo endpoint para agregar un like a la reseña
    @PostMapping("/{reviewId}/like")
    public String likeReview(@PathVariable Integer bookId, @PathVariable Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Crear un nuevo objeto Like y asignarlo a la review (usamos entityType "review")
        Like like = new Like();
        like.setIdEntity(review.getId().intValue());
        like.setEntityType("review");
        likeRepository.save(like);

        // Actualizamos el contador de likes (por ejemplo, incrementamos el valor)
        review.setLikes(review.getLikes() + 1);
        reviewRepository.save(review);
        
        return "redirect:/books/" + bookId;
    }
}