package com.example.gestionpeliculasseries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpeliculasseries.adapters.ApiMovieAdapter; // Importa la interfaz de la API
import com.example.gestionpeliculasseries.models.ApiClient;
import com.example.gestionpeliculasseries.models.MovieResponse; // Asegúrate de que la ruta a MovieResponse sea correcta
import com.example.gestionpeliculasseries.adapters.PeliculaAdapter; // Asegúrate de que la ruta a PeliculaAdapter sea correcta
import com.example.gestionpeliculasseries.models.Pelicula; // Asegúrate de que la ruta a Pelicula sea correcta

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.Gson;
import java.io.IOException;

public class PeliculasFragment extends Fragment {

    private Button buttonAddPelicula;
    private RecyclerView recyclerViewPeliculas;
    private PeliculaAdapter peliculaAdapter;
    private ApiMovieAdapter                                       apiMovieAdapter;
    private DatabaseHelper                                        dbHelper;
    private List<com.example.gestionpeliculasseries.models.Movie> popularMoviesList;

    private static final String API_KEY = "556521d6f530e1b6f571a078b48daf8c";
    private static final String LANGUAGE = "es";

    public PeliculasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peliculas, container, false);

        buttonAddPelicula = view.findViewById(R.id.buttonAddPelicula);
        recyclerViewPeliculas = view.findViewById(R.id.recyclerViewPeliculas);
        recyclerViewPeliculas.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadLocalPeliculas();

        buttonAddPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPeliculaActivity.class);
                startActivity(intent);
            }
        });

        loadPopularMoviesFromApi();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLocalPeliculas();
    }

    private void loadLocalPeliculas() {
        List<Pelicula> peliculasLocales = dbHelper.getAllPeliculas();
        peliculaAdapter = new PeliculaAdapter(getContext(), peliculasLocales);
        recyclerViewPeliculas.setAdapter(peliculaAdapter);
		recyclerViewPeliculas.setVisibility(View.VISIBLE);
    }

    private void loadPopularMoviesFromApi() {
        TheMovieDbApi apiService = ApiClient.getTheMovieDbApi(API_KEY); // ¡Usa getTheMovieDbApi aquí!
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, LANGUAGE);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
				
				
				int statusCode = response.code();
				Log.d("API_RESPONSE", "Status code: " + statusCode);
				
				String responseBody = "";
				try {
					if (response.errorBody() != null) {
						responseBody = response.errorBody().string();
						Log.d("API_RESPONSE", "Error body: " + responseBody);
					} else if (response.body() != null) {
						// Para mostrar el JSON completo puedes usar una librería como GSON
						Gson gson = new Gson();
						responseBody = gson.toJson(response.body());
						Log.d("API_RESPONSE", "Response body: " + responseBody);
					}
				} catch (IOException e) {
					Log.e("API_RESPONSE", "Error al leer la respuesta: " + e.getMessage());
				}
                if (response.isSuccessful() && response.body() != null) {
                    popularMoviesList = response.body().getResults();
                    Log.d("API_RESPONSE", "Películas populares cargadas: " + popularMoviesList.size());
                    apiMovieAdapter = new ApiMovieAdapter(getContext(), popularMoviesList);
                    recyclerViewPeliculas.setAdapter(apiMovieAdapter);
                } else {
                    Log.e("API_ERROR", "Error al cargar películas populares: " + response.message());
                    Toast.makeText(getContext(), "Error al cargar películas de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Fallo en la llamada a la API: " + t.getMessage());
                Toast.makeText(getContext(), "Fallo en la llamada a la API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}