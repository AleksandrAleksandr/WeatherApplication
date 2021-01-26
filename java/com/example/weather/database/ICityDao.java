package com.example.weather.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weather.model.LocalCity;
import com.example.weather.model.PopularCity;

import java.util.List;

@Dao
public interface ICityDao {
    @Query("SELECT * FROM LocalCity")
    List<LocalCity> getAll();

    @Query("SELECT * FROM LocalCity")
    LiveData<List<LocalCity>> getAllCities();

    @Query("SELECT * FROM PopularCity")
    LiveData<List<LocalCity>> getPopularCities();

    @Query("SELECT COUNT(*) FROM PopularCity")
    int count();

    @Update
    void updateCity(LocalCity city);

    @Update
    void updateCities(LocalCity... city);

    @Insert(onConflict=OnConflictStrategy.IGNORE)
    void insert(LocalCity city);

    @Insert
    void insert(PopularCity city);

    @Insert
    void insertAll(LocalCity... cities);

    @Insert
    void insertAll(List<PopularCity> city);

    @Delete
    void delete(LocalCity city);
}
