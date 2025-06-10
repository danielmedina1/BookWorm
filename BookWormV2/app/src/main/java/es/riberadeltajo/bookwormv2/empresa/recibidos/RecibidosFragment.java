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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentRecibidosBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.carrito.ListaCarrito;
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

        db.collection("Pedidos").whereEqualTo("estado",2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ListaPedidos.pedidos.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArrayList librosPedidos = (ArrayList) document.get("libros");
                                double precioTotal = Double.parseDouble("" + document.get("precioTotal")) ;
                                Timestamp ts = (Timestamp) document.get("fecha");
                                int estado = Integer.parseInt("" + document.get("estado"));
                                String codUsuario = "" + document.get("codUsuario");
                                ListaPedidos.pedidos.add(new Pedido(librosPedidos, precioTotal, ts.toDate(), estado, codUsuario, document.getId()));
                                ListaPedidos.miAdaptador.notifyDataSetChanged();
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