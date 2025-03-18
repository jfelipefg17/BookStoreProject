package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Like;
import com.BookStore.projectBookStore.services.BookService;
import com.BookStore.projectBookStore.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    // Inyección del repositorio de likes para la funcionalidad de "like"
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
                             @RequestParam String author,
                             @RequestParam String publisher,
                             @RequestParam String category,
                             @RequestParam(required = false) Boolean likes,
                             RedirectAttributes redirectAttributes) {

        try {
            bookService.createBook(title, stock, price, image, author, publisher, category, likes);
            redirectAttributes.addFlashAttribute("success", "The book was successfully uploaded");
            return "redirect:/book/listBooks";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating book: " + e.getMessage());
            return "redirect:/book/formBook";
        }
    }

    // Listar todos los libros, con opción de ordenarlos por cantidad de reviews mediante un parámetro
    @GetMapping("/listBooks")
    public String listBooks(@RequestParam(value = "orderByReviews", required = false) Boolean orderByReviews,
                            ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            List<Book> books;
            if (orderByReviews != null && orderByReviews) {
                books = bookService.searchAllBookOrderedByReviews();
            } else {
                books = bookService.searchAllBook();
            }
            if (books == null || books.isEmpty()) {
                modelMap.addAttribute("message", "There are no books");
            }
            modelMap.addAttribute("books", books);
            return "listBooks";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error reloading books: " + e.getMessage());
            return "redirect:/index";
        }
    }

    // Mostrar los detalles de un libro específico
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

    // Formulario para editar un libro
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

    // Actualizar la modificación del libro
    @PostMapping("/modifyBook")
    public String modifyBook(@RequestParam Integer id,
                             @RequestParam String title,
                             @RequestParam int stock,
                             @RequestParam double price,
                             @RequestParam String image,
                             @RequestParam String author,
                             @RequestParam String publisher,
                             @RequestParam String category,
                             @RequestParam(required = false) boolean likes,
                             RedirectAttributes redirectAttributes,
                             ModelMap modelMap) throws Exception {
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

    // Eliminar un libro
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            if (bookService.findById(id) == null) {
                redirectAttributes.addFlashAttribute("error", "The book does not exist or has not been deleted.");
                return "redirect:/book/listBooks";
            }
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", "The book was successfully deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/book/listBooks";
    }


    @PostMapping("/{id}/like")
    public String likeBook(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.addLikeToBook(id);
            redirectAttributes.addFlashAttribute("success", "Like added to book successfully");
            return "redirect:/book/listBook?id=" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/book/listBooks";
        }
    }
}
