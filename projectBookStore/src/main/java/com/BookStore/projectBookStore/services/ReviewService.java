package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Métodos básicos CRUD
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) throws Exception {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            return reviewOptional.get();
        } else {
            throw new Exception("Review not found with ID: " + id);
        }
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    // Métodos específicos
    public List<Review> findByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    public int countReviewsByBook(Book book) {
        return reviewRepository.countByBook(book);
    }
}
