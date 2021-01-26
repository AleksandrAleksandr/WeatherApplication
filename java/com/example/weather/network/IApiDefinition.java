package com.example.weather.network;

import com.example.weather.model.SearchResponce;
import com.example.weather.model.server.ServerResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiDefinition {
//    @Headers("X-Gismeteo-Token: 56b30cb255.3443075")
//    @GET("weather/forecast/by_day_part/")
//    Call<List<Forecast>> getCityForecasts(@Query("latitude") Double latitude,
//                                          @Query("longitude") Double longitude,
//                                          @Query("days") int days);


    @GET("data/2.5/forecast?units=metric&lang=ru")
    Call<ServerResponce> getForecasts(@Query("q") String city, @Query("appid") String key);

    @GET("data/2.5/forecast?appid=da8d4701bff23d868ff44b24852181db")
    Call<ServerResponce> getForecasts2(@Query("q") String city);

    @GET("data/2.5/forecast?appid=da8d4701bff23d868ff44b24852181db&units=metric&lang=ru")
    Call<ServerResponce> getForecastsByCoord(@Query("lat") double lat, @Query("lon") double lon);

    @GET("geo/1.0/direct?appid=da8d4701bff23d868ff44b24852181db&limit=3")
    Call<List<SearchResponce>> findCity(@Query("q") String city);
}
