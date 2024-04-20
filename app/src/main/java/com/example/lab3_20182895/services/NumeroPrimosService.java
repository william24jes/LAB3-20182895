package com.example.lab3_20182895.services;

import com.example.lab3_20182895.dto.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NumeroPrimosService {

    @GET("/primeNumbers?len=999&order=1")
    Call<List<Profile>> getPrimeNumbers();
}
