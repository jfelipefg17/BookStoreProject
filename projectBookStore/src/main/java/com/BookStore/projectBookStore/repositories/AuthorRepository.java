package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.Author;
import com.BookStore.projectBookStore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Find-search all authors
    List<Author> findAll();

    // Find-search author by id
    Optional<Author> findById(Integer id);

    // Save-Update author
    Author save(Author author);

    // Delete author
    void deleteById(Integer id);
}
