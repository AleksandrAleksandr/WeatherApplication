package com.example.weather.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LocalCity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String country;
    public double lat;
    public double lon;
    public boolean homeLocation = false;

    public LocalCity(String name, String country, double lat, double lon){
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }
}
