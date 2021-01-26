package com.example.weather.di;

import android.app.Application;

import androidx.room.Room;

import com.example.weather.database.CityDatabase;
import com.example.weather.network.IServerClient;
import com.example.weather.network.ServerClient;
import com.example.weather.repositories.CityRepository;
import com.example.weather.repositories.ForecastsRepository;
import com.example.weather.repositories.SearchRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class ApplicationModule {

    @Provides
    @Singleton
    public IServerClient provideServerClient(){
        return new ServerClient();
    }

    @Provides
    @Singleton
    public ForecastsRepository provideRepo(IServerClient serverClient){
        return new ForecastsRepository(serverClient);
    }

    @Provides
    @Singleton
    public CityRepository provideCityRepo(CityDatabase database){
        return new CityRepository(database);
    }

    @Provides
    @Singleton
    public SearchRepository provideSearchRepo(IServerClient serverClient){
        return new SearchRepository(serverClient);
    }

    @Provides
    @Singleton
    public CityDatabase provideRoom(Application application){
        CityDatabase database = Room.databaseBuilder(
                application, CityDatabase.class,
                "CityDataBase")
                .build();
        database.populateInitialData();
        return database;
//        return Room.databaseBuilder(
//                application, CityDatabase.class,
//                "CityDataBase")
//                .addCallback(CityDatabase.sRoomDatabaseCallback)
//                .build();
    }
}
