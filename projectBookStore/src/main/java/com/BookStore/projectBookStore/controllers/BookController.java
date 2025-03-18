package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//Book controller
@Controller
@RequestMapping("/book")
public class BookController {

    //bookService injection
    @Autowired
    private BookService bookService;


    //Form to create a book
    @GetMapping("/formBook")
    public String bookForm(ModelMap modelMap) {
        return "create";
    }


    //Create a book
    @PostMapping("/createBook")
    public String createBook(@RequestParam String title, @RequestParam int stock, @RequestParam double price, @RequestParam String image, @RequestParam String author, @RequestParam String publisher, @RequestParam String category, @RequestParam(required = false) Boolean likes, RedirectAttributes redirectAttributes) {

        try {
            bookService.createBook(title, stock, price, image, author, publisher, category, likes);
            redirectAttributes.addFlashAttribute("success", "The book was successfully uploaded");
            return "redirect:/book/listBooks";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating book: " + e.getMessage());
            return "redirect:/book/formBook";
        }
    }


    //Read-List the books
    @GetMapping("/listBooks")
    public String listBooks(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {
            List<Book> books = bookService.searchAllBook();
            if (books == null || books.isEmpty()) {
                modelMap.addAttribute("message", "There are no books");
            }
            modelMap.addAttribute("books", books);
            return "listBooks";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error for reloading book : " + e.getMessage());
            return "redirect:/index";
        }
    }


    //Read-List a specific book
    @GetMapping("/listBook")
    public String listBook(@RequestParam Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {
            Book book = bookService.findById(id);
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", "Book not found with id: " + id);
                return "redirect:/book/listBooks";
            }
            modelMap.addAttribute("book", book);
            return "book";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error getting the book: " + e.getMessage());
            return "redirect:/book/listBooks";
        }
    }


    // Form with data for edit or modify
    @GetMapping("/modifyBook")
    public String showModifyBookForm(@RequestParam Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {
            Book book = bookService.findById(id);
            modelMap.addAttribute("book", book);
            return "modify";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/book/listBooks";
        }
    }

    // Update-modify the book
    @PostMapping("/modifyBook")
    public String modifyBook(@RequestParam Integer id, @RequestParam String title, @RequestParam int stock, @RequestParam double price, @RequestParam String image, @RequestParam String author, @RequestParam String publisher, @RequestParam String category, @RequestParam(required = false) boolean likes, RedirectAttributes redirectAttributes, ModelMap modelMap) throws Exception {

        try {
            bookService.modifyBook(id, title, stock, price, image, author, publisher, category, likes);
            redirectAttributes.addFlashAttribute("success", "The book was successfully modified.");
            return "redirect:/book/listBooks";

        } catch (Exception e) {
            modelMap.put("error", e.getMessage());
            modelMap.addAttribute("book", bookService.findById(id));
            return "modify";
        }
    }


    //Delete a book
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Integer id, RedirectAttributes redirectAttributes) {

        try {
            if (bookService.findById(id) == null) {
                redirectAttributes.addFlashAttribute("error", "The book does not exist or has not been deleted.");
                return "redirect:/book/listBooks";
            }

            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", "the book was successfully deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "error: " + e.getMessage());
        }
        return "redirect:/book/listBooks";
    }

    // Method for search book by name
    @GetMapping("/findByName")
    public String findByName(@RequestParam(required = false) String title, ModelMap modelMap) {
        if (title != null && !title.isEmpty()) {
            Book book = bookService.findByTitle(title);
            if (book != null) {
                modelMap.addAttribute("book", book);
            } else {
                modelMap.addAttribute("error", "No se encontró un libro con ese nombre.");
            }
        }
        
        return "findByName";
    }



}