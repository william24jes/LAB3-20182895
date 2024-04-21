package com.example.lab3_20182895.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ButtonAscensoDescensoViewModel extends ViewModel {

    private final MutableLiveData<String> Button = new MutableLiveData<>();

    public MutableLiveData<String> getButton() {
        return Button;
    }
}
