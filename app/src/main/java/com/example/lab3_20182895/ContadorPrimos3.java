package com.example.lab3_20182895;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.lab3_20182895.databinding.ContadorPrimos3Binding;
import com.example.lab3_20182895.workers.ContadorWorker3;

import java.util.Random;
import java.util.UUID;

public class ContadorPrimos3 extends AppCompatActivity {

    private ContadorPrimos3Binding contadorPrimos3Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contadorPrimos3Binding = ContadorPrimos3Binding.inflate(this.getLayoutInflater());
        setContentView(contadorPrimos3Binding.getRoot());


        UUID uuid = UUID.randomUUID();

        contadorPrimos3Binding.DescenderWorker.setOnClickListener(view -> {

            Data dataBuilder = new Data.Builder()
                    .putInt("numero", new Random().nextInt(10))
                    .build();

            WorkRequest workRequest =  new OneTimeWorkRequest.Builder(ContadorWorker3.class)
                    .setId(uuid)
                    .setInputData(dataBuilder)
                    .build();

            WorkManager
                    .getInstance(ContadorPrimos3.this.getApplicationContext())
                    .enqueue(workRequest);


            WorkManager.getInstance(contadorPrimos3Binding.getRoot().getContext())
                    .getWorkInfoByIdLiveData(uuid)
                    .observe(ContadorPrimos3.this, workInfo -> {
                        if(workInfo != null){
                            Data progress = workInfo.getProgress();
                            int contador = progress.getInt("contador", 0);
                            Log.d("msg-test", "progress: " + contador);
                            contadorPrimos3Binding.contWorker.setText(String.valueOf(contador));
                        }else{
                            Log.d("msg-test", "work info == null ");
                        }
                    });

            /*WorkManager.getInstance(binding.getRoot().getContext())
                    .getWorkInfoByIdLiveData(uuid)
                    .observe(MainActivity3.this, workInfo -> {
                if (workInfo != null) {
                    if (workInfo.getState() == WorkInfo.State.RUNNING) {
                        Data progress = workInfo.getProgress();
                        int contador = progress.getInt("contador", 0);
                        Log.d("msg-test", "progress: " + contador);
                        binding.contadorVal.setText(String.valueOf(contador));

                    } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        Data outputData = workInfo.getOutputData();
                        String texto = outputData.getString("info");
                        Log.d("msg-test", texto);

                    }
                } else {
                    Log.d("msg-test", "work info == null ");
                }
            });*/


        });
    }


}
