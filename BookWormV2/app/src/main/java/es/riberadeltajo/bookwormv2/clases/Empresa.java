package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;

public class Empresa {
    private String nombre;
    private String password;
    private int codEmpresa;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(int codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public Empresa(String nombre, String password, int codEmpresa, ArrayList<Libro> libros) {
        this.nombre = nombre;
        this.password = password;
        this.codEmpresa = codEmpresa;
    }

    public Empresa() {

    }
}
