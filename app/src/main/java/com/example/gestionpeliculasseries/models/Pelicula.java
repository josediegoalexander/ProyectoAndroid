package com.example.gestionpeliculasseries.models;

public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private int anio; // Agrega el campo para el año

    public Pelicula() {
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    // Getter y Setter para el año (¡Asegúrate de tener estos!)
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}