package com.example.proyectotatto;

public class Tatuaje {
    //private long id;
    private String nombre;
    private String categoria;
    private String imagen;
    private double precio;
    private String descripcion;

    // Constructor

    public Tatuaje(){

    }

    public Tatuaje(String nombre, String categoria, String imagen, double precio, String descripcion) {

        this.nombre = nombre;
        this.categoria = categoria;
        this.imagen = imagen;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    // Getters y setters

    /*
    public long getId() {
        return id;
    }

    public void setId(long id) {
       this.id = id;
    }
    */

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
