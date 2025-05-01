package com.example.gestionpeliculasseries.models;

import com.example.gestionpeliculasseries.TheMovieDbApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
   // private static final String BASE_URL = "https://api.themoviedb.org/3/";
   private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit      retrofit      = null;
    private static TheMovieDbApi theMovieDbApi = null; // Cambiado al tipo de la interfaz TheMovieDbApi
    private static String        currentApiKey = null;

    // Metodo para obtener la instancia de Retrofit
    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Metodo para obtener la instancia de la interfaz TheMovieDbApi
    public static TheMovieDbApi getTheMovieDbApi(String apiKey) { // Cambiado el tipo de retorno a TheMovieDbApi
        // Verifica si ya existe una instancia con la misma API key
        if (theMovieDbApi == null || !apiKey.equals(currentApiKey)) {
            currentApiKey = apiKey;
            retrofit = getRetrofitClient(); // Asegura que Retrofit esté inicializado
            theMovieDbApi = retrofit.create(TheMovieDbApi.class); // Crea la instancia de la interfaz TheMovieDbApi
        }
        return theMovieDbApi;
    }

    public static com.example.gestionpeliculasseries.adapters.ApiMovieAdapter getClient(String apiKey) {
        return null; // Este metodo parece no estar implementado o no ser necesario
    }
}