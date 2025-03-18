package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public void createLike(Like like) {
        likeRepository.save(like);
    }

    public List<Like> findAllLikes() {
        return likeRepository.findAll();
    }

    public Like findLikeById(int id) throws Exception {
        Optional<Like> likeOptional = likeRepository.findById(id);
        if (likeOptional.isPresent()) {
            return likeOptional.get();
        } else {
            throw new Exception("Like not found with ID: " + id);
        }
    }

    public void deleteLike(int id) throws Exception {
        if (likeRepository.existsById(id)) {
            likeRepository.deleteById(id);
        } else {
            throw new Exception("Cannot delete. Like not found with ID: " + id);
        }
    }
}