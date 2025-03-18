package com.BookStore.projectBookStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "client")
    private List<BookOrder> bookOrders;
    //private List<Book> FavBooks = new ArrayList<>();

}
