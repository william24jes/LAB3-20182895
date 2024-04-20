package com.example.lab3_20182895.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContadorPrimoViewModel extends ViewModel {

    private final MutableLiveData<Integer> contador = new MutableLiveData<>();

    public MutableLiveData<Integer> getContador() {
        return contador;
    }
}
