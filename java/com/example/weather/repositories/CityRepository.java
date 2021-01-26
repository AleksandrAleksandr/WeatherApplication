package com.example.weather.repositories;

import androidx.lifecycle.LiveData;
import com.example.weather.database.CityDatabase;
import com.example.weather.model.LocalCity;
import com.example.weather.model.PopularCity;

import java.util.List;

public class CityRepository {

    private CityDatabase mDatabase;

    public CityRepository(CityDatabase database){
        mDatabase = database;

//        ArrayList<PopularCity> popular = new ArrayList<>();
//        popular.add(new PopularCity("London", "GB", 51.5085, -0.1257));
//        popular.add(new PopularCity("Madrid", "ES", 40.4165, -3.7026));
//        popular.add(new PopularCity("Paris", "FR", 48.8534, 2.3488));
//        popular.add(new PopularCity("Rome", "IT", 41.8947, 12.4839));
//        popular.add(new PopularCity("Athens", "GR", 37.9795, 23.7162));
//        popular.add(new PopularCity("Oslo", "NO", 59.9127, 10.7461));
//        popular.add(new PopularCity("Cairo", "EG", 30.0626, 31.2497));
//        insertAll(popular);

    }

    public List<LocalCity> getAll(){
        return mDatabase.Dao().getAll();
    }

    public LiveData<List<LocalCity>> getAllCities(){
        return mDatabase.Dao().getAllCities();
    }

    public LiveData<List<LocalCity>> getPopularCities(){
        return mDatabase.Dao().getPopularCities();
    }

    public void insert(LocalCity city){
        CityDatabase.databaseWriteExecutor.execute(() ->{
            mDatabase.Dao().insert(city);
        });

    }

    public void insertAll(List<PopularCity> city){
        CityDatabase.databaseWriteExecutor.execute(() ->{
            mDatabase.Dao().insertAll(city);
        });

    }

    public void delete(LocalCity city){
        CityDatabase.databaseWriteExecutor.execute(() ->{
            mDatabase.Dao().delete(city);
        });

    }

    public void update(LocalCity city){
        CityDatabase.databaseWriteExecutor.execute(() ->{
            mDatabase.Dao().updateCity(city);
        });
    }

    public void updateCities(LocalCity oldCity, LocalCity newCity){
        if (oldCity == null){
            CityDatabase.databaseWriteExecutor.execute(() ->{
                mDatabase.Dao().updateCity(newCity);
            });
        }
        else {
            LocalCity[] cities = {oldCity, newCity};
            CityDatabase.databaseWriteExecutor.execute(() ->{
                mDatabase.Dao().updateCities(cities);
            });
        }

    }

}
