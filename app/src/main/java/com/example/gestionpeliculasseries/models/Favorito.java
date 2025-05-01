package com.example.gestionpeliculasseries.models;

public class Favorito {
    private int id;
    private long userId;
    private int itemId;
    private String tipo; // "pelicula" o "serie"

    public Favorito() {
    }

    public Favorito(long userId, int itemId, String tipo) {
        this.userId = userId;
        this.itemId = itemId;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}