package es.riberadeltajo.bookwormv2.ui.mostrarlibro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MostrarLibroViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public MostrarLibroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}