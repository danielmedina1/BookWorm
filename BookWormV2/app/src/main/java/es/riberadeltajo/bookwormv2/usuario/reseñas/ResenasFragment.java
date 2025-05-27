package es.riberadeltajo.bookwormv2.usuario.reseñas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentResenasBinding;

public class ResenasFragment extends Fragment {
    private static ResenasFragment r;

    private es.riberadeltajo.bookwormv2.databinding.FragmentResenasBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String tituloLibro = new String();

    public static String usuario = new String();
    private static ArrayList res = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ResenasViewModel resenasViewModel =
                new ViewModelProvider(this).get(ResenasViewModel.class);

        binding = FragmentResenasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        r = ResenasFragment.this;

        Button bpublicar = root.findViewById(R.id.publicarResenaB);
        HashMap resena = new HashMap();
        EditText textR = root.findViewById(R.id.reseñaTexto);
        RatingBar puntR = root.findViewById(R.id.puntuacionReseña);
        Calendar cal = Calendar.getInstance();

        bpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resena.put("descripcion", textR.getText().toString());
                resena.put("fecha", cal.getTime());
                resena.put("libro", tituloLibro);
                resena.put("puntuacion", puntR.getRating());
                resena.put("usuario", usuario);
                resena.put("emailusuario", InicioSesion.emailusuario);
                db.collection("Reviews").document("res" + tituloLibro + usuario).set(resena)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("", "Hecho");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("No Hecho", "Hecho");
                            }
                        });
                db.collection("Libros").document(tituloLibro).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot ds = task.getResult();
                        String re = "res" + tituloLibro + usuario;
                        res = (ArrayList) ds.get("reviews");
                        res.add(re);
                        db.collection("Libros").document(tituloLibro).update("reviews", res);
                    }
                });
                Log.d("Fecha Actual:", cal.getTime() + "");
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