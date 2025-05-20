package com.BookStore.projectBookStore.entities;

public class ReportDataDTO {
    private String title;
    private String author;
    private String publisher;
    private String category;
    private double price;
    private String client;
    private BookOrder order; // Nuevo campo

    // Getters y setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public BookOrder getOrder() { return order; }
    public void setOrder(BookOrder order) { this.order = order; }
}
