package com.BookStore.projectBookStore.entities;


import jakarta.persistence.*;

@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    //TODO fijarme en todos las relaciones netre table al parecer es onetomany
}
