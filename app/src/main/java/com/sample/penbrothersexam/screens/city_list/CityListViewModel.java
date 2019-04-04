package com.sample.penbrothersexam.screens.city_list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sample.penbrothersexam.model.City;
import com.sample.penbrothersexam.networking.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityListViewModel extends ViewModel {

    private final MutableLiveData<List<City>> cities = new MutableLiveData<>();
    private final MutableLiveData<Boolean> citiesLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading= new MutableLiveData<>();

    private Call<List<City>> citiesCall;
    private String searchKey;

    LiveData<List<City>> getCities(){
        return cities;
    }

    LiveData<Boolean> getError(){
        return citiesLoadError;
    }

    LiveData<Boolean> getLoading(){
        return loading;
    }

    public CityListViewModel(){
        fetchCities();
    }

    public void searchCities(String searchKey){
        this.searchKey = searchKey;
        fetchCities();
    }

    private void fetchCities() {
        loading.setValue(true);
        citiesCall = ApiClient.getInstance().getCities(searchKey);
        citiesCall.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                citiesLoadError.setValue(false);
                cities.setValue(response.body());
                loading.setValue(false);
                citiesCall = null;
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                citiesLoadError.setValue(true);
                loading.setValue(false);
                citiesCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (citiesCall != null) {
            citiesCall.cancel();
            citiesCall = null;
        }
    }
}
