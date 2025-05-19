package es.riberadeltajo.bookwormv2.recyclerviews.carrito;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import es.riberadeltajo.bookwormv2.clases.Carrito;
import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentCarritoBinding;

import java.util.List;
import java.util.Objects;


public class MyCarritoRecyclerViewAdapter extends RecyclerView.Adapter<MyCarritoRecyclerViewAdapter.ViewHolder> {

    private final List<Carrito> mValues;
    private Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyCarritoRecyclerViewAdapter(List<Carrito> items, Context c) {
        mValues = items;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCarritoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("Tamaño Array", "" + ListaCarrito.carrito.size());
        String titulo = mValues.get(position).getLibro() + "";
        double precio = mValues.get(position).getPrecio();

        holder.mTituloLibro.setText(titulo);
        holder.mPrecioLibro.setText(precio+"");

        holder.mBotonQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    ListaCarrito.carrito.remove(pos);
                    ListaCarrito.librosCarrito.remove(pos);
                    notifyItemRemoved(pos);

                    db.collection("Libros").document(titulo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot ds = task.getResult();
                            int stock = Integer.parseInt(ds.get("stock") + "") + 1;
                            db.collection("Libros").document(titulo).update("stock", stock);
                        }
                    });

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTituloLibro;
        public final TextView mPrecioLibro;
        public final ImageButton mBotonQuitar;

        public ViewHolder(FragmentCarritoBinding binding) {
            super(binding.getRoot());
            mTituloLibro = binding.libroCarrito;
            mPrecioLibro = binding.precioCarrito;
            mBotonQuitar = binding.quitarCarrito;
        }

        @Override
        public String toString() {
            return super.toString() + " ''";
        }
    }
}