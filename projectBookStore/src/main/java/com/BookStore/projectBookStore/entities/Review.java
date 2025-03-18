package com.BookStore.projectBookStore.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Nombre del usuario que hace el comentario
    private String description;

    // Relación OneToMany con la entidad Like
    // Ajusta 'mappedBy' y 'cascade' según tu diseño de base de datos.
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Constructor vacío para JPA
    public Review() {}

    public Review(String name, String description, Book book) {
        this.name = name;
        this.description = description;
        this.book = book;
        // No asignes 'this.likes = 0;' porque es una lista, no un número
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Like> getLikes() {
        return likes;
    }
    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public int getLikeCount() {
        return this.likes.size();
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
}
