package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findAll();
    Optional<Like> findById(Integer id);
    Like save(Like like);
    void deleteById(Integer id);
}