package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//connection with database
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Find-search all books
    List<Book> findAll();

    // Find-search book by id
    Optional<Book> findById(Integer id);

    // Save-Update book
    Book save(Book book);

    // Delete book
    void deleteById(Integer id);

}
