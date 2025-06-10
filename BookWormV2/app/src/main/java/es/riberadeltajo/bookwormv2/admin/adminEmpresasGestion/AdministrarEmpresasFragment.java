package es.riberadeltajo.bookwormv2.admin.adminEmpresasGestion;

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

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentAdministrarEmpresasBinding;

public class AdministrarEmpresasFragment extends Fragment {

    private FragmentAdministrarEmpresasBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AdministrarEmpresasViewModel adev =
                new ViewModelProvider(this).get(AdministrarEmpresasViewModel.class);

        binding = FragmentAdministrarEmpresasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        HashMap empresa = new HashMap();
        TextView usoEmp = root.findViewById(R.id.usoActualAd);
        EditText empNombre = root.findViewById(R.id.nombreEmpresa);
        EditText empEmail = root.findViewById(R.id.emailEmpresaAd);
        EditText empContraseña = root.findViewById(R.id.contraseñaEmpresaAd);
        EditText empLocal = root.findViewById(R.id.localizacionEmpresaAd);
        Button bAccionEmp = root.findViewById(R.id.botonEmpresaAd);

        Bundle b = getArguments();
        if(b != null) {
            usoEmp.setText("Editar Empresa");
            empNombre.setText(b.get("nombre") + "");
            empEmail.setText(b.get("email") + "");
            empContraseña.setText(b.get("password") + "");
            empLocal.setText(b.get("localizacion") + "");
            bAccionEmp.setText("Guardar Cambios");
        }

        bAccionEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!empNombre.getText().toString().equals("") && !empEmail.getText().toString().equals("")
                        && !empContraseña.getText().toString().equals("") && !empLocal.getText().toString().equals("")) {
                    empresa.put("nombre", empNombre.getText().toString());
                    empresa.put("email", empEmail.getText().toString());
                    empresa.put("password", empContraseña.getText().toString());
                    empresa.put("localizacion", empLocal.getText().toString());
                    if(b != null) {
                        db.collection("Empresas").document(b.getString("idEmpresa")).update(empresa)
                                .addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(getActivity(), "Empresa actualizada con exito", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    }
                                });
                    } else {
                        db.collection("Empresas").document().set(empresa)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Empresa añadida con exito", Toast.LENGTH_SHORT).show();
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