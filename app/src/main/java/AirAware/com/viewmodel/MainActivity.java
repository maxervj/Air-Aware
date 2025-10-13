package AirAware.com.viewmodel;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import AirAware.com.R;
import AirAware.com.model.Forecast;
import AirAware.com.network.OpenWeatherService;
import AirAware.com.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ======================= CODE AJOUTÉ ICI =======================

        // On crée un objet "service" à partir de notre interface.
        // Cet objet sait déjà comment parler à l’API grâce aux annotations (@GET, @Query…).
        // Retrofit crée automatiquement le code nécessaire pour faire les requêtes HTTP.
        OpenWeatherService service =
                RetrofitClientInstance.getInstance().create(OpenWeatherService.class);


        // Préparation de l’appel (pas encore exécuté). On fournit ici les paramètres attendus par l’API.
        // "London" : ville
        // "VOTRE_CLE_API" : ta clé API (il faut éviter de la mettre en dur dans le code !)
        // "metric" : unités en système métrique (°C)
        Call<Forecast> call =
                service.getForecast("Las Vegas", "619d368931beecb904e9f5410dc515d6", "metric", "fr" );


        // Exécution ASYNCHRONE de la requête.
        // Retrofit place l’appel en file et invoquera l’un des deux callbacks ci-dessous
        // quand la réponse réseau arrivera ou en cas d’erreur.
        call.enqueue(new Callback<Forecast>() {
            // Callback invoqué quand on reçoit une réponse HTTP du serveur (200, 404, 500, …).
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {

                // isSuccessful() couvre les codes 2xx. Toujours vérifier qu’il y a un body.
                if (response.isSuccessful() && response.body() != null) {

                    // Corps de réponse désérialisé par Gson en objet Forecast.
                    Forecast data = response.body();

                    // ➜ ICI : mettre à jour l’UI / ViewModel avec les données reçues.
                    // Par exemple, affichons la température dans les logs pour vérifier.
                    double temperature = data.getMain().getTemp();
                    Log.d(TAG, "Température reçue : " + temperature + "°C");
                    Toast.makeText(MainActivity.this, "Température : " + temperature + "°C", Toast.LENGTH_LONG).show();


                } else {
                    // la requête a bien “réussi” au sens réseau, mais le code HTTP n’est pas 2xx
                    // (ex : 401 clé invalide, 404 ville inconnue, 429 rate limit, 500 serveur …)
                    String message = "Erreur HTTP " + response.code();

                    // On essaie de lire le message d’erreur renvoyé pour aider au débogage.
                    try {
                        if (response.errorBody() != null) {
                            message += " : " + response.errorBody().string();
                        }
                    } catch (IOException ignored) {
                        // Ignoré
                    }
                    Log.e(TAG, "Erreur onResponse: " + message);
                    // Affichage utilisateur minimal
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            // Callback invoqué quand l’appel n’a pas pu aboutir (pas de réseau, timeout, etc.).
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.e(TAG, "Échec onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Échec réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // ======================= FIN DU CODE AJOUTÉ =======================
    }
    }
