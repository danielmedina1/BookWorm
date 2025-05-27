package es.riberadeltajo.bookwormv2.empresa.gestion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GestionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GestionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}