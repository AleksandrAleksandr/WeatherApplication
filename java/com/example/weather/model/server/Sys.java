
package com.example.weather.model.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("pod")
    @Expose
    public String pod;

}
