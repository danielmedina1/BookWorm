package es.riberadeltajo.bookwormv2.usuario.mostrarlibro;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.clases.Review;
import es.riberadeltajo.bookwormv2.databinding.FragmentMostrarLibroBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.carrito.ListaCarrito;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.ListaReseñas;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.MyReseñasRecyclerViewAdapter;
import es.riberadeltajo.bookwormv2.usuario.inicio.InicioViewModel;
import es.riberadeltajo.bookwormv2.usuario.reseñas.ResenasFragment;

public class MostrarLibroFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String idLibro = new String();
    public static String tituloLibro = new String();
    public static String isbnLibro = new String();
    public static String autorLibro = new String();
    public static String empresaLibro = new String();
    public static String sinopsisLibro = new String();
    public static int stockLibro = 0;
    public static float valoracionLibro = 0.0f;
    public static float p = 0.0f;
    public static double precioLibro = 0;
    private FragmentMostrarLibroBinding binding;
    private static MostrarLibroFragment ml;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ml = MostrarLibroFragment.this;
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentMostrarLibroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button bres = root.findViewById(R.id.reseñaLibro);
        Button bcarrito = root.findViewById(R.id.carritoLibro);
        cargarReseñas(tituloLibro, root);
        MyReseñasRecyclerViewAdapter.ruta = R.id.action_nav_mostrarLibro_to_nav_usuarios;

        TextView tit = root.findViewById(R.id.tituloLibro);
        TextView aut = root.findViewById(R.id.autorLibro);
        TextView is = root.findViewById(R.id.isbnLibro);
        TextView empr = root.findViewById(R.id.empresaLibro);
        TextView sin = root.findViewById(R.id.sinopsisLibro);
        TextView sto = root.findViewById(R.id.stockLibro);

        TextView pre = root.findViewById(R.id.precioLibro);



        if (tit != null) {
            tit.setText(tituloLibro);
            aut.setText(autorLibro);
            is.setText(isbnLibro);
            empr.setText(empresaLibro);
            sin.setText(sinopsisLibro);
            sto.setText("Hay " + stockLibro + " en stock");
            pre.setText("Precio: " + precioLibro + "€");
        }


            if (stockLibro >= 1) {
                bcarrito.setText("Añadir a pedidos");
                int color = ContextCompat.getColor(requireContext(), R.color.red);
                bcarrito.setBackgroundColor(color);
            } else {
                bcarrito.setText("Sin stock");
                int color = ContextCompat.getColor(requireContext(), R.color.impred);
                bcarrito.setBackgroundColor(color);
                bcarrito.setEnabled(false);
            }


        bcarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stockLibro >= 1) {
                    stockLibro = stockLibro - 1;
                    db.collection("Usuarios").document(InicioSesion.codusuario).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot d = task.getResult();
                            ArrayList librosPedidos = (ArrayList) d.get("carrito");
                            if (!librosPedidos.contains(d.getId())) {
                                librosPedidos.add(idLibro);
                                db.collection("Usuarios").document(InicioSesion.codusuario)
                                        .update("carrito", librosPedidos);
                            }
                            db.collection("Libros").document(idLibro).update("stock", stockLibro);


                        }
                    });
                } else {
                    bcarrito.setText("Sin stock");
                    int color = ContextCompat.getColor(requireContext(), R.color.black);
                    bcarrito.setBackgroundColor(color);
                    bcarrito.setEnabled(false);
                }
            }
        });

        bres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResenasFragment.tituloLibro = tituloLibro;
                ResenasFragment.idLibro = idLibro;
                ResenasFragment.usuario = InicioSesion.nombreusuario;
                NavHostFragment.findNavController(ml).navigate(R.id.action_nav_mostrarLibro_to_nav_resenasLibros);
            }
        });

        return root;
    }

    private void cargarReseñas(String b, View root) {
        RatingBar val = root.findViewById(R.id.raitingLibro);
        val.setIsIndicator(true);

        db.collection("Reviews")
                .whereEqualTo("libro", idLibro)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ListaReseñas.reseñas.clear();
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                String descripcion = document.getData().get("descripcion") + "";
                                Timestamp ts = (Timestamp) document.getData().get("fecha");
                                Date fecha = ts.toDate();
                                String libro = document.getData().get("libro") + "";
                                String usuario = document.getData().get("usuario") + "";
                                String coduser = document.getData().get("codusuario") + "";
                                float valoracion = Float.parseFloat(document.getData().get("puntuacion") + "");
                                ListaReseñas.reseñas.add(new Review(descripcion, fecha, libro, valoracion, usuario, coduser));
                                val.setRating(calcularPuntuacion(b));
                                ListaReseñas.miAdaptador.notifyDataSetChanged();

                            }
                        } else {
                            Log.d("ERROR", "---Error al conseguir los datos---");
                        }
                    }
                });
    }

    private float calcularPuntuacion(String b) {

            db.collection("Reviews")
                    .whereEqualTo("libro", b)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                p = 0.0f;
                                int r = 0;
                                for(QueryDocumentSnapshot document : task.getResult()) {
                                    float valoracion = Float.parseFloat(document.getData().get("puntuacion") + "");
                                    p = p + valoracion;
                                    r++;
                                    Log.d("P V", ""+p);
                                }
                                p = p/r;
                            }
                            Log.d("P FINAL", ""+p);
                        }

                    });
            return p;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}