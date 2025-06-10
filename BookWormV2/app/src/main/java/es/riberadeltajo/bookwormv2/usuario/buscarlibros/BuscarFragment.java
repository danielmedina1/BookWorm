package es.riberadeltajo.bookwormv2.usuario.buscarlibros;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentBuscarBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.libros.ListaLibros;

public class BuscarFragment extends Fragment {

    private FragmentBuscarBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuscarViewModel buscarViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        binding = FragmentBuscarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText e = root.findViewById(R.id.buscarLibro);
        Log.d("Busqueda", e.getText().toString());

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!e.getText().toString().equals("")) {
                    cargarDatos(e.getText().toString());
                } else {
                    ListaLibros.libros.clear();
                    ListaLibros.miAdaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        return root;
    }

    private void cargarDatos(String b) {
        ListaLibros.libros.clear();
        ListaLibros.miAdaptador.notifyDataSetChanged();
        db.collection("Libros")
                .whereGreaterThanOrEqualTo("nombre", b)
                .whereLessThanOrEqualTo("nombre", b + "\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("Empresas").document(document.get("empresa") + "")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot d = task.getResult();
                                        String idLibro = document.getId() + "";
                                        String nombre = document.getData().get("nombre") + "";
                                        String autor = document.getData().get("autor") + "";
                                        String empresa = d.get("nombre") + "";
                                        String sinopsis = document.getData().get("sinopsis") + "";
                                        float valoracion = Float.parseFloat(document.getData().get("puntuacion") + "");
                                        double precio =  Double.parseDouble(document.getData().get("precio") + "");
                                        int stock = Integer.parseInt(document.getData().get("stock") + "");
                                        long isbn = Long.parseLong(document.getData().get("isbn") + "");

                                        ListaLibros.libros.add(new Libro(nombre, autor, precio, isbn, empresa, sinopsis, valoracion, stock, idLibro));
                                        ListaLibros.miAdaptador.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            Log.d("ERROR", "---Error al conseguir los datos---");
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        ListaLibros.miAdaptador.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ListaLibros.libros.clear();
        binding = null;
    }
}