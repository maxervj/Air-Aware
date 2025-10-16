package AirAware.com.network;

import androidx.lifecycle.LiveData;

import java.util.List;

import AirAware.com.model.AirQualityResponse;
import AirAware.com.model.Forecast;
import AirAware.com.model.GeocodingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("data/2.5/air_pollution")
    Call<AirQualityResponse> getCurrentAirQuality(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey
    );

    @GET("data/2.5/air_pollution/forecast")
    Call<AirQualityResponse> getAirQualityForecast(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey
    );

    @GET("geo/1.0/direct")
    Call<List<GeocodingResponse>> getCoordinatesByCityName(
            @Query("q") String cityName,
            @Query("limit") int limit,
            @Query("appid") String apiKey
    );

    // Deprecated methods - kept for backward compatibility
    @Deprecated
    Call<Forecast> getForecast(String lasVegas, String s, String metric, String fr);

    @Deprecated
    LiveData<List<Forecast>> getForecasts();

    @Deprecated
    Call<Forecast> getAirPollutionForecast(double lat, double lon, String apiKey);
}
