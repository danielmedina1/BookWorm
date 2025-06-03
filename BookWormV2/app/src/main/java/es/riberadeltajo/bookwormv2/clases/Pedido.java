package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    private ArrayList libros;
    private double precioTotal;
    private Date fecha;
    private int estado;
    private String codUser;
    private String codPed;

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

    public String getCodUser() {
        return codUser;
    }

    public void setCodUser(String codUser) {
        this.codUser = codUser;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCodPed() {
        return codPed;
    }

    public void setCodPed(String codPed) {
        this.codPed = codPed;
    }

    public Pedido(ArrayList libros, double precioTotal, Date fecha, int estado, String codUser) {
        this.libros = libros;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
        this.estado = estado;
        this.codUser = codUser;
    }
    public Pedido(ArrayList libros, double precioTotal, Date fecha, int estado, String codUser, String codPed) {
        this.libros = libros;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
        this.estado = estado;
        this.codUser = codUser;
        this.codPed = codPed;
    }
}
