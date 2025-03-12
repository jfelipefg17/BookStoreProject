package com.BookStore.projectBookStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "likes")
public class Like {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    @PrimaryKeyJoinColumn
    private int id = 0;
    @ManyToMany
    @jakarta.persistence.JoinTable(
        name = "user_likes",
        joinColumns = @jakarta.persistence.JoinColumn(name = "like_id"),
        inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "user_id")
    )
    private List<Client> clients = new ArrayList<>();
    private int idEntity = 0;
    private String entityType = "";
    private Date date = new Date();

    public Like() {}

    public Like(int id, List<Client> clients, int idEntity, String entityType, Date date) {
        this.id = id;
        this.clients = clients;
        this.idEntity = idEntity;
        this.entityType = entityType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Client> getUsers() {
        return clients;
    }

    public void setUsers(List<Client> clients) {
        this.clients = clients;
    }

    public int getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", users=" + clients +
                ", idEntity=" + idEntity +
                ", entityType='" + entityType + '\'' +
                ", date=" + date +
                '}';
    }
}
