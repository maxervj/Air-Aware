package AirAware.com.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import AirAware.com.model.Forecast;
import AirAware.com.network.OpenWeatherService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Response;

@HiltViewModel
public class ForecastsViewModel extends ViewModel {
    private final OpenWeatherService openWeatherServiceRepo;

    private final MutableLiveData<List<Forecast>> forecasts = new MutableLiveData<>();

    @Inject
    public ForecastsViewModel(OpenWeatherService repo) {

        this.openWeatherServiceRepo = repo;
    }

    public OpenWeatherService getOpenWeatherServiceRepo() {
        return openWeatherServiceRepo;
    }

    public LiveData<List<Forecast>> getForecasts() {
        return openWeatherServiceRepo.getForecasts();
    }



    public LiveData<Forecast> fetchAirPollutionForecast(double lat, double lon, String apiKey) {
        MutableLiveData<Forecast> airPollutionLiveData = new MutableLiveData<>();
        Call<Forecast> call = openWeatherServiceRepo.getAirPollutionForecast(lat, lon, apiKey);
        call.enqueue(new retrofit2.Callback<Forecast>() {
            @Override
            public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
                if (response.isSuccessful() && response.body() != null) {
                    airPollutionLiveData.setValue(response.body());
                    Log.d(TAG, "Air pollution data received successfully");
                } else {
                    Log.e(TAG, "Error: " + response.code() + " - " + response.message());
                    try {
                        if (response.errorBody() != null) {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    airPollutionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
                Log.e(TAG, "Air pollution API failure: " + t.getMessage());
                airPollutionLiveData.setValue(null);
            }
        });
        return airPollutionLiveData;
    }
}