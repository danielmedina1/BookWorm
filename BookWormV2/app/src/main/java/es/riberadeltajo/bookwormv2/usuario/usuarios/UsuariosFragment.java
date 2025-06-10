package es.riberadeltajo.bookwormv2.usuario.usuarios;

import androidx.core.content.ContextCompat;
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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentUsuariosBinding;

public class UsuariosFragment extends Fragment {

    private FragmentUsuariosBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String nomUsuario = new String();
    public static String mailPropio = new String();
    public static String mailUsuario = new String();
    public static boolean siguiendo = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        UsuariosViewModel usuariosViewModel =
                new ViewModelProvider(this).get(UsuariosViewModel.class);
        binding = FragmentUsuariosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle b = getArguments();
        mailPropio = InicioSesion.emailusuario;
        mailUsuario = b.getString("email");
        nomUsuario = b.getString("username");
        String codAjeno = b.getString("coduser");
        Log.d("CODAJENO","" + codAjeno);

        Button bAccion = root.findViewById(R.id.accionUsuario);

        TextView username = root.findViewById(R.id.usernameUsuario);
        username.setText(nomUsuario + "");


        db.collection("Usuarios").document(InicioSesion.codusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                ArrayList sig = (ArrayList) document.get("siguiendo");
                if (sig.contains(codAjeno)) {
                    bAccion.setText("Siguiendo");
                    int color = ContextCompat.getColor(requireContext(), R.color.black);
                    bAccion.setBackgroundColor(color);
                    siguiendo = true;
                } else {
                    bAccion.setText("Seguir");
                    int color = ContextCompat.getColor(requireContext(), R.color.red);
                    bAccion.setBackgroundColor(color);
                    siguiendo = false;
                }

            }
        });
        db.collection("Usuarios").document(codAjeno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                TextView numSeg = root.findViewById(R.id.numSeguidores);
                TextView numSig = root.findViewById(R.id.numSeguidos);
                if (document.get("siguiendo") != null) {
                    ArrayList sig = (ArrayList) document.get("siguiendo");
                    numSig.setText(""+sig.size());
                } else {
                    numSig.setText("0");
                }
                if ((ArrayList) document.get("seguidores") != null) {
                    ArrayList seg = (ArrayList) document.get("seguidores");
                    numSeg.setText(""+seg.size());
                } else {
                    numSeg.setText("0");
                }


            }
        });

        bAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!siguiendo) {
                    bAccion.setText("Siguiendo");
                    int color = ContextCompat.getColor(requireContext(), R.color.black);
                    bAccion.setBackgroundColor(color);
                    siguiendo = true;
                    db.collection("Usuarios").document(InicioSesion.codusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            ArrayList sig = (ArrayList) document.get("siguiendo");
                            sig.add("" + codAjeno);
                            db.collection("Usuarios").document(InicioSesion.codusuario).update("siguiendo", sig);
                            siguiendo = true;
                        }
                    });
                    db.collection("Usuarios").document(codAjeno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            ArrayList seg = (ArrayList) document.get("seguidores");
                            seg.add("" + InicioSesion.codusuario);
                            db.collection("Usuarios").document(codAjeno).update("seguidores", seg);
                            siguiendo = true;
                        }
                    });
                } else {
                    if (siguiendo) {
                        bAccion.setText("Seguir");
                        int color = ContextCompat.getColor(requireContext(), R.color.red);
                        bAccion.setBackgroundColor(color);
                        siguiendo = false;
                        db.collection("Usuarios").document(InicioSesion.codusuario).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                ArrayList sig = (ArrayList) document.get("siguiendo");
                                sig.remove("" + codAjeno);
                                db.collection("Usuarios").document(InicioSesion.codusuario).update("siguiendo", sig);
                                siguiendo = false;
                            }
                        });
                        db.collection("Usuarios").document(codAjeno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                ArrayList seg = (ArrayList) document.getData().get("seguidores");
                                seg.remove("" + InicioSesion.codusuario);
                                db.collection("Usuarios").document(codAjeno).update("seguidores", seg);
                                siguiendo = false;
                            }
                        });
                    }
                }


            }
        });
        return root;
    }

}