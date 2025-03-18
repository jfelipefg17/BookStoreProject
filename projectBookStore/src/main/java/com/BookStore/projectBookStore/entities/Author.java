package com.BookStore.projectBookStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;


@Entity
@jakarta.persistence.Table(name = "authors")
public class Author {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    @PrimaryKeyJoinColumn
    private int id = 0;
    private String name = "";

    public Author() {}

    public Author(int i, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
