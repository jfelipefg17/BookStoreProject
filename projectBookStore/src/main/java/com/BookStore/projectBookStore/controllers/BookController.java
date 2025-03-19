package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.*;
import com.BookStore.projectBookStore.repositories.BookRepository;
import com.BookStore.projectBookStore.repositories.CategoryRepository;
import com.BookStore.projectBookStore.services.*;
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
    private CategoryService categoryService;

    @GetMapping("/formBook")
    public String bookForm(ModelMap modelMap) {
        try {
            modelMap.addAttribute("authors", authorService.searchAllAuthors());
            modelMap.addAttribute("publishers", publisherService.searchAllPublisher());
            modelMap.addAttribute("categories", categoryService.searchAllCategories());
        } catch (Exception e) {
            modelMap.addAttribute("error", "Error loading form: " + e.getMessage());
        }
        return "create";
    }

    @PostMapping("/createBook")
    public String createBook(@RequestParam String title,
                             @RequestParam int stock,
                             @RequestParam double price,
                             @RequestParam String image,
                             @RequestParam Integer authorId,
                             @RequestParam Integer publisherId,
                             @RequestParam Integer categoryId,
                             @RequestParam(required = false) List<Like> likes,
                             @RequestParam(required = false) List<Review> reviews,
                             RedirectAttributes redirectAttributes) {
        try {
            Author author = authorService.findById(authorId);
            Publisher publisher = publisherService.findById(publisherId);
            Category category = categoryService.findById(categoryId);

            if (author == null || publisher == null || category == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid author, publisher, or category.");
                return "redirect:/book/formBook";
            }

            bookService.createBook(title, stock, price, image, author, publisher, category, likes, reviews);
            redirectAttributes.addFlashAttribute("success", "The book was successfully uploaded");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating book: " + e.getMessage());
        }
        return "redirect:/book/listBooks";
    }

    @GetMapping("/listBooks")
    public String listBooks(ModelMap modelMap) {
        try {
            List<Book> books = bookService.searchAllBook();
            modelMap.addAttribute("books", books);
        } catch (Exception e) {
            modelMap.addAttribute("error", "Error listing books: " + e.getMessage());
        }
        return "listBooks";
    }

    @GetMapping("/listBook")
    public String listBook(@RequestParam Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", "Book not found with id: " + id);
                return "redirect:/book/listBooks";
            }
            modelMap.addAttribute("book", book);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving book: " + e.getMessage());
        }
        return "book";
    }

    @GetMapping("/modifyBook")
    public String showModifyBookForm(@RequestParam Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", "Book not found.");
                return "redirect:/book/listBooks";
            }
            modelMap.addAttribute("book", book);
            modelMap.addAttribute("authors", authorService.searchAllAuthors());
            modelMap.addAttribute("publishers", publisherService.searchAllPublisher());
            modelMap.addAttribute("categories", categoryService.searchAllCategories());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading modification form: " + e.getMessage());
        }
        return "modify";
    }

    @PostMapping("/modifyBook")
    public String modifyBook(@RequestParam Integer id,
                             @RequestParam String title,
                             @RequestParam int stock,
                             @RequestParam double price,
                             @RequestParam String image,
                             @RequestParam Integer authorId,
                             @RequestParam Integer publisherId,
                             @RequestParam Integer categoryId,
                             @RequestParam(required = false) List<Like> likes,
                             @RequestParam(required = false) List<Review> reviews,
                             RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", "Book not found.");
                return "redirect:/book/listBooks";
            }

            Author author = authorService.findById(authorId);
            Publisher publisher = publisherService.findById(publisherId);
            Category category = categoryService.findById(categoryId);

            if (author == null || publisher == null || category == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid author, publisher, or category.");
                return "redirect:/book/modifyBook?id=" + id;
            }

            bookService.modifyBook(id, title, stock, price, image, author, publisher, category, likes, reviews);
            redirectAttributes.addFlashAttribute("success", "The book was successfully modified.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error modifying book: " + e.getMessage());
        }
        return "redirect:/book/listBooks";
    }

    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", "The book was successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/book/listBooks";
    }

    @GetMapping("/searchBook")
    public String findByName(@RequestParam(required = false) String title, ModelMap modelMap) {
        try {
            if (title != null && !title.isEmpty()) {
                Book book = bookService.findByTitle(title);
                if (book != null) {
                    modelMap.addAttribute("book", book);
                } else {
                    modelMap.addAttribute("error", "No book found with that title.");
                }
            }
        } catch (Exception e) {
            modelMap.addAttribute("error", "Error searching for book: " + e.getMessage());
        }
        return "findByName";
    }

    @GetMapping("/category/{categoryId}")
    public String getBooksByCategory(@PathVariable int categoryId, ModelMap modelMap) {
        try {
            List<Book> books = bookService.findBooksByCategory(categoryId);
            modelMap.addAttribute("books", books);
        } catch (Exception e) {
            modelMap.addAttribute("error", "Error retrieving books by category: " + e.getMessage());
        }
        return "listBooks";
    }

}