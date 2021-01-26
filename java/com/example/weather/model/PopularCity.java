package com.example.weather.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PopularCity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String name;
    private String country;
    private double lat;
    private double lon;
    private boolean homeLocation = false;

    public PopularCity(String name, String country, double lat, double lon) {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(boolean home){
        this.homeLocation = home;
    }
}
