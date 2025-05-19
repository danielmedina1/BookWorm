package es.riberadeltajo.bookwormv2.clases;

public class Carrito {
    public String libro;
    public double precio;

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Carrito(String libro, double precio) {
        this.libro = libro;
        this.precio = precio;
    }


}
