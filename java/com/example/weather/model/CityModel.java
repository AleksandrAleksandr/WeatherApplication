package com.example.weather.model;

import java.io.Serializable;

public class CityModel implements Serializable {
    public String name;
    public String country;
    public double lat;
    public double lon;

    public CityModel(String name, String country, double lat, double lon){
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

}
