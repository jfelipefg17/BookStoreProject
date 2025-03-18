package com.BookStore.projectBookStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre del usuario que hace el comentario
    private String description;
    private int likes;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Constructor vacío para JPA
    public Review() {}

    public Review(String name, String description, Book book) {
        this.name = name;
        this.description = description;
        this.book = book;
        this.likes = 0; // Inicialmente 0 likes
    }

    // Getters y Setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
