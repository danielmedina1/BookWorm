package es.riberadeltajo.bookwormv2.empresa.recibidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentRecibidosBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.pedidos.ListaPedidos;

public class RecibidosFragment extends Fragment {

    private FragmentRecibidosBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecibidosViewModel homeViewModel =
                new ViewModelProvider(this).get(RecibidosViewModel.class);

        binding = FragmentRecibidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db.collection("Pedidos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot d : task.getResult()) {
                    String email = d.get("email").toString();
                    ArrayList libros = (ArrayList) d.get("libros");
                    double precioTotal = Double.parseDouble(d.get("precioTotal") + "") ;
                    ListaPedidos.pedidos.add(new Pedido(libros, precioTotal, email));
                    ListaPedidos.miAdaptador.notifyDataSetChanged();
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