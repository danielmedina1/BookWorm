package es.riberadeltajo.bookwormv2.recyclerviews.libros;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.usuario.mostrarlibro.MostrarLibroFragment;

/**
 * A fragment representing a list of Items.
 */
public class LibrosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static LibrosFragment l;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LibrosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LibrosFragment newInstance(int columnCount) {
        LibrosFragment fragment = new LibrosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_libros_list, container, false);



        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            l = LibrosFragment.this;

            ListaLibros.miAdaptador = new MyLibrosRecyclerViewAdapter(ListaLibros.libros, getActivity());
            recyclerView.setAdapter(ListaLibros.miAdaptador);
        }
        return view;
    }

    public static void iniciarLibro(String nombre, String isbn, String autor, String empresa,
                                    String sinopsis, int stock, float valoracion, double precio) {
        MostrarLibroFragment.tituloLibro = nombre;
        MostrarLibroFragment.isbnLibro = isbn;
        MostrarLibroFragment.autorLibro = autor;
        MostrarLibroFragment.empresaLibro = empresa;
        MostrarLibroFragment.sinopsisLibro = sinopsis;
        MostrarLibroFragment.stockLibro = stock;
        MostrarLibroFragment.valoracionLibro = valoracion;
        MostrarLibroFragment.precioLibro = precio;
        NavHostFragment.findNavController(l).navigate(R.id.action_nav_buscar_to_nav_mostrarLibro);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ListaLibros.miAdaptador.notifyDataSetChanged();
    }
}