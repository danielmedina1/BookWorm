package es.riberadeltajo.bookwormv2.ui.buscarlibros;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentGalleryBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.libros.ListaLibros;
import es.riberadeltajo.bookwormv2.recyclerviews.libros.MyLibrosRecyclerViewAdapter;

public class BuscarFragment extends Fragment {

    private FragmentGalleryBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuscarViewModel buscarViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText e = binding.buscarLibro;
        Log.d("Busqueda", e.getText().toString());

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ListaLibros.libros.isEmpty())
                    cargarDatos(e.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {
                ListaLibros.libros.clear();
                ListaLibros.miAdaptador.notifyDataSetChanged();
            }
        });




        return root;
    }

    private void cargarDatos(String b) {
        db.collection("Libros")
                .whereEqualTo("nombre", b)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("Libro: " + document.getId(), "" + document.getData().get("nombre"));
                                //Log.d("Libro: " + document.getId(), "" + document.getData().get("autor"));
                                //Log.d("Libro: " + document.getId(), "" + document.getData().get("precio"));
                                //Log.d("Libro: " + document.getId(), "" + document.getData().get("isbn"));
                                //Log.d("Libro: " + document.getId(), "" + document.getData().get("empresa"));
                                //Log.d("Libro: " + document.getId(), "" + document.getData());

                                String nombre = document.getData().get("nombre") + "";
                                String autor = document.getData().get("autor") + "";
                                double precio =  Double.parseDouble(document.getData().get("precio") + "");
                                int isbn = Integer.parseInt(document.getData().get("isbn") + "");
                                String empresa = document.getData().get("empresa") + "";
                                ListaLibros.libros.add(new Libro(nombre, autor, precio, isbn, empresa));
                                ListaLibros.miAdaptador.notifyDataSetChanged();

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