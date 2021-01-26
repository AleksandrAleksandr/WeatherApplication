package com.example.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.weather.adapters.FavoriteCityAdapter;
import com.example.weather.model.CityModel;
import com.example.weather.model.LocalCity;
import com.example.weather.viewmodels.FavoritesCityListViewModel;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoritesFragment extends Fragment {

    RecyclerView recyclerView;
    FavoriteCityAdapter adapter;
    FavoritesCityListViewModel viewModel;
    public static final String workTag = "work_tag";
    public static final String cityTag = "city_tag";

    public FavoritesFragment() {
        super(R.layout.favorites_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.favorites_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);

        adapter = new FavoriteCityAdapter(new FavoriteCityAdapter.ButtonClickListener() {
            @Override
            public void onButtonClick(LocalCity city) {
                if (city.homeLocation){
                    cancelWork();
                }
                onClickCity(city);
            }

            @Override
            public void onCityNameClick(LocalCity city) {
                CityModel cityModel = new CityModel(city.name, city.country, city.lat, city.lon);
                FavoritesFragmentDirections.ActionHomeToCityWeatherFragment action = FavoritesFragmentDirections.actionHomeToCityWeatherFragment(cityModel);

                Navigation.findNavController(getView()).navigate(action);
            }

            @Override
            public void onSetHomeClick(LocalCity oldCity, LocalCity newCity) {
                homeLocationChanged(oldCity, newCity);
            }
        });
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FavoritesCityListViewModel.class);

        viewModel.getAllCities().observe(getViewLifecycleOwner(), favoriteCities -> {
            adapter.refresh(favoriteCities);
        });


    }

    public void onClickCity(LocalCity city) {
        viewModel.onClickCity(city);
    }

    public void homeLocationChanged(LocalCity oldCity, LocalCity newCity) {
        viewModel.setNewHomeLocation(oldCity, newCity);

        updateNotificationWorker(newCity);
    }

    private void updateNotificationWorker(LocalCity city) {
        WorkManager.getInstance(getContext()).cancelAllWorkByTag(workTag);

        Data inputData = new Data.Builder().putString(cityTag, city.name).build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest notificationWork = new PeriodicWorkRequest.Builder(NotifyWorker.class, 30, TimeUnit.MINUTES)
                .setInputData(inputData)
                .setConstraints(constraints)
                .addTag(workTag)
                .build();

        WorkManager.getInstance(getContext()).enqueue(notificationWork);
    }

    private void cancelWork(){
        WorkManager.getInstance(getContext()).cancelAllWorkByTag(workTag);
    }
}
