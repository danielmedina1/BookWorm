package es.riberadeltajo.bookwormv2.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Libro implements Parcelable {
    private String nombre;
    private String autor;
    private String sinopsis;
    private float puntuacion;
    private double precio;
    private long isbn;
    private int stock;
    private String empresa;
    private String idLibro;

    public Libro(String nombre, String autor, String sinopsis, float puntuacion, double precio, long isbn, int stock, String empresa, String idLibro) {
        this.nombre = nombre;
        this.autor = autor;
        this.sinopsis = sinopsis;
        this.puntuacion = puntuacion;
        this.precio = precio;
        this.isbn = isbn;
        this.stock = stock;
        this.empresa = empresa;
        this.idLibro = idLibro;
    }

    public Libro(String nombre, String autor, double precio, long isbn, String empresa, String sinopsis, float valoracion, int stock, String idLibro) {
        this.nombre = nombre;
        this.autor = autor;
        this.precio = precio;
        this.isbn = isbn;
        this.empresa = empresa;
        this.sinopsis = sinopsis;
        this.puntuacion = valoracion;
        this.stock = stock;
        this.idLibro = idLibro;
    }

    public Libro() {
    }

    protected Libro(Parcel in) {
        nombre = in.readString();
        sinopsis = in.readString();
        puntuacion = in.readFloat();
        precio = in.readDouble();
        isbn = in.readInt();
        empresa = in.readString();
    }

    public static final Creator<Libro> CREATOR = new Creator<Libro>() {
        @Override
        public Libro createFromParcel(Parcel in) {
            return new Libro(in);
        }

        @Override
        public Libro[] newArray(int size) {
            return new Libro[size];
        }
    };

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
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

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(sinopsis);
        dest.writeDouble(puntuacion);
        dest.writeDouble(precio);
        dest.writeLong(isbn);
    }
}
