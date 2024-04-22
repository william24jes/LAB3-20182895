package com.example.lab3_20182895;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20182895.databinding.PeliculaBinding;
import com.example.lab3_20182895.dto.DtoPeli;

public class Pelicula extends AppCompatActivity {

    PeliculaBinding peliculaBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        peliculaBinding = PeliculaBinding.inflate(getLayoutInflater());
        setContentView(peliculaBinding.getRoot());

        Toast.makeText(this, "Tiene internet: "+ tengoInternet()+"\nSe encuentra en la vista de información de Película", Toast.LENGTH_LONG).show();


        Intent intent =getIntent();
        String titulo=intent.getStringExtra("titulo");
        String director = intent.getStringExtra("director");
        String actores = intent.getStringExtra("actores");
        String fecha = intent.getStringExtra("fecha");
        String genero = intent.getStringExtra("genero");
        String escritores = intent.getStringExtra("escritores");
        String trama = intent.getStringExtra("trama");

        String databaseSource = intent.getStringExtra("databaseSource");
        String databaseValue = intent.getStringExtra("databaseValue");
        String rottenSource = intent.getStringExtra("rottenSource");
        String rottenValue = intent.getStringExtra("rottenValue");
        String metacriticSource = intent.getStringExtra("metacriticSource");
        String metacriticValue = intent.getStringExtra("metacriticValue");

        peliculaBinding.textoPelicula.setText(titulo);
        peliculaBinding.director.setText(director);
        peliculaBinding.actores.setText(actores);
        peliculaBinding.fecha.setText(fecha);
        peliculaBinding.genero.setText(genero);
        peliculaBinding.escritores.setText(escritores);
        peliculaBinding.trama.setText(trama);

        peliculaBinding.fuentedatabase.setText(databaseSource);
        peliculaBinding.fuenteRotten.setText(rottenSource);
        peliculaBinding.fuenteMetacritic.setText(metacriticSource);
        peliculaBinding.database.setText(databaseValue);
        peliculaBinding.rotten.setText(rottenValue);
        peliculaBinding.metacritic.setText(metacriticValue);


        peliculaBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    peliculaBinding.regresar.setVisibility(View.VISIBLE);
                } else {
                    peliculaBinding.regresar.setVisibility(View.GONE);
                }
            }
        });

        // Configuración del clic del botón "Regresar"
        peliculaBinding.regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la vista del menú
                Intent intent = new Intent(Pelicula.this, Menu.class);
                startActivity(intent);
                // Finaliza la actividad actual (Pelicula)
                finish();
            }
        });
    }


    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

