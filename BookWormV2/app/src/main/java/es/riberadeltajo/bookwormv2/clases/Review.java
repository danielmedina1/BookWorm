package es.riberadeltajo.bookwormv2.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Review implements Parcelable {
    private Usuario usuario;
    private Libro libro;
    private float puntuacion;
    private String desc;
    private Date publicacion;



    public Review(Usuario usuario, Libro libro, float puntuacion, String desc, Date publicacion) {
        this.usuario = usuario;
        this.libro = libro;
        this.puntuacion = puntuacion;
        this.desc = desc;
        this.publicacion = publicacion;
    }

    public Review() {

    }

    protected Review(Parcel in) {
        puntuacion = in.readFloat();
        desc = in.readString();
    }



    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Date publicacion) {
        this.publicacion = publicacion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeFloat(puntuacion);
        dest.writeString(desc);

    }
}
