package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.repositories.CategoryRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
        // Implementación de ordenamiento por reviews
        return bookRepository.findAllOrderByReviewCountDesc();

    }
    // Find book by name
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }


    public void sellBook(int bookId, int quantity) throws Exception {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (book.getStock() >= quantity) {
                book.setStock(book.getStock() - quantity);
                bookRepository.save(book);
                generateFactura(book, quantity);
            } else {
                throw new Exception("Not enough stock for book ID: " + bookId);
            }
        } else {
            throw new Exception("Book not found with ID: " + bookId);
        }
    }

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Book> findBooksByCategory(int categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new Exception("Category not found with ID: " + categoryId));
        return bookRepository.findByCategory(category);
    }

    private void generateFactura(Book book, int quantity) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Factura_" + book.getId() + ".pdf"));
            document.open();
            document.add(new Paragraph("Factura"));
            document.add(new Paragraph("Book Title: " + book.getTitle()));
            document.add(new Paragraph("Quantity: " + quantity));
            document.add(new Paragraph("Price per unit: " + book.getPrice()));
            document.add(new Paragraph("Total: " + (book.getPrice() * quantity)));
        } catch (FileNotFoundException | DocumentException e) {
            // Log the error
            System.err.println("Error generating PDF: " + e.getMessage());
            // Optionally, rethrow the exception or handle it as needed
        } finally {
            document.close();
        }
    }

}