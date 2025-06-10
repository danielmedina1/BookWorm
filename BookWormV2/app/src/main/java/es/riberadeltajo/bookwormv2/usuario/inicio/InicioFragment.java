package es.riberadeltajo.bookwormv2.usuario.inicio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import es.riberadeltajo.bookwormv2.databinding.FragmentInicioBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.ListaReseñas;
import es.riberadeltajo.bookwormv2.recyclerviews.reviews.MyReseñasRecyclerViewAdapter;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MyReseñasRecyclerViewAdapter.ruta = R.id.action_nav_inicio_to_nav_usuarios;
        db.collection("Usuarios").document(InicioSesion.codusuario)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList siguiendo = new ArrayList();
                siguiendo.clear();
                ListaReseñas.reseñas.clear();
                ListaReseñas.miAdaptador.notifyDataSetChanged();
                siguiendo = (ArrayList) task.getResult().get("siguiendo");
                if (siguiendo != null &&  siguiendo.size() != 0) {
                    for (int i = 0; i < siguiendo.size(); i++) {
                        db.collection("Reviews")
                                .whereEqualTo("codusuario", siguiendo.get(i))
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
                                                String codus = document.getData().get("codusuario") + "";
                                                float valoracion = Float.parseFloat(document.getData().get("puntuacion") + "");
                                                ListaReseñas.reseñas.add(new Review(descripcion, fecha, libro, valoracion, usuario, codus));
                                                ListaReseñas.miAdaptador.notifyDataSetChanged();
                                            }
                                        } else {
                                            Log.d("ERROR", "---Error al conseguir los datos---");
                                        }
                                    }
                                });
                    }
                }

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