package com.example.weather.viewmodels;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.weather.model.server.Forecast;
import com.example.weather.repositories.ForecastsRepository;

import java.util.List;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<List<Forecast>> weathers;
    private final ForecastsRepository mRepository;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    WeatherViewModel(ForecastsRepository repository, @Assisted SavedStateHandle savedStateHandle) {
        this.mRepository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    public LiveData<List<Forecast>> getWeathers(double lat, double lon) {
        if (weathers == null) {
            //weathers = mRepository.getCityForecast(city);
            weathers = mRepository.getCityForecastByCoord(lat, lon);
        }
        return weathers;
    }



}