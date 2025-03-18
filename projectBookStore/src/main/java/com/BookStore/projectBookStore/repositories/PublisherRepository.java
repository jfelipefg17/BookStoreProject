package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.Author;
import com.BookStore.projectBookStore.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    // Find-search all publishers
    List<Publisher> findAll();

    // Find-search publisher by id
    Optional<Publisher> findById(Integer id);

    // Save-Update publisher
    Author save(Publisher publisher);

    // Delete publisher
    void deleteById(Integer id);
}
