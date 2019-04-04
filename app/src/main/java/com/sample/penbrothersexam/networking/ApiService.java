package com.sample.penbrothersexam.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sample.penbrothersexam.model.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cities")
    Call<List<City>> getCities(@Query("q") @Nullable String searchKey);

}
