package com.example.weather.viewmodels;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.weather.model.LocalCity;
import com.example.weather.repositories.CityRepository;
import java.util.List;

public class FavoritesCityListViewModel extends ViewModel {
    private LiveData<List<LocalCity>> cities;
    private final CityRepository mRepository;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    FavoritesCityListViewModel(CityRepository repository, @Assisted SavedStateHandle savedStateHandle) {
        this.mRepository = repository;
        this.savedStateHandle = savedStateHandle;
    }


    public LiveData<List<LocalCity>> getAllCities(){
        if (cities == null) {
            cities = mRepository.getAllCities();
        }
        return cities;
    }

    public void onClickCity(LocalCity city){
        mRepository.delete(city);
    }

    public void setNewHomeLocation(LocalCity oldCity, LocalCity newCity){
        mRepository.updateCities(oldCity, newCity);
    }
}
