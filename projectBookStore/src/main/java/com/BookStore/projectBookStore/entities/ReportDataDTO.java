package com.BookStore.projectBookStore.entities;

public class ReportDataDTO {
    private String title;
    private String author;
    private String publisher;
    private String category;
    private double price;
    private String client;
    private Long pedidoId;
    private String usuarioPedido;
    private String pagoPedido;
    private boolean descuentoPedido;
    private String items; // <-- Agregado para reflejar el campo de Pedido

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

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public String getUsuarioPedido() { return usuarioPedido; }
    public void setUsuarioPedido(String usuarioPedido) { this.usuarioPedido = usuarioPedido; }

    public String getPagoPedido() { return pagoPedido; }
    public void setPagoPedido(String pagoPedido) { this.pagoPedido = pagoPedido; }

    public boolean isDescuentoPedido() { return descuentoPedido; }
    public void setDescuentoPedido(boolean descuentoPedido) { this.descuentoPedido = descuentoPedido; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
}
