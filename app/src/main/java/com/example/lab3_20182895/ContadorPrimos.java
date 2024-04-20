package com.example.lab3_20182895;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20182895.databinding.ContadorPrimosBinding;
import com.example.lab3_20182895.dto.Profile;
import com.example.lab3_20182895.services.NumeroPrimosService;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContadorPrimos extends AppCompatActivity {

    ContadorPrimosBinding contadorPrimosBinding;
    NumeroPrimosService numeroPrimosService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contadorPrimosBinding = ContadorPrimosBinding.inflate(getLayoutInflater());
        setContentView(contadorPrimosBinding.getRoot());



        contadorPrimosBinding.Pausar.setOnClickListener(view -> {
            ApplicationThreads application = (ApplicationThreads) getApplication();
            ExecutorService executorService = application.executorService;


            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    int contador = 1;
                    while (contador <= 10) {
                        contadorPrimosBinding.cont.setText(String.valueOf(contador));
                        contador++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });


        });

        Toast.makeText(this, "Tiene internet: " + tengoInternet(), Toast.LENGTH_LONG).show();

        numeroPrimosService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NumeroPrimosService.class);

        fetchPrimos();
    }



    public void fetchPrimos(){
        if(tengoInternet()){
            numeroPrimosService.getPrimeNumbers().enqueue(new Callback<List<Profile>>() {
                @Override
                public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                    if(response.isSuccessful()){
                        List<Profile> profileList = response.body();

                        contadorPrimosBinding.cont.setText(profileList.get(0).getNumber());



                        /*for(Profile c : profileList){

                            try {
                                Log.d("msg-test-ws-comments","id: "
                                        + c.getOrder() + " | body: " + c.getNumber());
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }*/




                    }
                }

                @Override
                public void onFailure(Call<List<Profile>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test-internet", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}
