package com.example.lab3_20182895;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20182895.databinding.MenuBinding;

public class Menu extends AppCompatActivity {

    MenuBinding menuBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuBinding=MenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());

        menuBinding.buttonVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad ContadorPrimos
                Intent intent = new Intent(Menu.this, ContadorPrimos.class);
                startActivity(intent);
            }
        });



    }


}