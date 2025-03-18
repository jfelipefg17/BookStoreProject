package com.BookStore.projectBookStore.repositories;
import com.BookStore.projectBookStore.entities.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
