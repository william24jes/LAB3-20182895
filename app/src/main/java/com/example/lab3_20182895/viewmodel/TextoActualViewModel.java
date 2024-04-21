package com.example.lab3_20182895.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TextoActualViewModel extends ViewModel {

    private final MutableLiveData<String> textoActual = new MutableLiveData<>();

    public MutableLiveData<String> gettextoActual() {
        return textoActual;
    }
}
