package com.BookStore.projectBookStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "users")
public class Client {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    @PrimaryKeyJoinColumn
    private int id = 0;
    private String name = "";
    private String email = "";
    private String password = "";
    private String role = "";
    @jakarta.persistence.OneToMany
    private List<BookOrder> bookOrders = new ArrayList<>();
    private List<Book> FavBooks = new ArrayList<>();

}
