package com.example.weather.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.weather.model.LocalCity;
import com.example.weather.model.SearchResponce;
import com.example.weather.network.IServerClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private MutableLiveData<List<LocalCity>> location = new MutableLiveData<>();
    private IServerClient mServerClient;

    public SearchRepository(IServerClient serverClient){
        mServerClient = serverClient;
    }

    public MutableLiveData<List<LocalCity>> findCity(String city){
        mServerClient.getService().findCity(city).enqueue(new Callback<List<SearchResponce>>() {
            @Override
            public void onResponse(Call<List<SearchResponce>> call, Response<List<SearchResponce>> response) {
                //location.postValue(null);
                if (response.isSuccessful() && response.body().size() != 0){
                    List<LocalCity> localCities = new ArrayList<>();
                    for (SearchResponce city : response.body()) {
                        localCities.add(new LocalCity(city.name, city.country, city.lat, city.lon));
                    }
                    location.postValue(localCities);
                }
            }

            @Override
            public void onFailure(Call<List<SearchResponce>> call, Throwable t) {
                location.setValue(null);
            }
        });
        return location;
    }
}
