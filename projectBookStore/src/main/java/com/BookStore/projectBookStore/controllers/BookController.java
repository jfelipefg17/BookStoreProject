package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.*;
import com.BookStore.projectBookStore.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;

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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ClientService clientService;    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model, Locale locale) {
        if (userDetails != null) {
            // Verificar si tenemos un ClientUserDetails
            if (userDetails instanceof com.BookStore.projectBookStore.security.ClientUserDetails) {
                com.BookStore.projectBookStore.security.ClientUserDetails clientDetails = 
                    (com.BookStore.projectBookStore.security.ClientUserDetails) userDetails;
                
                String clientName = clientDetails.getClientName();
                model.addAttribute("username", clientName);
                
                System.out.println("Usuario autenticado (ClientUserDetails): " + clientName);
            } else {
                // Usar el nombre de usuario directamente si no es un ClientUserDetails
                String username = userDetails.getUsername();
                model.addAttribute("username", username);
                
                System.out.println("Usuario autenticado (username): " + username);
            }
            
            // No añadir los atributos de depuración cuando el usuario está autenticado
        } else {
            // Usar messageSource para obtener "Invitado" en el idioma actual
            String guestName = messageSource.getMessage("user.guest", null, locale);
            model.addAttribute("username", guestName);
            
            // Añadir atributos de depuración para verificar la internacionalización
            model.addAttribute("currentLocale", locale.toString());
            model.addAttribute("localizedGuest", guestName);
            model.addAttribute("welcomeMessage", messageSource.getMessage("index.welcomeUser", new Object[]{guestName}, locale));
            
            System.out.println("Usuario no autenticado: " + guestName);
        }
        return "index";
    }

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
                             @RequestParam String authorName,
                             @RequestParam String publisherName,
                             @RequestParam String categoryName,
                             @RequestParam(required = false) List<Like> likes,
                             @RequestParam(required = false) List<Review> reviews,
                             RedirectAttributes redirectAttributes) {
        try {
            // Buscar o crear Autor
            Author author = authorService.findByName(authorName);
            if (author == null) {
                author = new Author();
                author.setName(authorName);
                author = authorService.save(author); // Guarda y obtiene el ID
            }

            // Buscar o crear Editorial
            Publisher publisher = publisherService.findByName(publisherName);
            if (publisher == null) {
                publisher = new Publisher();
                publisher.setName(publisherName);
                publisher = publisherService.save(publisher);
            }

            // Buscar o crear Categoría
            Category category = categoryService.findByName(categoryName);
            if (category == null) {
                category = new Category();
                category.setName(categoryName);
                category = categoryService.save(category);
            }            // Crear el libro con los objetos creados
            bookService.createBook(title, stock, price, image, author, publisher, category, likes, reviews);

            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("book.create.success", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("book.create.error", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
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
    }    @GetMapping("/listBook")
    public String listBook(@RequestParam(required = false) Integer id, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            if (id == null) {
                redirectAttributes.addFlashAttribute("error", messageSource.getMessage("book.id.required", null, LocaleContextHolder.getLocale()));
                return "redirect:/book/listBooks";
            }
            
            Book book = bookService.findById(id);
            if (book == null) {
                redirectAttributes.addFlashAttribute("error", messageSource.getMessage("book.notFound.withId", new Object[]{id}, LocaleContextHolder.getLocale()));
                return "redirect:/book/listBooks";
            }
            modelMap.addAttribute("book", book);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
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

            if (author == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid author.");
                return "redirect:/book/modifyBook?id=" + id;
            }
            if (publisher == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid publisher.");
                return "redirect:/book/modifyBook?id=" + id;
            }
            if (category == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid category.");
                return "redirect:/book/modifyBook?id=" + id;
            }            bookService.modifyBook(id, title, stock, price, image, author, publisher, category, likes, reviews);
            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("book.update.success", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("book.update.error", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
        }
        return "redirect:/book/listBooks";
    }    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("success", messageSource.getMessage("book.delete.success", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("book.delete.error", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
        }
        return "redirect:/book/listBooks";
    }

    @GetMapping("/findByName")
    public String findByName(@RequestParam(required = false) String title, ModelMap modelMap) {
        try {
            if (title != null && !title.isEmpty()) {
                Book book = bookService.findByTitle(title);                if (book != null) {
                    modelMap.addAttribute("book", book);
                } else {
                    modelMap.addAttribute("error", messageSource.getMessage("book.notFound", null, LocaleContextHolder.getLocale()));
                }
            }
        } catch (Exception e) {
            modelMap.addAttribute("error", messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
        }
        return "findByName";
    }
    @GetMapping("/moreReviews")
    public String listBooksByReviews(ModelMap modelMap) {
        try {
            List<Book> books = bookService.searchAllBookOrderedByReviews();
            modelMap.addAttribute("books", books);
        } catch (Exception e) {
            modelMap.addAttribute("error", messageSource.getMessage("error.listing.reviews", 
                                          new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
        }
        return "moreReviewedBooks";
    }

}