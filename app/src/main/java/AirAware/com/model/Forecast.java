package AirAware.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Forecast implements Serializable {
    @SerializedName("main") private Weather weather;
    @SerializedName("dt")   private long dt;


    public Weather getMain() { return weather; }
    public long getDt()    { return dt; }
}
