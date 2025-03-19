package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.*;
import com.BookStore.projectBookStore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Book service
@Service
public class BookService {

    // Book repository injection
    @Autowired
    private BookRepository bookRepository;

    //Create book
    public void createBook(String title, int stock, double price, String image, Author author, Publisher publisher, Category category, List<Like>likes, List<Review>reviews) {

        Book book = new Book();
        book.setTitle(title);
        book.setStock(stock);
        book.setPrice(price);
        book.setImage(image);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setLikes(likes);
        book.setReviews(reviews);
        bookRepository.save(book);
    }

    //Read-Search all books
    public List<Book> searchAllBook() {
        return bookRepository.findAll();
    }

    //Read-Search specific book by ID
    public Book findById(int id) throws Exception {

        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            return bookOptional.get();

        } else {
            throw new Exception("Book not found with ID: " + id);
        }
    }

    //Update-Modify Book
// Update-Modify Book
    public void modifyBook(Integer id, String title, int stock, double price, String image, Author author, Publisher publisher, Category category, List<Like> likes, List<Review> reviews) throws Exception {

        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(title);
            book.setStock(stock);
            book.setPrice(price);
            book.setImage(image);
            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setCategory(category);
            book.setLikes(likes);
            book.setReviews(reviews);
            bookRepository.save(book);
        } else {
            throw new Exception("Cannot modify. Book not found with ID: " + id);
        }
    }


    // Delete Book
    public void deleteBook(Integer id) throws Exception {

        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);

        } else {
            throw new Exception("Cannot delete. Book not found with ID: " + id);
        }
    }

    // Añadir un método específico para dar like
    public void addLikeToBook(Integer bookId) throws Exception {
        Book book = findById(bookId);
        Like like = new Like(book);
        book.getLikes().add(like);
        bookRepository.save(book);
    }

    public int countLikesForBook(Integer bookId) throws Exception {
        Book book = findById(bookId);
        return book.getLikes().size();
    }

    public List<Book> searchAllBookOrderedByReviews() {
        // Find bokk by counting reviews
        return bookRepository.findAllOrderByReviewCountDesc();

    }
    // Find book by name
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

}