package es.riberadeltajo.bookwormv2.recyclerviews.productos;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentProductosBinding;

import java.util.ArrayList;
import java.util.List;


public class MyProductosRecyclerViewAdapter extends RecyclerView.Adapter<MyProductosRecyclerViewAdapter.ViewHolder> {

    private final List<Libro> mValues;
    public int cant = 0;
    public static int estado = 0;
    private ArrayList productos = new ArrayList();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MyProductosRecyclerViewAdapter(List<Libro> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentProductosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cant = 0;
        holder.mNombreProducto.setText(mValues.get(position).getNombre());
        holder.mAutorProducto.setText(mValues.get(position).getAutor());
        db.collection("Empresas").document(mValues.get(position).getEmpresa()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot d = task.getResult();
                holder.mEmpresaProducto.setText(d.get("nombre") + "");
            }
        });
        holder.mIsbnProducto.setText(mValues.get(position).getIsbn() + "");
        for (int i = 0; i < ListaProductos.listaLibros.size(); i++) {
            if (ListaProductos.listaLibros.get(i).getIdLibro().equals(mValues.get(position).getIdLibro())) {
                cant++;
            }
        }

        holder.mCantidadProducto.setText(cant + "");

        if (estado != 1) {
            holder.mBotonEliminar.setVisibility(View.INVISIBLE);
        }

        holder.mBotonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productos.clear();
                ListaProductos.listaLibros.remove(position);
                notifyItemRemoved(position);
                ListaProductos.miAdaptador.notifyDataSetChanged();
                for (int i = 0; i < ListaProductos.listaLibros.size(); i++) {
                    productos.add(ListaProductos.listaLibros.get(i).getIdLibro());
                }
                db.collection("Usuarios").document(InicioSesion.codusuario)
                        .update("carrito", productos);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNombreProducto;
        public final TextView mAutorProducto;
        public final TextView mEmpresaProducto;
        public final TextView mIsbnProducto;
        public final TextView mCantidadProducto;
        public final Button mBotonEliminar;


        public ViewHolder(FragmentProductosBinding binding) {
            super(binding.getRoot());
            mNombreProducto = binding.productoTitulo;
            mAutorProducto = binding.productoAutor;
            mEmpresaProducto = binding.productoEmpresa;
            mIsbnProducto = binding.productoIsbn;
            mCantidadProducto = binding.productoNumero;
            mBotonEliminar = binding.eliminarBoton;

        }

        @Override
        public String toString() {
            return super.toString() + "' '";
        }
    }
}