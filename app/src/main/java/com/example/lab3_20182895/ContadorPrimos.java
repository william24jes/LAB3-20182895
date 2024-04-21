package com.example.lab3_20182895;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab3_20182895.databinding.ContadorPrimosBinding;
import com.example.lab3_20182895.dto.Profile;
import com.example.lab3_20182895.services.NumeroPrimosService;
import com.example.lab3_20182895.viewmodel.ContadorPrimoViewModel;

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

    List<Profile> listaDeProfile;

    boolean verificarPrimeraVezAD = true;

    boolean verificarDescenso = true; // Descendiente=true; Ascendente=false
    int contadorVerificador=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contadorPrimosBinding = ContadorPrimosBinding.inflate(getLayoutInflater());
        setContentView(contadorPrimosBinding.getRoot());

        Toast.makeText(this, "Tiene internet: " + tengoInternet(), Toast.LENGTH_LONG).show();

        numeroPrimosService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NumeroPrimosService.class);

        fetchPrimos();

        ApplicationThreads application = (ApplicationThreads) getApplication();
        ExecutorService executorService = application.executorService;
        ExecutorService executorService2 = application.executorService;

        ContadorPrimoViewModel contadorPrimoViewModel =
                new ViewModelProvider(ContadorPrimos.this).get(ContadorPrimoViewModel.class);

        contadorPrimoViewModel.getContador().observe(this, contador -> {
            //aquí o2
            contadorPrimosBinding.cont.setText(String.valueOf(contador));
        });

        contadorPrimosBinding.Descender.setOnClickListener(view -> {
            setContadorVerificador(getContadorVerificador()+1);
            if (getContadorVerificador()%2!=0){
                setVerificarDescenso(true);
            }else {
                setVerificarDescenso(false);
            }


            if (isVerificarDescenso()==true){
                executorService.execute(() -> {

                    for (int i = 0; i <= getListaDeProfile().size() - 1; i++) {
                        if (isVerificarDescenso()==false){
                            break;
                        }
                        contadorPrimoViewModel.getContador().postValue(Integer.parseInt(getListaDeProfile().get(i).getNumber())); // o1

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }else{

                executorService.execute(() -> {

                    for (int i = 998; i <= getListaDeProfile().size() - 1; i--) {
                        if (isVerificarDescenso()==true){
                            break;
                        }
                        contadorPrimoViewModel.getContador().postValue(Integer.parseInt(getListaDeProfile().get(i).getNumber())); // o1

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

            }

        });


    }


    public void fetchPrimos() {
        if (tengoInternet()) {
            numeroPrimosService.getPrimeNumbers().enqueue(new Callback<List<Profile>>() {
                @Override
                public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                    if (response.isSuccessful()) {
                        List<Profile> profileList = response.body();

                        setListaDeProfile(profileList);

                        /*contadorPrimosBinding.cont.setText(profileList.get(0).getNumber());



                        for(Profile c : profileList){

                            try {
                                Log.d("msg-test-ws-comments","id: "
                                        + c.getOrder() + " | body: " + c.getNumber());
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }



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

    public List<Profile> getListaDeProfile() {
        return listaDeProfile;
    }

    public void setListaDeProfile(List<Profile> listaDeProfile) {
        this.listaDeProfile = listaDeProfile;
    }

    public boolean isVerificarPrimeraVezAD() {
        return verificarPrimeraVezAD;
    }

    public void setVerificarPrimeraVezAD(boolean verificarPrimeraVezAD) {
        this.verificarPrimeraVezAD = verificarPrimeraVezAD;
    }

    public boolean isVerificarDescenso() {
        return verificarDescenso;
    }

    public void setVerificarDescenso(boolean verificarDescenso) {
        this.verificarDescenso = verificarDescenso;
    }

    public int getContadorVerificador() {
        return contadorVerificador;
    }

    public void setContadorVerificador(int contadorVerificador) {
        this.contadorVerificador = contadorVerificador;
    }
}
