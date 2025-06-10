package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;

public class Empresa {
    private String idEmpresa;
    private String nombre;
    private String email;
    private String password;
    private String localizacion;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Empresa(String idEmpresa, String nombre, String password,String email ,String localizacion) {
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.localizacion = localizacion;
    }

    public Empresa() {

    }
}
