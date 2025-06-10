package es.riberadeltajo.bookwormv2.recyclerviews.gestion_libros;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentGestionLibrosBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.empresas.ListaEmpresas;

import java.util.List;


public class MyGestionLibrosRecyclerViewAdapter extends RecyclerView.Adapter<MyGestionLibrosRecyclerViewAdapter.ViewHolder> {

    private final List<Libro> mValues;
    private Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyGestionLibrosRecyclerViewAdapter(List<Libro> items, Context c) {
        mValues = items;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentGestionLibrosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String titulo = mValues.get(position).getNombre() + "";
        holder.mItem = mValues.get(position);
        holder.mTituloLibro.setText(mValues.get(position).getNombre() + "");

        holder.mBotonQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    ListaGestion.listaGestion.remove(pos);
                    notifyItemRemoved(pos);
                    ListaGestion.miAdaptador.notifyDataSetChanged();
                    db.collection("Reviews").whereEqualTo("libro", titulo)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for(QueryDocumentSnapshot d : task.getResult()) {
                                            db.collection("Reviews")
                                                    .document(d.getId()).delete();
                                        }
                                    }
                                }
                            });
                    db.collection("Libros").document(titulo).delete();
                }
                ListaEmpresas.listEmpresas.clear();
                ListaEmpresas.miAdaptador.notifyDataSetChanged();
            }
        });
        holder.mBotonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nc = Navigation.findNavController(v);
                Bundle b = new Bundle();
                b.putString("titulo", mValues.get(position).getNombre().toString());
                b.putString("autor", mValues.get(position).getAutor().toString());
                b.putLong("isbn", mValues.get(position).getIsbn());
                b.putDouble("precio", mValues.get(position).getPrecio());
                b.putInt("stock", mValues.get(position).getStock());
                b.putString("sinopsis", mValues.get(position).getSinopsis().toString());
                b.putString("idLibro", mValues.get(position).getIdLibro().toString());
                nc.navigate(R.id.action_nav_gestion_to_nav_editar, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Libro mItem;
        public final TextView mTituloLibro;
        public final ImageButton mBotonQuitar;
        public final ImageButton mBotonEditar;


        public ViewHolder(FragmentGestionLibrosBinding binding) {
            super(binding.getRoot());
            mTituloLibro = binding.tituloLibroG;
            mBotonEditar = binding.editarLibro;
            mBotonQuitar = binding.eliminarLibro;
        }

        @Override
        public String toString() {
            return super.toString() + " ' '";
        }
    }
}