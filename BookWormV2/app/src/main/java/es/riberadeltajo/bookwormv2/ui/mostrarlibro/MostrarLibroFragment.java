package es.riberadeltajo.bookwormv2.ui.mostrarlibro;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.clases.Review;
import es.riberadeltajo.bookwormv2.databinding.FragmentHomeBinding;
import es.riberadeltajo.bookwormv2.databinding.FragmentMostrarLibroBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.libros.LibrosFragment;
import es.riberadeltajo.bookwormv2.recyclerviews.libros.ListaLibros;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.ListaReseñas;
import es.riberadeltajo.bookwormv2.ui.inicio.InicioViewModel;
import es.riberadeltajo.bookwormv2.ui.reseñas.ResenasFragment;

public class MostrarLibroFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String tituloLibro = new String();
    public static String isbnLibro = new String();
    public static String autorLibro = new String();
    public static String empresaLibro = new String();
    public static String sinopsisLibro = new String();
    public static int stockLibro = 0;
    public static float valoracionLibro = 0.0f;
    public static double precioLibro = 0;
    private FragmentMostrarLibroBinding binding;
    private static MostrarLibroFragment ml;
    private static float p = 0.0f;
    private static int r = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ml = MostrarLibroFragment.this;
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentMostrarLibroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button bres = root.findViewById(R.id.reseñaLibro);
        cargarReseñas(tituloLibro);

        TextView tit = root.findViewById(R.id.tituloLibro);
        TextView aut = root.findViewById(R.id.autorLibro);
        TextView is = root.findViewById(R.id.isbnLibro);
        TextView empr = root.findViewById(R.id.empresaLibro);
        TextView sin = root.findViewById(R.id.sinopsisLibro);
        TextView sto = root.findViewById(R.id.stockLibro);
        RatingBar val = root.findViewById(R.id.raitingLibro);
        TextView pre = root.findViewById(R.id.precioLibro);

        val.setIsIndicator(true);

        if (tit != null) {
            tit.setText(tituloLibro);
            aut.setText(autorLibro);
            is.setText(isbnLibro);
            empr.setText(empresaLibro);
            sin.setText(sinopsisLibro);
            sto.setText("Hay " + stockLibro + " en stock");
            val.setRating(valoracionLibro);
            pre.setText("Precio: " + precioLibro + "€");
        }


        bres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResenasFragment.tituloLibro = tituloLibro;
                ResenasFragment.usuario = InicioSesion.nombreusuario;
                NavHostFragment.findNavController(ml).navigate(R.id.action_nav_mostrarLibro_to_nav_resenasLibros);
            }
        });



        return root;
    }

    private void cargarReseñas(String b) {

        db.collection("Reviews")
                .whereEqualTo("libro", b)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                String descripcion = document.getData().get("descripcion") + "";

                                Timestamp ts = (Timestamp) document.getData().get("fecha");
                                Date fecha = ts.toDate();
                                String libro = document.getData().get("libro") + "";
                                String usuario = document.getData().get("usuario") + "";
                                float valoracion = Float.parseFloat(document.getData().get("puntuacion") + "");
                                p = p + valoracion;

                                ListaReseñas.reseñas.add(new Review(descripcion, fecha, libro, valoracion, usuario));
                                ListaReseñas.miAdaptador.notifyDataSetChanged();
                                r++;

                            }
                        } else {
                            Log.d("ERROR", "---Error al conseguir los datos---");
                        }

                    }
                });
            valoracionLibro = p/r;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}