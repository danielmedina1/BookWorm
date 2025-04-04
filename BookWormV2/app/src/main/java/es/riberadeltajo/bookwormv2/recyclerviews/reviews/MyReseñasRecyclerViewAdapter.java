package es.riberadeltajo.bookwormv2.recyclerviews.reviews;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import es.riberadeltajo.bookwormv2.clases.Review;
import es.riberadeltajo.bookwormv2.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;


public class MyReseñasRecyclerViewAdapter extends RecyclerView.Adapter<MyReseñasRecyclerViewAdapter.ViewHolder> {

    private final List<Review> mValues;

    private Context context;

    public MyReseñasRecyclerViewAdapter(List<Review> items, Context c) {
        mValues = items;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.usuario.setText(mValues.get(position).getUsuario());
        holder.libro.setText(mValues.get(position).getLibro());
        holder.contenido.setText(mValues.get(position).getDesc());
        holder.ratingBar.setRating(mValues.get(position).getPuntuacion());
        holder.ratingBar.setIsIndicator(true);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView usuario;
        public final TextView libro;
        public final TextView contenido;
        public final RatingBar ratingBar;
        public Review mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            usuario = binding.nomUsuario;
            libro = binding.nomLibro;
            contenido = binding.contReseA;
            ratingBar=binding.ratingRes;
        }

        @Override
        public String toString() {
            return super.toString() + "'";
        }
    }
}