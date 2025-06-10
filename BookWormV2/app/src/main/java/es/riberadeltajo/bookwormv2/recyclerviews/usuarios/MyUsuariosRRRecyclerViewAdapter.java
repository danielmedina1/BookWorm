package es.riberadeltajo.bookwormv2.recyclerviews.usuarios;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.riberadeltajo.bookwormv2.clases.Usuario;
import es.riberadeltajo.bookwormv2.databinding.FragmentUsuariosrrBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUsuariosRRRecyclerViewAdapter extends RecyclerView.Adapter<MyUsuariosRRRecyclerViewAdapter.ViewHolder> {

    private final List<Usuario> mValues;

    public MyUsuariosRRRecyclerViewAdapter(List<Usuario> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentUsuariosrrBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) { ;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(FragmentUsuariosrrBinding binding) {
            super(binding.getRoot());
        }

        @Override
        public String toString() {
            return super.toString() + " ' '";
        }
    }
}