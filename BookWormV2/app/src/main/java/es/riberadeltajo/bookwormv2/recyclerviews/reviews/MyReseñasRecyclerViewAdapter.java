package es.riberadeltajo.bookwormv2.recyclerviews.reviews;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.riberadeltajo.bookwormv2.InicioSesion;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.clases.Review;
import es.riberadeltajo.bookwormv2.databinding.FragmentItemBinding;
import es.riberadeltajo.bookwormv2.usuario.mostrarlibro.MostrarLibroFragment;

import java.util.List;


public class MyReseñasRecyclerViewAdapter extends RecyclerView.Adapter<MyReseñasRecyclerViewAdapter.ViewHolder> {

    private final List<Review> mValues;
    public static int ruta;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        String userName = mValues.get(position).getUsuario() + "";
        String mail = mValues.get(position).getCodUser() + "";
        db.collection("Libros").document(mValues.get(position).getLibro()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot d = task.getResult();
                String titLibro= d.get("nombre")  + "";
                holder.libro.setText(titLibro);

            }
        });


        holder.mItem = mValues.get(position);
        holder.usuario.setText(mValues.get(position).getUsuario());
        holder.contenido.setText(mValues.get(position).getDesc());
        holder.ratingBar.setRating(mValues.get(position).getPuntuacion());
        holder.ratingBar.setIsIndicator(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.equals(InicioSesion.nombreusuario)) {
                    if (ruta == R.id.action_nav_perfil_to_nav_mostrarLibro) {
                        db.collection("Libros").document(mValues.get(position).getLibro()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot d = task.getResult();
                                NavController nc = Navigation.findNavController(v);
                                MostrarLibroFragment.tituloLibro = d.getString("nombre");
                                MostrarLibroFragment.isbnLibro = d.get("isbn") + "";
                                MostrarLibroFragment.autorLibro = d.getString("autor");
                                db.collection("Empresas").document("" + d.getString("empresa")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot d2 = task.getResult();
                                        MostrarLibroFragment.empresaLibro = d2.getString("nombre");
                                    }
                                });
                                MostrarLibroFragment.sinopsisLibro = d.getString("sinopsis");
                                MostrarLibroFragment.stockLibro = Integer.parseInt(d.get("stock") + "");
                                MostrarLibroFragment.valoracionLibro = Float.parseFloat(d.get("puntuacion") + "");
                                MostrarLibroFragment.precioLibro = Double.parseDouble(d.get("precio") + "");
                                nc.navigate(ruta);
                            }
                        });
                    }

                } else {
                    if (ruta == R.id.action_nav_perfil_to_nav_mostrarLibro) {
                        db.collection("Libros").document(mValues.get(position).getLibro()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot d = task.getResult();
                                NavController nc = Navigation.findNavController(v);
                                MostrarLibroFragment.tituloLibro = d.getString("nombre");
                                MostrarLibroFragment.isbnLibro = d.get("isbn") + "";
                                MostrarLibroFragment.autorLibro = d.getString("autor");
                                db.collection("Empresas").document("" + d.getString("empresa")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot d2 = task.getResult();
                                        MostrarLibroFragment.empresaLibro = d2.getString("nombre");
                                    }
                                });

                                MostrarLibroFragment.sinopsisLibro = d.getString("sinopsis");
                                MostrarLibroFragment.stockLibro = Integer.parseInt(d.get("stock") + "");
                                MostrarLibroFragment.valoracionLibro = Float.parseFloat(d.get("puntuacion") + "");
                                MostrarLibroFragment.precioLibro = Double.parseDouble(d.get("precio") + "");
                                nc.navigate(ruta);
                            }
                        });


                    } else {
                        NavController nc = Navigation.findNavController(v);
                        Bundle b = new Bundle();
                        b.putString("username", userName);
                        b.putString("email", mail);
                        b.putString("coduser", mValues.get(position).getCodUser() + "");
                        nc.navigate(ruta, b);
                    }


                }

            }
        });

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