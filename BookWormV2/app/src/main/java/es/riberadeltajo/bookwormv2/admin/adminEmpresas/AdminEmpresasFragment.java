package es.riberadeltajo.bookwormv2.admin.adminEmpresas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Empresa;
import es.riberadeltajo.bookwormv2.databinding.FragmentAdminEmpresasBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.empresas.ListaEmpresas;

public class AdminEmpresasFragment extends Fragment {

    private FragmentAdminEmpresasBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminEmpresasViewModel homeViewModel =
                new ViewModelProvider(this).get(AdminEmpresasViewModel.class);

        binding = FragmentAdminEmpresasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText bE = root.findViewById(R.id.buscarEmpresa);
        bE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!bE.getText().toString().equals("")) {
                    cargarEmpresas(bE.getText().toString());

                } else {
                    ListaEmpresas.listEmpresas.clear();
                    ListaEmpresas.miAdaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button bAñadir = root.findViewById(R.id.añadirEmpresa);
        bAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nc = Navigation.findNavController(v);
                nc.navigate(R.id.action_nav_admin_empresas_to_nav_administrar_empresas);
            }
        });


        return root;
    }

    private void cargarEmpresas(String nom) {
        ListaEmpresas.listEmpresas.clear();
        ListaEmpresas.miAdaptador.notifyDataSetChanged();
        db.collection("Empresas")
                .whereGreaterThanOrEqualTo("nombre", nom)
                .whereLessThanOrEqualTo("nombre", nom + "\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot d : task.getResult()) {
                                db.collection("Empresas").document(d.getId()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot ds = task.getResult();
                                        String idEmpresa = ds.getId();
                                        String nombre = ds.get("nombre") + "";
                                        String email = ds.get("email") + "";
                                        String password = ds.get("password") + "";
                                        String localizacion = ds.get("localizacion") + "";
                                        ListaEmpresas.listEmpresas.add(new Empresa(idEmpresa, nombre, password, email, localizacion));
                                        ListaEmpresas.miAdaptador.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}