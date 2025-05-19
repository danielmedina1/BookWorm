package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;

public class Pedido {
    private ArrayList libros;
    private double precioTotal;
    private String emaiUser;

    public ArrayList getLibros() {
        return libros;
    }

    public void setLibros(ArrayList libros) {
        this.libros = libros;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEmaiUser() {
        return emaiUser;
    }

    public void setEmaiUser(String emaiUser) {
        this.emaiUser = emaiUser;
    }

    public Pedido(ArrayList libros, double precioTotal, String emaiUser) {
        this.libros = libros;
        this.precioTotal = precioTotal;
        this.emaiUser = emaiUser;
    }
}
