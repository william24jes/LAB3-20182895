package com.example.lab3_20182895;

import android.os.Bundle;

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
    }
}