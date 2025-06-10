package es.riberadeltajo.bookwormv2.admin.adminUsuariosGestion;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.riberadeltajo.bookwormv2.R;

public class AdminUsuariosGestionFragment extends Fragment {

    private AdminUsuariosGestionViewModel mViewModel;

    public static AdminUsuariosGestionFragment newInstance() {
        return new AdminUsuariosGestionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_usuarios_gestion, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminUsuariosGestionViewModel.class);
        // TODO: Use the ViewModel
    }

}