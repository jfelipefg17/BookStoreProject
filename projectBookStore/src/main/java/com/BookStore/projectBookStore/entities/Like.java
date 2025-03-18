package com.BookStore.projectBookStore.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Por el momento, la asociación con Client es opcional
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private Client client;

    // Asociación opcional a Book (se llena solo si el like es para un libro)
    @ManyToOne(optional = true)
    @JoinColumn(name = "book_id")
    private Book book;

    // Asociación opcional a Review (se llena solo si el like es para una reseña)
    @ManyToOne(optional = true)
    @JoinColumn(name = "review_id")
    private Review review;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    public Like() {
        this.date = new Date();
    }

    // Constructor para like en un Book (por ahora no asignamos el client)
    public Like(Book book) {
        this.book = book;
        this.date = new Date();
    }

    // Constructor para like en una Review (por ahora no asignamos el client)
    public Like(Review review) {
        this.review = review;
        this.date = new Date();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
