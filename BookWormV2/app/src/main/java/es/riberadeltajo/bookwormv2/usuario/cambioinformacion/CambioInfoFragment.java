package es.riberadeltajo.bookwormv2.usuario.cambioinformacion;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentCambioInfoBinding;

public class CambioInfoFragment extends Fragment {

    private CambioInfoViewModel mViewModel;
    private FragmentCambioInfoBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CambioInfoViewModel cambioInfoViewModel =
                new ViewModelProvider(this).get(CambioInfoViewModel.class);

        binding = FragmentCambioInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText edNombre = root.findViewById(R.id.editNombre);
        EditText edApellidos = root.findViewById(R.id.editApellidos);
        EditText edUsername = root.findViewById(R.id.editUsername);
        EditText edContraseña = root.findViewById(R.id.editContrasena);
        Button gCambios = root.findViewById(R.id.botonCambiar);
        db.collection("Usuarios").document(InicioSesion.emailusuario)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot d = task.getResult();
                edNombre.setText("" + d.getString("nombre"));
                edApellidos.setText("" + d.getString("apellidos"));
                edUsername.setText("" + d.getString("username"));
                edContraseña.setText("" + d.getString("contraseña"));
            }
        });

        gCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edNombre.getText().toString().equals(null) || edApellidos.getText().toString().equals(null) ||
                        edUsername.getText().toString().equals(null) || edContraseña.getText().toString().equals(null)) {
                    Toast.makeText(getContext(), "Uno de los campos está vacío", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("Usuarios").document(InicioSesion.emailusuario)
                            .update("nombre", edNombre.getText().toString(), "apellidos", edApellidos.getText().toString(),
                                    "username", edUsername.getText().toString(), "contraseña", edContraseña.getText().toString());

                    db.collection("Reviews").whereEqualTo("usuario", InicioSesion.nombreusuario)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot d : task.getResult()) {
                                DocumentReference ref = d.getReference();
                                Map update = new HashMap();
                                update.put("usuario", edUsername.getText().toString() + "");

                                ref.update(update)
                                        .addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                Log.d("Actualizar Documento", "Exito " + ref.getId());
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Actualizar Documento", "Fracaso " + ref.getId());
                                            }
                                        });

                            }
                        }
                    });
                    getFragmentManager().popBackStack();
                }
                Log.d("Click", "Clickado");

            }
        });

        return inflater.inflate(R.layout.fragment_cambio_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CambioInfoViewModel.class);
        // TODO: Use the ViewModel
    }

}