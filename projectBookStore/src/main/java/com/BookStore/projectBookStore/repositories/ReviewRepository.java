package com.BookStore.projectBookStore.repositories;
import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    // Find-search all reviews
    List<Review> findAll();

    // Find-search review by id
    Optional<Review> findById(Long id);

    // Save-Update review
    Review save(Review review);

    // Delete review
    void deleteById(Long id);
    
    // Find reviews by book
    List<Review> findByBook(Book book);
    
    // Count reviews by book
    int countByBook(Book book);
}
