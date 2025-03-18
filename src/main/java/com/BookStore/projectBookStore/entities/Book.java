package com.BookStore.projectBookStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "books")
public class Book {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    @PrimaryKeyJoinColumn
    private int id = 0;

    private String tittle = "";

    private int stock = 0;

    private double price = 0;

    private String image = "";

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "author_id")
    private Author author;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "Category_id")
    private Category category;

    @jakarta.persistence.OneToMany
    private List<Like> likes = new ArrayList<>();



    public Book() {

    }

    public Book(int id, String tittle, int stock, double price, String image, Author author, Publisher publisher, Category category) {
        this.id = id;
        this.tittle = tittle;
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
        return tittle;
    }

    public void setTitle(String tittle) {
        this.tittle = tittle;
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
                ", tittle='" + tittle + '\'' +
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
