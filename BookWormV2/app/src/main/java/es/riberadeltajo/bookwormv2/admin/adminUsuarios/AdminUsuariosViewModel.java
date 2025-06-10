package es.riberadeltajo.bookwormv2.admin.adminUsuarios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminUsuariosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminUsuariosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}