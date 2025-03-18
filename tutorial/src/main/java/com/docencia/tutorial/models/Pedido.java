package com.docencia.tutorial.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private String items;
    private String pago;
    private boolean descuento;

    // Constructores
    public Pedido() { }

    public Pedido(String usuario, String items, String pago, boolean descuento) {
        this.usuario = usuario;
        this.items = items;
        this.pago = pago;
        this.descuento = descuento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }
}

