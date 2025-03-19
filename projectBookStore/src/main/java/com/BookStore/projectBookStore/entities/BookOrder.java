package com.BookStore.projectBookStore.entities;


import jakarta.persistence.*;

@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    //TODO fijarme en todos las relaciones netre table al parecer es onetomany
}
