package es.riberadeltajo.bookwormv2.recyclerviews.libros;

import android.content.Context;

import java.util.ArrayList;

import es.riberadeltajo.bookwormv2.clases.Libro;


public class ListaLibros {
    public static ArrayList<Libro> libros = new ArrayList<>();
    public static MyLibrosRecyclerViewAdapter miAdaptador;
    public static Context contextoLibro;
    public static String tituloLibro;
}
