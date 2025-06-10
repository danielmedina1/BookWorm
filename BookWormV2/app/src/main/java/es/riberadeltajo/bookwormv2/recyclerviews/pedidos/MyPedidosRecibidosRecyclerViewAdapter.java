package es.riberadeltajo.bookwormv2.recyclerviews.pedidos;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.riberadeltajo.bookwormv2.clases.Pedido;
import es.riberadeltajo.bookwormv2.databinding.FragmentPedidosRecibidosBinding;

import java.util.ArrayList;
import java.util.List;


public class MyPedidosRecibidosRecyclerViewAdapter extends RecyclerView.Adapter<MyPedidosRecibidosRecyclerViewAdapter.ViewHolder> {

    private final List<Pedido> mValues;
    private final ArrayList libs = new ArrayList();
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
        String delPed = mValues.get(position).getCodUser() + "";
        holder.mItem = mValues.get(position);
        db.collection("Usuarios").document(mValues.get(position).getCodUser() + "").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot d = task.getResult();
                holder.usuarioPedido.setText(d.getId() + " --- " + d.get("username"));
            }
        });

        for (int i = 0; i < mValues.get(position).getLibros().size(); i++) {
            db.collection("Libros").document(mValues.get(position).getLibros().get(i) + "")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot d = task.getResult();
                    //Log.d("adadadad", libs[0]);
                    libs.add(d.get("nombre") + "");
                }

            });
        }

        Log.d("FINAL 3", libs.toString());
        holder.contenidoPedido.setText(mValues.get(position).getLibros() + "");

        holder.precioPedido.setText(mValues.get(position).getPrecioTotal() + "€");

        holder.botonEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    db.collection("Pedidos").document(mValues.get(pos).getCodPed()).update("estado", 3);
                    ListaPedidos.pedidos.remove(pos);
                    notifyItemRemoved(pos);
                    ListaPedidos.miAdaptador.notifyDataSetChanged();

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