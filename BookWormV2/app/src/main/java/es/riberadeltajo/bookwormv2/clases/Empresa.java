package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;

public class Empresa {
    private String nombre;
    private String password;
    private int codEmpresa;
    private ArrayList<Libro> libros;
    private ArrayList<Peticion> peticiones;

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

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

    public ArrayList<Peticion> getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(ArrayList<Peticion> peticiones) {
        this.peticiones = peticiones;
    }

    public Empresa(String nombre, String password, int codEmpresa, ArrayList<Libro> libros, ArrayList<Peticion> peticiones) {
        this.nombre = nombre;
        this.password = password;
        this.codEmpresa = codEmpresa;
        this.libros = libros;
        this.peticiones = peticiones;
    }

    public Empresa() {

    }
}
