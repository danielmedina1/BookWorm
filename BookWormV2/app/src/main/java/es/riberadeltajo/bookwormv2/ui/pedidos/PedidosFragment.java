package es.riberadeltajo.bookwormv2.ui.pedidos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import es.riberadeltajo.bookwormv2.CrearCuenta;
import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentSlideshowBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.carrito.ListaCarrito;

public class PedidosFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PedidosViewModel pedidosViewModel =
                new ViewModelProvider(this).get(PedidosViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button realizarPedido = root.findViewById(R.id.realizarPedido);
        if(InicioSesion.hayPedido) {
            if (ListaCarrito.carrito.size() >= 1) {
                realizarPedido.setText("Realizar pedido");
                int color = ContextCompat.getColor(requireContext(), R.color.purple_700);
                realizarPedido.setBackgroundColor(color);
            } else {
                realizarPedido.setText("Selecciona algun libro");
                int color = ContextCompat.getColor(requireContext(), R.color.teal_700);
                realizarPedido.setBackgroundColor(color);
                realizarPedido.setEnabled(false);
            }
        } else {
            realizarPedido.setText("Cancelar pedido");
            int color = ContextCompat.getColor(requireContext(), R.color.teal_700);
            realizarPedido.setBackgroundColor(color);
        }

        realizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(InicioSesion.hayPedido == false) {
                        HashMap pedido = new HashMap();
                        double totalPrecio = 0.0f;
                        realizarPedido.setText("Realizar pedido");
                        int color = ContextCompat.getColor(requireContext(), R.color.purple_700);
                        realizarPedido.setBackgroundColor(color);
                        pedido.put("email", InicioSesion.emailusuario);
                        pedido.put("libros", ListaCarrito.librosCarrito);
                        for (int i = 0; i < ListaCarrito.carrito.size(); i++) {
                            totalPrecio = totalPrecio + ListaCarrito.carrito.get(i).getPrecio();
                        }
                        pedido.put("precioTotal", totalPrecio);
                        db.collection("Pedidos").document("pedido" + InicioSesion.emailusuario).set(pedido);
                        InicioSesion.hayPedido = true;
                    } else {
                        realizarPedido.setText("Cancelar pedido");
                        int color = ContextCompat.getColor(requireContext(), R.color.teal_700);
                        realizarPedido.setBackgroundColor(color);
                        db.collection("Pedidos").document("pedido" + InicioSesion.emailusuario).delete();
                        InicioSesion.hayPedido = false;

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