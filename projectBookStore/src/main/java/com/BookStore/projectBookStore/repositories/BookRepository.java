package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Métodos básicos ya existentes
    List<Book> findAll();
    List<Book> findByCategory(Category category);
    Optional<Book> findById(Integer id);
    Book save(Book book);
    void deleteById(Integer id);

    // Método personalizado para ordenar libros por cantidad de reviews (de mayor a menor)
    @Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b.id ORDER BY COUNT(r) DESC")
    List<Book> findAllOrderByReviewCountDesc();
  
    // find-search book by name
    Book findByTitle(String title);

}
