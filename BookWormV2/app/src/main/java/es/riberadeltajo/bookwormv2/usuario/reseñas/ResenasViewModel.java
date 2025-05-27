package es.riberadeltajo.bookwormv2.usuario.reseñas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResenasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ResenasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}