package AirAware.com.repository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import AirAware.com.model.Forecast;
import AirAware.com.network.OpenWeatherService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirQualityRepository {


    @Singleton
    public class ForecastsRepository {

        private final OpenWeatherService service;
        private final MutableLiveData<List<Forecast>> forecastsLiveData = new MutableLiveData<>(new ArrayList<>());

        @Inject
        public ForecastsRepository(OpenWeatherService service) {
            this.service = service;
        }
        public LiveData<List<Forecast>> getAll() {
            return forecastsLiveData;
        }

        public LiveData<Forecast> fetchAirPollutionForecast(double lat, double lon, String apiKey) {
            MutableLiveData<Forecast> airPollutionData = new MutableLiveData<>();

            Call<Forecast> call = service.getAirPollutionForecast(lat, lon, apiKey);
            call.enqueue(new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        airPollutionData.postValue(response.body());
                    } else {
                        airPollutionData.postValue(null);
                    }
                }

                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    airPollutionData.postValue(null);
                }
            });
            return airPollutionData;
        }
    }
}
