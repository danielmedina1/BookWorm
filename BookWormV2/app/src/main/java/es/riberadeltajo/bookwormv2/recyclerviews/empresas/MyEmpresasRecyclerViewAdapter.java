package es.riberadeltajo.bookwormv2.recyclerviews.empresas;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Empresa;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentEmpresasBinding;

import java.util.ArrayList;
import java.util.List;


public class MyEmpresasRecyclerViewAdapter extends RecyclerView.Adapter<MyEmpresasRecyclerViewAdapter.ViewHolder> {

    private final List<Empresa> mValues;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyEmpresasRecyclerViewAdapter(List<Empresa> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEmpresasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNombreEmpresa.setText(mValues.get(position).getNombre() + "");

        holder.mBotonEditarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nc = Navigation.findNavController(v);
                Bundle b = new Bundle();
                b.putString("idEmpresa", mValues.get(position).getIdEmpresa().toString());
                b.putString("nombre", mValues.get(position).getNombre().toString());
                b.putString("email", mValues.get(position).getEmail().toString());
                b.putString("password", mValues.get(position).getPassword().toString());
                b.putString("localizacion", mValues.get(position).getLocalizacion().toString());

                nc.navigate(R.id.action_nav_admin_empresas_to_nav_administrar_empresas, b);
            }
        });

        holder.mBotonQuitarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Libros").whereEqualTo("empresa", mValues.get(position).getIdEmpresa()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot d: task.getResult()) {
                            db.collection("Reviews").whereEqualTo("libro", d.getId()).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot d2: task.getResult()) {
                                        db.collection("Reviews").document(d2.getId()).delete();
                                        Log.d("ELIMINADO REVIEWS", "" + d2.getId());
                                    }
                                }
                            });
                            db.collection("Pedidos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot d3: task.getResult()) {
                                        ArrayList<Libro> l = (ArrayList<Libro>) d3.get("libros");
                                        if (l.contains(d3.getId())) {
                                            db.collection("Pedidos").document(d3.getId()).delete();
                                            Log.d("ELIMINADO PEDIDOS", "" + d3.getId());
                                        }
                                    }
                                }
                            });
                            db.collection("Libros").document(d.getId()).delete();
                            Log.d("ELIMINADO LIBROS", "" + d.getId());
                        }
                    }
                });
                db.collection("Empresas").document(mValues.get(position).getIdEmpresa() + "").delete();
                Log.d("EMPRESA ELIMINADA", mValues.get(position).getIdEmpresa());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNombreEmpresa;
        public final ImageButton mBotonQuitarEmpresa;
        public final ImageButton mBotonEditarEmpresa;


        public ViewHolder(FragmentEmpresasBinding binding) {
            super(binding.getRoot());
            mNombreEmpresa = binding.nombreEmpresaA;
            mBotonEditarEmpresa = binding.editarEmpresa;
            mBotonQuitarEmpresa = binding.eliminarEmpresa;
        }

        @Override
        public String toString() {
            return super.toString() + " ' '";
        }
    }
}