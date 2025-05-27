package es.riberadeltajo.bookwormv2.usuario.perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import es.riberadeltajo.bookwormv2.clases.Review;
import es.riberadeltajo.bookwormv2.databinding.FragmentPerfilBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.ListaReseñas;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.MyReseñasRecyclerViewAdapter;


public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        MyReseñasRecyclerViewAdapter.ruta = R.id.action_nav_perfil_to_nav_mostrarLibro;

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView tituloPerfil = root.findViewById(R.id.perfilTitulo);
        TextView seguidoresPerfil = root.findViewById(R.id.perfilSeguidores);
        TextView siguiendoPerfil = root.findViewById(R.id.perfilSeguidos);
        Button cambiarInfo = root.findViewById(R.id.cambiarInformacion);
        tituloPerfil.setText(" ");
        seguidoresPerfil.setText(" ");
        siguiendoPerfil.setText(" ");

        cargarReseñas();

        db.collection("Usuarios").document(InicioSesion.emailusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                ArrayList seg = (ArrayList) ds.get("seguidores");
                ArrayList sig = (ArrayList) ds.get("siguiendo");
                String nom = ds.getString("username") + "";
                tituloPerfil.setText(nom);
                seguidoresPerfil.setText(seg.size() + "");
                siguiendoPerfil.setText(sig.size() + "");



            }
        });
        cambiarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nc = Navigation.findNavController(v);
                nc.navigate(R.id.action_nav_perfil_to_nav_cambio);
            }
        });



        return root;
    }

    private void cargarReseñas() {
        db.collection("Reviews")
                .whereEqualTo("emailusuario", InicioSesion.emailusuario)
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
                                String mail = document.getData().get("emailusuario") + "";
                                float valoracion = Float.parseFloat(document.getData().get("puntuacion") + "");
                                ListaReseñas.reseñas.add(new Review(descripcion, fecha, libro, valoracion, usuario, mail));
                                ListaReseñas.miAdaptador.notifyDataSetChanged();
                            }
                        } else {
                            Log.d("ERROR", "---Error al conseguir los datos---");
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}