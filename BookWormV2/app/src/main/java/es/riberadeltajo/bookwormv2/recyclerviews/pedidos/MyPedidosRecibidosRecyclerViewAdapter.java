package es.riberadeltajo.bookwormv2.recyclerviews.pedidos;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentPedidosRecibidosBinding;

import java.util.List;


public class MyPedidosRecibidosRecyclerViewAdapter extends RecyclerView.Adapter<MyPedidosRecibidosRecyclerViewAdapter.ViewHolder> {

    private final List<Pedido> mValues;
    private Context context;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyPedidosRecibidosRecyclerViewAdapter(List<Pedido> items, Context c ) {
        mValues = items;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPedidosRecibidosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String delPed = mValues.get(position).getEmaiUser() + "";
        holder.mItem = mValues.get(position);
        holder.usuarioPedido.setText(mValues.get(position).getEmaiUser() + "");
        holder.contenidoPedido.setText(mValues.get(position).getLibros().toString() + "");
        holder.precioPedido.setText(mValues.get(position).getPrecioTotal() + "");

        holder.botonEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    ListaPedidos.pedidos.remove(pos);
                    notifyItemRemoved(pos);
                    ListaPedidos.miAdaptador.notifyDataSetChanged();
                    db.collection("Pedidos")
                            .whereEqualTo("email", delPed)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for(QueryDocumentSnapshot d : task.getResult()) {
                                            db.collection("Pedidos")
                                                    .document(d.getId()).delete();
                                        }
                                    }
                                }
                            });
                    db.collection("Usuarios").document(delPed)
                            .update("ped", false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Pedido mItem;
        public final TextView usuarioPedido;
        public final TextView contenidoPedido;
        public final TextView precioPedido;
        public final Button botonEntregar;

        public ViewHolder(FragmentPedidosRecibidosBinding binding) {
            super(binding.getRoot());
            usuarioPedido = binding.usuarioPedido;
            contenidoPedido = binding.contenidoPedido;
            precioPedido = binding.precioPeedido;
            botonEntregar = binding.entregarPedido;
        }

        @Override
        public String toString() {
            return super.toString() + " ' '";
        }
    }
}