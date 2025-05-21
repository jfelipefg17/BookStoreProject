package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.entities.Client;
import com.BookStore.projectBookStore.repositories.LikeRepository;
import com.BookStore.projectBookStore.repositories.BookRepository;
import com.BookStore.projectBookStore.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    // Métodos generales
    public void createLike(Like like) {
        likeRepository.save(like);
    }

    public List<Like> findAllLikes() {
        return likeRepository.findAll();
    }

    public Like findLikeById(Long id) throws Exception {
        Optional<Like> likeOptional = likeRepository.findById(id);
        if (likeOptional.isPresent()) {
            return likeOptional.get();
        } else {
            throw new Exception("Like not found with ID: " + id);
        }
    }

    public void deleteLike(Long id) throws Exception {
        if (likeRepository.existsById(id)) {
            likeRepository.deleteById(id);
        } else {
            throw new Exception("Cannot delete. Like not found with ID: " + id);
        }
    }
      // Métodos para dar like a un libro
    @Transactional
    public void toggleLikeBook(Integer bookId, Client client) throws Exception {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Libro no encontrado con ID: " + bookId));
        
        // Verificar si el usuario ya dio like al libro
        boolean hasLiked = likeRepository.existsByBookAndClient(book, client);
        
        if (hasLiked) {
            // Si ya dio like, eliminarlo (unlike)
            List<Like> likes = likeRepository.findByBook(book);
            for (Like like : likes) {
                if (like.getClient() != null && like.getClient().getId() == client.getId()) {
                    likeRepository.delete(like);
                    break;
                }
            }
        } else {
            // Si no ha dado like, crearlo
            Like like = new Like(book);
            like.setClient(client);
            likeRepository.save(like);
        }
    }
    
    // Métodos para dar like a una reseña
    @Transactional
    public void toggleLikeReview(Long reviewId, Client client) throws Exception {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("Reseña no encontrada con ID: " + reviewId));
        
        // Verificar si el usuario ya dio like a la reseña
        boolean hasLiked = likeRepository.existsByReviewAndClient(review, client);
        
        if (hasLiked) {
            // Si ya dio like, eliminarlo (unlike)
            List<Like> likes = likeRepository.findByReview(review);
            for (Like like : likes) {
                if (like.getClient() != null && like.getClient().getId() == client.getId()) {
                    likeRepository.delete(like);
                    break;
                }
            }
        } else {
            // Si no ha dado like, crearlo
            Like like = new Like(review);
            like.setClient(client);
            likeRepository.save(like);
        }
    }
      // Método para verificar si un usuario ya dio like a un libro
    public boolean hasUserLikedBook(Integer bookId, Client client) throws Exception {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Libro no encontrado con ID: " + bookId));
        return likeRepository.existsByBookAndClient(book, client);
    }
    
    // Método para verificar si un usuario ya dio like a una reseña
    public boolean hasUserLikedReview(Long reviewId, Client client) throws Exception {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("Reseña no encontrada con ID: " + reviewId));
        return likeRepository.existsByReviewAndClient(review, client);
    }
    
    // Método para contar likes de un libro
    public int countBookLikes(Integer bookId) throws Exception {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Libro no encontrado con ID: " + bookId));
        return likeRepository.findByBook(book).size();
    }
    
    // Método para contar likes de una reseña
    public int countReviewLikes(Long reviewId) throws Exception {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("Reseña no encontrada con ID: " + reviewId));
        return likeRepository.findByReview(review).size();
    }
}