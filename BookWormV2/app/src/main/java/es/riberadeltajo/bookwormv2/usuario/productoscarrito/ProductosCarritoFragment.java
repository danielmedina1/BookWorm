package es.riberadeltajo.bookwormv2.usuario.productoscarrito;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentProductosCarritoBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.productos.ListaProductos;

public class ProductosCarritoFragment extends Fragment {

    private FragmentProductosCarritoBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList ll = new ArrayList();
    public static double pT ;
    public static int est = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProductosCarritoViewModel productosCarritoViewModel =
                new ViewModelProvider(this).get(ProductosCarritoViewModel.class);
        binding = FragmentProductosCarritoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button bRealizar = root.findViewById(R.id.realizarPedido);
        pT = 0;

        for (int i = 0; i < ll.size(); i++) {
            db.collection("Libros").document(ll.get(i).toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot d = task.getResult();
                    String nom = d.get("nombre") + "";
                    String aut = d.get("autor") + "";
                    double pre = Double.parseDouble(d.get("precio") + "") ;
                    long isbn = Long.parseLong(d.get("isbn") + "") ;
                    String emp = d.get("empresa") + "";
                    String sin = d.get("sinopsis") + "";
                    float punt = Float.parseFloat(d.get("puntuacion") + "") ;
                    int stock = Integer.parseInt(d.get("stock") + "");
                    String idLibro = d.getId() + "";
                    Libro libro = new Libro(nom, aut, pre, isbn, emp, sin, punt, stock, idLibro);
                    if (!ListaProductos.listaProductos.contains(libro)) {
                        ListaProductos.listaProductos.add(libro);
                        ListaProductos.miAdaptador.notifyDataSetChanged();
                    }
                    ListaProductos.listaLibros.add(libro);
                    ListaProductos.miAdaptador.notifyDataSetChanged();
                    pT = pT + pre;
                }
            });
        }

        if (est != 1) {
            bRealizar.setVisibility(View.INVISIBLE);
        }

        bRealizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timestamp fA = Timestamp.now();
                HashMap ped = new HashMap();
                ped.put("codUsuario", InicioSesion.codusuario);
                ped.put("estado", 2);
                ped.put("fecha", fA);
                ped.put("libros", ll);
                ped.put("precioTotal", pT);
                ListaProductos.listaLibros.clear();
                db.collection("Usuarios").document(InicioSesion.codusuario).update("carrito", new ArrayList<>());
                db.collection("Pedidos").document().set(ped).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
                getFragmentManager().popBackStack();
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