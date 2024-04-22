package com.example.lab3_20182895;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20182895.databinding.MenuBinding;
import com.example.lab3_20182895.dto.DtoPeli;
import com.example.lab3_20182895.services.PeliculasService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu extends AppCompatActivity {

    MenuBinding menuBinding;
    PeliculasService peliculasService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuBinding = MenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());

        Toast.makeText(this, "Tiene internet: "+ tengoInternet()+"\nSe encuentra en la vista de Menú", Toast.LENGTH_LONG).show();
        peliculasService = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PeliculasService.class);

        menuBinding.buttonVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ContadorPrimos.class);
                startActivity(intent);
            }
        });

        menuBinding.ButtonBuscarPeli.setOnClickListener(view -> {
            String id = menuBinding.input.getText().toString();
            fetchInfo(id);
        });
    }

    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void fetchInfo(String peliculaID) {
        if (tengoInternet()) {
            peliculasService.getInformacionPelicula("bf81d461", peliculaID).enqueue(new Callback<DtoPeli>() {
                @Override
                public void onResponse(Call<DtoPeli> call, Response<DtoPeli> response) {
                    if (response.isSuccessful()) {
                        DtoPeli dtoPeliList = response.body();
                        if (dtoPeliList != null) {
                            Intent intent2 = new Intent(Menu.this, Pelicula.class);

                            intent2.putExtra("titulo", dtoPeliList.getTitle());
                            intent2.putExtra("director", dtoPeliList.getDirector());
                            intent2.putExtra("actores", dtoPeliList.getActors());
                            intent2.putExtra("fecha", dtoPeliList.getReleased());
                            intent2.putExtra("genero", dtoPeliList.getGenre());
                            intent2.putExtra("escritores", dtoPeliList.getWriter());
                            intent2.putExtra("trama", dtoPeliList.getPlot());

                            //RATINGS
                            //database
                            intent2.putExtra("databaseSource",dtoPeliList.getRatings().get(0).getSource());
                            intent2.putExtra("databaseValue",dtoPeliList.getRatings().get(0).getValue());

                            //ROtten
                            intent2.putExtra("rottenSource",dtoPeliList.getRatings().get(1).getSource());
                            intent2.putExtra("rottenValue",dtoPeliList.getRatings().get(1).getValue());

                            //Metacritic
                            intent2.putExtra("metacriticSource",dtoPeliList.getRatings().get(2).getSource());
                            intent2.putExtra("metacriticValue",dtoPeliList.getRatings().get(2).getValue());




                            if (dtoPeliList.getRatings() != null && !dtoPeliList.getRatings().isEmpty()) {
                                intent2.putExtra("ratings", dtoPeliList.getRatings().get(0).getValue());
                            }
                            startActivity(intent2);
                        } else {
                            Log.d("fetchInfo", "La respuesta del servidor está vacía");
                        }
                    } else {
                        Log.d("fetchInfo", "Error en la solicitud: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<DtoPeli> call, Throwable t) {
                    Log.e("fetchInfo", "Error al realizar la solicitud: " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Log.d("fetchInfo", "No hay conexión a internet");
        }
    }
}
