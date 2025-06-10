package es.riberadeltajo.bookwormv2.admin.adminCerrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminCerrarViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminCerrarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}