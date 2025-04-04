package es.riberadeltajo.bookwormv2.recyclerviews.libros;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import es.riberadeltajo.bookwormv2.clases.Libro;
import es.riberadeltajo.bookwormv2.databinding.FragmentLibrosBinding;

import java.util.List;


public class MyLibrosRecyclerViewAdapter extends RecyclerView.Adapter<MyLibrosRecyclerViewAdapter.ViewHolder> {

    private final List<Libro> mValues;
    private Context context;


    public MyLibrosRecyclerViewAdapter(List<Libro> items, Context c) {
        mValues = items;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentLibrosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String titulo = mValues.get(position).getNombre();
        String isbn = mValues.get(position).getIsbn() + "";
        String autor = mValues.get(position).getAutor();
        String empresa = mValues.get(position).getEmpresa();
        String sinopsis = mValues.get(position).getSinopsis();
        int stock = mValues.get(position).getStock();
        float valoracion = mValues.get(position).getPuntuacion();
        double precio = mValues.get(position).getPrecio();


        holder.mTitulo.setText(mValues.get(position).getNombre());
        holder.mAutor.setText(mValues.get(position).getAutor());
        holder.mPrecio.setText(mValues.get(position).getPrecio() + "");
        holder.mIsbn.setText(mValues.get(position).getIsbn() + "");
        holder.mEmpresa.setText(mValues.get(position).getEmpresa());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Prueba al clickar", titulo);
                LibrosFragment.iniciarLibro(titulo, isbn, autor, empresa, sinopsis, stock, valoracion, precio);
            }
        });


    }




    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitulo;
        public final TextView mAutor;
        public final TextView mPrecio;
        public final TextView mIsbn;
        public final TextView mEmpresa;


        public ViewHolder(FragmentLibrosBinding binding) {
            super(binding.getRoot());
            mTitulo = binding.titulo;
            mAutor = binding.autor;
            mPrecio = binding.precio;
            mIsbn = binding.isbn;
            mEmpresa = binding.empresa;

        }

        @Override
        public String toString() {
            return super.toString() + "";
        }


    }

}