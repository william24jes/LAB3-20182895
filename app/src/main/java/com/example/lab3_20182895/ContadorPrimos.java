package com.example.lab3_20182895;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20182895.databinding.ContadorPrimosBinding;


public class ContadorPrimos extends AppCompatActivity {

    ContadorPrimosBinding contadorPrimosBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contadorPrimosBinding=ContadorPrimosBinding.inflate(getLayoutInflater());
        setContentView(contadorPrimosBinding.getRoot());
    }
}
