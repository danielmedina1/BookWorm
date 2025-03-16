package es.riberadeltajo.bookwormv2.clases;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String apellidos;
    private String username;
    private String password;
    private String email;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Usuario> siguiendo;
    private ArrayList<Usuario> seguidores;


    public Usuario() {

    }

    public Usuario(String nombre, String apellidos, String username, String password, String email, ArrayList<Pedido> pedidos, ArrayList<Usuario> siguiendo, ArrayList<Usuario> seguidores) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.email = email;
        this.pedidos = pedidos;
        this.siguiendo = siguiendo;
        this.seguidores = seguidores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public ArrayList<Usuario> getSiguiendo() {
        return siguiendo;
    }

    public void setSiguiendo(ArrayList<Usuario> siguiendo) {
        this.siguiendo = siguiendo;
    }

    public ArrayList<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(ArrayList<Usuario> seguidores) {
        this.seguidores = seguidores;
    }
}
