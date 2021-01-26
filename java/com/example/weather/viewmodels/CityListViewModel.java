package com.example.weather.viewmodels;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.weather.model.LocalCity;
import com.example.weather.model.SearchResponce;
import com.example.weather.repositories.CityRepository;
import com.example.weather.repositories.SearchRepository;

import java.util.List;

public class CityListViewModel extends ViewModel {
    private MutableLiveData<List<LocalCity>> cities;
    //private LiveData<List<LocalCity>> cities;
    private MutableLiveData<List<LocalCity>> location = new MutableLiveData<>();
    private final CityRepository mRepository;
    private final SearchRepository mSearchRepository;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    CityListViewModel(CityRepository repository, SearchRepository searchRepository, @Assisted SavedStateHandle savedStateHandle) {
        this.mRepository = repository;
        this.mSearchRepository = searchRepository;
        this.savedStateHandle = savedStateHandle;
    }

    public LiveData<List<LocalCity>> getPopularCities(){
        return mRepository.getPopularCities();
    }

    public LiveData<List<LocalCity>> findCity(String city){
        location = mSearchRepository.findCity(city);
        return location;
    }

    public void onClickCity(LocalCity city){
        mRepository.insert(city);
    }
}
