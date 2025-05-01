package com.example.gestionpeliculasseries.models;

public class Serie {
    private int id;
    private String titulo;
    private int temporadas;
    // ... otros campos locales ...

    public Serie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public char[] getNumTemporadas() {
        return String.valueOf(temporadas).toCharArray();

    }

    // ... otros getters y setters ...
}