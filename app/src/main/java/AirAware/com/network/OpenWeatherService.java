package AirAware.com.network;

import AirAware.com.model.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {


    Call<Forecast> getForecast(String lasVegas, String s, String metric, String fr);

    public interface openWeatherService {
        @GET("weather")
        Call<Forecast> getForecast(
                @Query("q") String city,         // "London" par ex.
                @Query("appid") String apiKey,   // à mettre dans BuildConfig
                @Query("units") String units,     // "metric" pour °C
                @Query("lang") String lang // langue
        );
    }

}
