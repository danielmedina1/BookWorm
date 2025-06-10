package es.riberadeltajo.bookwormv2.recyclerviews.carrito;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentCarritoBinding;
import es.riberadeltajo.bookwormv2.recyclerviews.productos.ListaProductos;
import es.riberadeltajo.bookwormv2.recyclerviews.productos.MyProductosRecyclerViewAdapter;
import es.riberadeltajo.bookwormv2.usuario.productoscarrito.ProductosCarritoFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyCarritoRecyclerViewAdapter extends RecyclerView.Adapter<MyCarritoRecyclerViewAdapter.ViewHolder> {

    private final List<Pedido> mValues;
    private Context context;
    private int pos = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyCarritoRecyclerViewAdapter(List<Pedido> items, Context c) {
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
        Date fecha = mValues.get(position).getFecha();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        String fechaF = f.format(fecha);
        pos = position;

        holder.mFechaPedido.setText(fechaF);

        if (mValues.get(position).getEstado() == 1) {
            holder.mEstadoPedido.setText("En Carrito");
            holder.mNombrePedido.setText("Carrito");
        } else {
            pos++;
            if (mValues.get(position).getEstado() == 2) {
                holder.mEstadoPedido.setText("En espera");
                holder.mNombrePedido.setText("Pedido-" + pos);
            } else {
                if (mValues.get(position).getEstado() == 3) {
                    holder.mEstadoPedido.setText("Recibido");
                    holder.mNombrePedido.setText("Pedido-" + pos);
                }
            }

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaProductos.listaLibros.clear();
                ListaProductos.listaProductos.clear();
                ProductosCarritoFragment.est = mValues.get(position).getEstado();
                NavController nc = Navigation.findNavController(v);
                ProductosCarritoFragment.ll = mValues.get(position).getLibros();
                MyProductosRecyclerViewAdapter.estado = mValues.get(position).getEstado();
                nc.navigate(R.id.action_nav_pedidos_to_nav_productos_carrito);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNombrePedido;
        public final TextView mEstadoPedido;
        public final TextView mFechaPedido;

        public ViewHolder(FragmentCarritoBinding binding) {
            super(binding.getRoot());
            mNombrePedido = binding.nombrePedido;
            mEstadoPedido = binding.estadoPedido;
            mFechaPedido = binding.fechaPedido;
        }

        @Override
        public String toString() {
            return super.toString() + " ''";
        }
    }
}