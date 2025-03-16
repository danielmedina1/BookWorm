package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;

public class Libro {
    private String nombre;
    private String sinopsis;
    private double puntuacion;
    private ArrayList<Review> reviews;
    private double precio;
    private int isbn;
    private Empresa empresa;

    public Libro(String nombre, String sinopsis, double puntuacion, ArrayList<Review> reviews, double precio, int isbn, Empresa empresa) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.puntuacion = puntuacion;
        this.reviews = reviews;
        this.precio = precio;
        this.isbn = isbn;
        this.empresa = empresa;
    }

    public Libro() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
