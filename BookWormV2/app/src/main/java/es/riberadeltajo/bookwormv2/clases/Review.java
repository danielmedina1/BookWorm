package es.riberadeltajo.bookwormv2.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Review implements Parcelable {
    private String desc;
    private Date publicacion;
    private String libro;
    private float puntuacion;
    private String usuario;
    private String codUser;
    private String codLibro;

    public Review(String desc, Date publicacion, String libro, float puntuacion, String usuario, String codUser) {
        this.desc = desc;
        this.publicacion = publicacion;
        this.libro = libro;
        this.puntuacion = puntuacion;
        this.usuario = usuario;
        this.codUser = codUser;
    }


    public Review(String libro) {
        this.libro = libro;
    }

    protected Review(Parcel in) {
        desc = in.readString();
        libro = in.readString();
        puntuacion = in.readFloat();
        usuario = in.readString();
        codUser = in.readString();
        codLibro = in.readString();
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

    public String getCodUser() {
        return codUser;
    }

    public void setCodUser(String codUser) {
        this.codUser = codUser;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Date publicacion) {
        this.publicacion = publicacion;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeString(libro);
        dest.writeFloat(puntuacion);
        dest.writeString(usuario);
        dest.writeString(codUser);
        dest.writeString(codLibro);
    }
}
