package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAll();
    Optional<Like> findById(Long id);
    Like save(Like like);
    void deleteById(Long id);
    
    // Métodos personalizados para encontrar likes
    List<Like> findByBook(Book book);
    List<Like> findByReview(Review review);
    List<Like> findByClient(Client client);
    
    // Verificar si un usuario ya dio like a un libro o reseña
    boolean existsByBookAndClient(Book book, Client client);
    boolean existsByReviewAndClient(Review review, Client client);
    
    // Eliminar un like específico
    void deleteByBookAndClient(Book book, Client client);
    void deleteByReviewAndClient(Review review, Client client);
}