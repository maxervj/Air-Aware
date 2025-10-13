package AirAware.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {
    @SerializedName("temp")       private double temp;
    @SerializedName("feels_like") private double feelsLike;
    @SerializedName("humidity")   private int humidity;


    public double getTemp()      { return temp; }
    public double getFeelsLike() { return feelsLike; }
    public int getHumidity()     { return humidity; }
}

