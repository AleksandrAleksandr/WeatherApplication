package com.example.weather.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.weather.model.server.Forecast;
import com.example.weather.model.server.ServerResponce;
import com.example.weather.network.IServerClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastsRepository{

    private MutableLiveData<List<Forecast>> forecast = new MutableLiveData<>();
    private IServerClient mServerClient;

    public ForecastsRepository(IServerClient serverClient){
        mServerClient = serverClient;
    }

    public MutableLiveData<List<Forecast>> getCityForecast(String city){

        mServerClient.getService().getForecasts(city, "da8d4701bff23d868ff44b24852181db")
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.isSuccessful()){
                            forecast.postValue(response.body().forecasts);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {
                        forecast.setValue(null);
                    }
                });
        return forecast;
    }

    public MutableLiveData<List<Forecast>> getCityForecastByCoord(double lat, double lon){

        mServerClient.getService().getForecastsByCoord(lat, lon)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.isSuccessful()){
                            forecast.postValue(response.body().forecasts);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {
                        forecast.setValue(null);
                    }
                });
        return forecast;
    }
}
