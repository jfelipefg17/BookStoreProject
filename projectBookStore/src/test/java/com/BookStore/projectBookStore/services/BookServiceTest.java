package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.entities.*;
import com.BookStore.projectBookStore.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private Author testAuthor;
    private Publisher testPublisher;
    private Category testCategory;

    @BeforeEach
    public void setup() {
        // Configurar objetos de prueba
        testAuthor = new Author();
        testAuthor.setId(1);
        testAuthor.setName("Gabriel García Márquez");

        testPublisher = new Publisher();
        testPublisher.setId(1);
        testPublisher.setName("Editorial Planeta");

        testCategory = new Category();
        testCategory.setId(1);
        testCategory.setName("Ficción");

        testBook = new Book();
        testBook.setId(1);
        testBook.setTitle("Cien años de soledad");
        testBook.setStock(10);
        testBook.setPrice(25.99);
        testBook.setImage("imagen.jpg");
        testBook.setAuthor(testAuthor);
        testBook.setPublisher(testPublisher);
        testBook.setCategory(testCategory);
        testBook.setLikes(new ArrayList<>());
        testBook.setReviews(new ArrayList<>());
    }

    @Test
    public void testFindById_ExistingBook() throws Exception {
        // Mock del comportamiento del repositorio
        when(bookRepository.findById(1)).thenReturn(Optional.of(testBook));

        // Ejecutar el método bajo prueba
        Book foundBook = bookService.findById(1);

        // Verificaciones
        assertNotNull(foundBook, "El libro no debería ser nulo");
        assertEquals("Cien años de soledad", foundBook.getTitle(), "El título del libro no coincide");
        assertEquals(testAuthor, foundBook.getAuthor(), "El autor del libro no coincide");
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void testSearchAllBooks() {
        // Mock del comportamiento del repositorio
        List<Book> mockBooks = Arrays.asList(testBook);
        when(bookRepository.findAll()).thenReturn(mockBooks);

        // Ejecutar el método bajo prueba
        List<Book> books = bookService.searchAllBook();

        // Verificaciones
        assertNotNull(books, "La lista de libros no debería ser nula");
        assertEquals(1, books.size(), "Debería haber un libro en la lista");
        assertEquals("Cien años de soledad", books.get(0).getTitle(), "El título del libro no coincide");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testFindByTitle() {
        // Mock del comportamiento del repositorio
        when(bookRepository.findByTitle("Cien años de soledad")).thenReturn(testBook);

        // Ejecutar el método bajo prueba
        Book foundBook = bookService.findByTitle("Cien años de soledad");

        // Verificaciones
        assertNotNull(foundBook, "El libro no debería ser nulo");
        assertEquals(1, foundBook.getId(), "El ID del libro no coincide");
        assertEquals("Cien años de soledad", foundBook.getTitle(), "El título del libro no coincide");
        verify(bookRepository, times(1)).findByTitle("Cien años de soledad");
    }
}
