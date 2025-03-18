package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.entities.Review;
import com.BookStore.projectBookStore.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    // Crea y guarda un like para un libro
    public Like likeBook(Book book) {
        Like like = new Like(book);
        return likeRepository.save(like);
    }

    // Crea y guarda un like para una reseña
    public Like likeReview(Review review) {
        Like like = new Like(review);
        return likeRepository.save(like);
    }
}
