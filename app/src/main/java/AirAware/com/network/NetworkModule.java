package AirAware.com.network;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return RetrofitClientInstance.getInstance();
    }

    @Provides
    @Singleton
    public WeatherApiService provideOpenWeatherService(Retrofit retrofit) {
        return retrofit.create(WeatherApiService.class);
    }
}

