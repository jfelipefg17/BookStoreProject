package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Author;
import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Publisher;
import com.BookStore.projectBookStore.repositories.LikeRepository;
import com.BookStore.projectBookStore.services.AuthorService;
import com.BookStore.projectBookStore.services.BookService;
import com.BookStore.projectBookStore.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private LikeRepository likeRepository;

    // Formulario para crear un libro
    @GetMapping("/formBook")
    public String bookForm(ModelMap modelMap) {
        return "create";
    }

    // Crear un libro
    @PostMapping("/createBook")
    public String createBook(@RequestParam String title,
                             @RequestParam int stock,
                             @RequestParam double price,
                             @RequestParam String image,
                             @RequestParam Integer authorId,
                             @RequestParam Integer publisherId,
                             @RequestParam String category,
                             @RequestParam(required = false) Boolean likes,
                             RedirectAttributes redirectAttributes) {
        try {
            Author author = authorService.findById(authorId);
            Publisher publisher = publisherService.findById(publisherId);

            if (author == null || publisher == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid author or publisher.");
                return "redirect:/book/formBook";
            }

            bookService.createBook(title, stock, price, image, author, publisher, category, likes != null && likes);
            redirectAttributes.addFlashAttribute("success", "The book was successfully uploaded.");
            return "redirect:/book/listBooks";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating book: " + e.getMessage());
            return "redirect:/book/formBook";
        }
    }

    // Listar todos los libros
    @GetMapping("/listBooks")
    public String listBooks(ModelMap modelMap) {
        List<Book> books = bookService.searchAllBook();
        if (books == null || books.isEmpty()) {
            modelMap.addAttribute("message", "There are no books.");
        }
        modelMap.addAttribute("books", books);
        return "listBooks";
    }

    // Mostrar detalles de un libro específico
    @GetMapping("/listBook")
    public String listBook(@RequestParam Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        Book book = bookService.findById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Book not found with id: " + id);
            return "redirect:/book/listBooks";
        }
        modelMap.addAttribute("book", book);
        return "book";
    }

    // Formulario para modificar un libro
    @GetMapping("/modifyBook")
    public String showModifyBookForm(@RequestParam Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        Book book = bookService.findById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Book not found.");
            return "redirect:/book/listBooks";
        }
        modelMap.addAttribute("book", book);
        return "modify";
    }

    // Modificar un libro
    @PostMapping("/modifyBook")
    public String modifyBook(@RequestParam Integer id,
                             @RequestParam String title,
                             @RequestParam int stock,
                             @RequestParam double price,
                             @RequestParam String image,
                             @RequestParam Integer authorId,
                             @RequestParam Integer publisherId,
                             @RequestParam String category,
                             @RequestParam(required = false) Boolean likes,
                             RedirectAttributes redirectAttributes) {

        try {
            Book existingBook = bookService.findById(id);
            if (existingBook == null) {
                redirectAttributes.addFlashAttribute("error", "The book does not exist.");
                return "redirect:/book/listBooks";
            }

            Author author = authorService.findById(authorId);
            Publisher publisher = publisherService.findById(publisherId);

            if (author == null || publisher == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid author or publisher.");
                return "redirect:/book/modifyBook?id=" + id;
            }

            bookService.modifyBook(id, title, stock, price, image, author, publisher, category, likes != null && likes);
            redirectAttributes.addFlashAttribute("success", "The book was successfully modified.");
            return "redirect:/book/listBooks";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error modifying book: " + e.getMessage());
            return "redirect:/book/modifyBook?id=" + id;
        }
    }

    // Eliminar un libro
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", "The book does not exist or has already been deleted.");
                return "redirect:/book/listBooks";
            }

            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", "The book was successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/book/listBooks";
    }

    // Buscar un libro por título
    @GetMapping("/findByName")
    public String findByName(@RequestParam(required = false) String title, ModelMap modelMap) {
        if (title != null && !title.isEmpty()) {
            Book book = bookService.findByTitle(title);
            if (book != null) {
                modelMap.addAttribute("book", book);
            } else {
                modelMap.addAttribute("error", "No book found with that title.");
            }
        }
        return "findByName";
    }

    // Dar like a un libro
    @PostMapping("/{id}/like")
    public String likeBook(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.addLikeToBook(id);
            redirectAttributes.addFlashAttribute("success", "Like added to book successfully.");
            return "redirect:/book/listBook?id=" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding like: " + e.getMessage());
            return "redirect:/book/listBooks";
        }
    }
}