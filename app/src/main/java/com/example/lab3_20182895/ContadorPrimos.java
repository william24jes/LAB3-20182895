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
import com.example.lab3_20182895.viewmodel.ButtonViewModel;
import com.example.lab3_20182895.viewmodel.ContadorPrimoViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContadorPrimos extends AppCompatActivity {

    ContadorPrimosBinding contadorPrimosBinding;
    NumeroPrimosService numeroPrimosService;

    List<Profile> listaDeProfile;

    boolean verificarDescenso = true; // Descendiente=true; Ascendente=false
    int ordenGuardar = 0;

    ExecutorService executorServiceAscender = Executors.newSingleThreadExecutor();
    ExecutorService executorServiceDescender = Executors.newSingleThreadExecutor();

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

        //ContadorViewModel
        ContadorPrimoViewModel contadorPrimoViewModel =
                new ViewModelProvider(ContadorPrimos.this).get(ContadorPrimoViewModel.class);

        contadorPrimoViewModel.getContador().observe(this, contador -> {
            contadorPrimosBinding.cont.setText(String.valueOf(contador));
        });

        //ButtonViewModel
        ButtonViewModel buttonViewModel =
                new ViewModelProvider(ContadorPrimos.this).get(ButtonViewModel.class);

        buttonViewModel.getButton().observe(this, button -> {
            contadorPrimosBinding.Descender.setText(button);
        });

        contadorPrimosBinding.Descender.setOnClickListener(view -> {
            if (isVerificarDescenso()==true){
                setVerificarDescenso(false);
            }else{
                setVerificarDescenso(true);
            }

            if (verificarDescenso) {

                iniciarDescensoAutomatico(executorServiceDescender,executorServiceAscender, isVerificarDescenso(),  contadorPrimoViewModel,buttonViewModel);

            } else {

                iniciarAscensoAutomatico(executorServiceAscender, executorServiceDescender, isVerificarDescenso(), contadorPrimoViewModel,buttonViewModel);
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
                        listaDeProfile = profileList;
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

    private void iniciarDescensoAutomatico(ExecutorService executorServiceDescender,ExecutorService executorServiceAscender, boolean verificarDescenso, ContadorPrimoViewModel contadorPrimoViewModel, ButtonViewModel buttonViewModel) {
        buttonViewModel.getButton().postValue("Ascender");
        executorServiceDescender.execute(() -> {
            for (int i = getOrdenGuardar(); i >= 0; i--) {
                if (!isVerificarDescenso()) {
                    break;
                }
                int num = Integer.parseInt(listaDeProfile.get(i).getNumber());
                contadorPrimoViewModel.getContador().postValue(num);
                setOrdenGuardar(i);
                if (i == 0) {
                    setVerificarDescenso(false);
                    iniciarAscensoAutomatico(executorServiceAscender, executorServiceDescender, false, contadorPrimoViewModel, buttonViewModel);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void iniciarAscensoAutomatico(ExecutorService executorServiceAscender,ExecutorService executorServiceDescender ,boolean verificarDescenso, ContadorPrimoViewModel contadorPrimoViewModel, ButtonViewModel buttonViewModel) {
        buttonViewModel.getButton().postValue("Descender");
        executorServiceAscender.execute(() -> {
            for (int i = getOrdenGuardar(); i < listaDeProfile.size(); i++) {
                if (isVerificarDescenso()) {
                    break;
                }
                int num = Integer.parseInt(listaDeProfile.get(i).getNumber());
                contadorPrimoViewModel.getContador().postValue(num);
                setOrdenGuardar(i);
                if (i == listaDeProfile.size() - 1) {
                    setVerificarDescenso(true);
                    iniciarDescensoAutomatico(executorServiceDescender, executorServiceAscender, true, contadorPrimoViewModel, buttonViewModel);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public boolean isVerificarDescenso() {
        return verificarDescenso;
    }

    public void setVerificarDescenso(boolean verificarDescenso) {
        this.verificarDescenso = verificarDescenso;
    }

    public int getOrdenGuardar() {
        return ordenGuardar;
    }

    public void setOrdenGuardar(int ordenGuardar) {
        this.ordenGuardar = ordenGuardar;
    }


}
