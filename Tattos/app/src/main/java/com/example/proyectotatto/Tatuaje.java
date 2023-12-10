package com.example.proyectotatto;

public class Tatuaje {
    private long id;
    private String nombre;
    private String categoria;
    private String imagen;

    // Constructor

    public Tatuaje(){

    }

    public Tatuaje(long id, String nombre, String categoria, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;

        this.imagen = imagen;
    }

    // Getters y setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
