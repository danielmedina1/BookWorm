package es.riberadeltajo.bookwormv2.empresa.gestion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentGestionBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.gestion_libros.ListaGestion;

public class GestionFragment extends Fragment {

    private FragmentGestionBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GestionViewModel galleryViewModel =
                new ViewModelProvider(this).get(GestionViewModel.class);

        binding = FragmentGestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button bAñadir = root.findViewById(R.id.botonAñadirLibro);
        db.collection("Libros").whereEqualTo("empresa", InicioSesion.codempresa)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ListaGestion.listaGestion.clear();
                        for(QueryDocumentSnapshot d : task.getResult()) {
                            String idLibro = d.getId();
                            String nombre = d.getData().get("nombre") + "";
                            String autor = d.getData().get("autor") + "";
                            String sinopsis = d.getData().get("sinopsis") + "";
                            float puntuacion = Float.parseFloat(d.getData().get("puntuacion") + "");
                            double precio = Double.parseDouble(d.getData().get("precio") + "");
                            long isbn = Long.parseLong(d.getData().get("isbn") + "");
                            int stock = Integer.parseInt(d.getData().get("stock") + "");
                            String empresa = d.getData().get("empresa") + "";
                            Libro l = new Libro(nombre, autor, sinopsis, puntuacion, precio, isbn, stock, empresa, idLibro);
                            ListaGestion.listaGestion.add(l);
                            ListaGestion.miAdaptador.notifyDataSetChanged();
                        }
                    }
                });

        bAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nc = Navigation.findNavController(v);
                nc.navigate(R.id.action_nav_gestion_to_nav_editar);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}