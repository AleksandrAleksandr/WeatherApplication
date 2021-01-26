package com.example.weather.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerClient implements IServerClient {

    //static final String BASE_URL = "https://api.gismeteo.net/v2/";
    //static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    //static final String GEO_URL = "http://api.openweathermap.org/geo/1.0/";
    static final String BASE_URL = "https://api.openweathermap.org/";
    private Retrofit mRetrofit;
    private IApiDefinition mService;

    public ServerClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(IApiDefinition.class);
    }

    @Override
    public IApiDefinition getService() {
        return mService;
    }
}
