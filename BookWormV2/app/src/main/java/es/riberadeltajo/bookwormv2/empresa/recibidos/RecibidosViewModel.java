package es.riberadeltajo.bookwormv2.empresa.recibidos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecibidosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecibidosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}