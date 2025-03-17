package es.riberadeltajo.bookwormv2.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Libro implements Parcelable {
    private String nombre;
    private String autor;
    private String sinopsis;
    private double puntuacion;
    private ArrayList<Review> reviews;
    private double precio;
    private int isbn;
    private String empresa;

    public Libro(String nombre, String autor, String sinopsis, double puntuacion, ArrayList<Review> reviews, double precio, int isbn, String empresa) {
        this.nombre = nombre;
        this.autor = autor;
        this.sinopsis = sinopsis;
        this.puntuacion = puntuacion;
        this.reviews = reviews;
        this.precio = precio;
        this.isbn = isbn;
        this.empresa = empresa;
    }

    public Libro(String nombre, String autor, double precio, int isbn, String empresa) {
        this.nombre = nombre;
        this.autor = autor;
        this.precio = precio;
        this.isbn = isbn;
        this.empresa = empresa;
    }

    public Libro() {
    }

    protected Libro(Parcel in) {
        nombre = in.readString();
        sinopsis = in.readString();
        puntuacion = in.readDouble();
        reviews = in.createTypedArrayList(Review.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(sinopsis);
        dest.writeDouble(puntuacion);
        dest.writeTypedList(reviews);
        dest.writeDouble(precio);
        dest.writeInt(isbn);
    }
}
