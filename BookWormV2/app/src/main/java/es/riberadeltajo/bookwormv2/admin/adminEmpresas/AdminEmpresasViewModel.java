package es.riberadeltajo.bookwormv2.admin.adminEmpresas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminEmpresasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminEmpresasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}