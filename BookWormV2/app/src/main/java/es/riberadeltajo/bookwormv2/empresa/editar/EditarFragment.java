package es.riberadeltajo.bookwormv2.empresa.editar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentEditarBinding;

public class EditarFragment extends Fragment {

    private FragmentEditarBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static EditarFragment newInstance() {
        return new EditarFragment();
    }
    private String id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        EditarViewModel editarViewModel =
                new ViewModelProvider(this).get(EditarViewModel.class);

        binding = FragmentEditarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        HashMap libro = new HashMap();
        TextView uso  = root.findViewById(R.id.usoActual);
        EditText titulo = root.findViewById(R.id.tituloLibroN);
        EditText autor = root.findViewById(R.id.autorLibroN);
        EditText isbn = root.findViewById(R.id.ISBNLibroN);
        EditText precio = root.findViewById(R.id.precioLibroN);
        EditText stock = root.findViewById(R.id.stockLibroN);
        EditText sinopsis = root.findViewById(R.id.sinopsisLibroN);
        Button bAccion = root.findViewById(R.id.botonAccionEditar);


        Bundle b = getArguments();
        if(b != null) {
            id = b.get("idLibro") + "";
            uso.setText("Editar Libro");
            bAccion.setText("Guardar Cambios");
            titulo.setText(b.get("titulo") + "");
            autor.setText(b.get("autor") + "");
            isbn.setText(b.get("isbn") + "");
            precio.setText(b.get("precio") + "");
            stock.setText(b.get("stock") + "");
            sinopsis.setText(b.get("sinopsis") + "");
        }


        bAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libro.put("autor", autor.getText().toString());
                libro.put("empresa", InicioSesion.codempresa);
                libro.put("isbn", Long.parseLong(isbn.getText() + ""));
                libro.put("nombre", titulo.getText().toString());
                libro.put("precio", Double.parseDouble(precio.getText() + ""));
                libro.put("puntuacion", 0);
                libro.put("sinopsis", sinopsis.getText().toString());
                libro.put("stock", Integer.parseInt(stock.getText() + ""));
                if(b != null) {
                    db.collection("Libros").document(id).update(libro).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Libro actualizado con exito", Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        }
                    });
                } else {
                    if (!titulo.getText().equals("")) {
                        db.collection("Libros").document(titulo.getText().toString()).set(libro).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Libro añadido con exito", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();
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