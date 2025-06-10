package es.riberadeltajo.bookwormv2.admin.adminCerrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import es.riberadeltajo.bookwormv2.MainActivity2;
import es.riberadeltajo.bookwormv2.MainActivity3;
import es.riberadeltajo.bookwormv2.R;
import es.riberadeltajo.bookwormv2.databinding.FragmentAdminCerrarBinding;
import es.riberadeltajo.bookwormv2.databinding.FragmentSlideshowBinding;

public class AdminCerrarFragment extends Fragment {

    private FragmentAdminCerrarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminCerrarViewModel slideshowViewModel =
                new ViewModelProvider(this).get(AdminCerrarViewModel.class);

        binding = FragmentAdminCerrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button bCerrar = root.findViewById(R.id.cerrarSesionAdmin);

        bCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity3) {
                    ((MainActivity3) getActivity()).cerrarSesion();
                }
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}