package es.riberadeltajo.bookwormv2.admin.adminUsuarios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import es.riberadeltajo.bookwormv2.databinding.FragmentAdminUsuariosBinding;

public class AdminUsuariosFragment extends Fragment {

    private FragmentAdminUsuariosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminUsuariosViewModel galleryViewModel =
                new ViewModelProvider(this).get(AdminUsuariosViewModel.class);

        binding = FragmentAdminUsuariosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}