package es.riberadeltajo.bookwormv2.usuario.pedidos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentPedidosBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.carrito.ListaCarrito;
import es.riberadeltajo.bookwormv2.recyclerviews.productos.ListaProductos;

public class PedidosFragment extends Fragment {

    private FragmentPedidosBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PedidosViewModel pedidosViewModel =
                new ViewModelProvider(this).get(PedidosViewModel.class);
        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db.collection("Usuarios").document(InicioSesion.codusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ListaCarrito.carrito.clear();
                DocumentSnapshot d = task.getResult();
                ArrayList librosPedidos = (ArrayList) d.get("carrito");
                final double[] precioTotal = {0.0f};
                Calendar cal = Calendar.getInstance();
                Log.d("LIBROSPEDIDOS", librosPedidos.toString());
                if (librosPedidos.size() != 0) {
                    for (int i = 0; i < librosPedidos.size(); i++) {
                        db.collection("Libros").document("" + librosPedidos.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot d2) {
                                precioTotal[0] += Double.parseDouble(d2.get("precio") + "") ;
                            }
                        });
                    }
                    ListaCarrito.carrito.add(new Pedido(librosPedidos, precioTotal[0], cal.getTime(), 1, InicioSesion.codusuario));
                    ListaCarrito.miAdaptador.notifyDataSetChanged();
                }
                db.collection("Pedidos").whereEqualTo("codUsuario", InicioSesion.codusuario)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        ArrayList librosPedidos = (ArrayList) document.get("libros");
                                        double precioTotal = Double.parseDouble("" + document.get("precioTotal")) ;
                                        Timestamp ts = (Timestamp) document.get("fecha");
                                        int estado = Integer.parseInt("" + document.get("estado"));
                                        String codUsuario = "" + document.get("codUsuario");
                                        ListaCarrito.carrito.add(new Pedido(librosPedidos, precioTotal, ts.toDate(), estado, codUsuario));
                                        ListaCarrito.miAdaptador.notifyDataSetChanged();
                                    }
                                }
                            }
                        });



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