package com.example.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponce {
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("lat")
    @Expose
    public Double lat;

    @SerializedName("lon")
    @Expose
    public Double lon;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("state")
    @Expose
    public String state;
}
