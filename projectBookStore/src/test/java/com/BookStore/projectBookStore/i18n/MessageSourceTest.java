package com.BookStore.projectBookStore.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void testMessageSourceNotNull() {
        assertNotNull(messageSource, "MessageSource no debería ser nulo");
    }

    @Test
    public void testSpanishMessages() {
        // Probar algunos mensajes en español
        Locale spanishLocale = new Locale("es");
        assertEquals("Bienvenido, Invitado", 
               messageSource.getMessage("index.welcomeUser", new Object[]{"Invitado"}, spanishLocale),
               "El mensaje de bienvenida en español no coincide");
        
        assertEquals("Libro no encontrado.", 
               messageSource.getMessage("book.notFound", null, spanishLocale),
               "El mensaje de libro no encontrado en español no coincide");
    }

    @Test
    public void testEnglishMessages() {
        // Probar los mismos mensajes en inglés
        Locale englishLocale = new Locale("en");
        assertEquals("Welcome, Guest", 
               messageSource.getMessage("index.welcomeUser", new Object[]{"Guest"}, englishLocale),
               "El mensaje de bienvenida en inglés no coincide");
        
        assertEquals("Book not found.", 
               messageSource.getMessage("book.notFound", null, englishLocale),
               "El mensaje de libro no encontrado en inglés no coincide");
    }
}
