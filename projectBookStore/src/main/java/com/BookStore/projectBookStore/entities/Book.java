package com.BookStore.projectBookStore.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;  // <- Corregido de "tittle" a "title"

    private int stock;
    private double price;
    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Like> likes;

    public Book() {}

    public Book(String title, int stock, double price, String image, Author author, Publisher publisher, Category category) {
        this.title = title;
        this.stock = stock;
        this.price = price;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", tittle='" + title + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", author=" + author +
                ", publisher=" + publisher +
                ", category=" + category +
                ", likes=" + likes +
                '}';
    }
}
