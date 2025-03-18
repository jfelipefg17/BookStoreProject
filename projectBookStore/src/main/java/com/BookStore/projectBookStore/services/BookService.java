package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.Book;
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
    public void createBook(String title, int stock, double price, String image, String author, String publisher, String category, Boolean likes) {

        Book book = new Book();
        book.setTitle(title);
        book.setStock(stock);
        book.setPrice(price);
        book.setImage(image);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setLikes(likes);
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
    public void modifyBook(Integer id, String title, int stock, double price, String image, String author, String publisher, String category, boolean likes) throws Exception {

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

    // Find book by name
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

}