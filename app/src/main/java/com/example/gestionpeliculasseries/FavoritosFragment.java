package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpeliculasseries.adapters.ApiFavoritoAdapter;
import com.example.gestionpeliculasseries.models.ApiClient;
import com.example.gestionpeliculasseries.models.Favorito;
import com.example.gestionpeliculasseries.models.MovieResponse;
import com.example.gestionpeliculasseries.models.SerieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosFragment extends Fragment {

    private RecyclerView recyclerViewFavoritos;
    private ApiFavoritoAdapter apiFavoritoAdapter; // Adaptador para mostrar favoritos de la API
    private DatabaseHelper     dbHelper;
    private List<Object>       listaFavoritosApi; // Lista para almacenar tanto ApiMovie como ApiSerie

    private static final String API_KEY = "556521d6f530e1b6f571a078b48daf8c"; // ¡Tu API key!
    private static final String LANGUAGE = "es-ES";
    private TheMovieDbApi apiService;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        recyclerViewFavoritos = view.findViewById(R.id.recyclerViewFavoritos);
        recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        apiService = ApiClient.getTheMovieDbApi(API_KEY);
        listaFavoritosApi = new ArrayList<>();
        apiFavoritoAdapter = new ApiFavoritoAdapter(getContext(), listaFavoritosApi);
        recyclerViewFavoritos.setAdapter(apiFavoritoAdapter);

        loadFavoritos();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoritos();
    }

    private void loadFavoritos() {
        long userId = 1; // Simulación - Aquí deberías obtener el ID del usuario real
        List<Favorito> favoritosLocales = dbHelper.getAllFavoritosDeUsuario(userId);
        listaFavoritosApi.clear();
        apiFavoritoAdapter.notifyDataSetChanged();

        for (Favorito favorito : favoritosLocales) {
            if (favorito.getTipo().equals("pelicula")) {
                cargarDetallePelicula(favorito.getItemId());
            } else if (favorito.getTipo().equals("serie")) {
                cargarDetalleSerie(favorito.getItemId());
            }
        }
    }

    private void cargarDetallePelicula(int movieId) {
        Call<MovieResponse> call = apiService.getMovieDetails(movieId, API_KEY, LANGUAGE);
        call.enqueue(new Callback<MovieResponse>() { // Cambiado a Callback<MovieResponse>
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaFavoritosApi.add(response.body()); // Añade el MovieResponse a la lista
                    apiFavoritoAdapter.notifyItemInserted(listaFavoritosApi.size() - 1);
                } else {
                    Log.e("API_ERROR_FAV", "Error al cargar detalle de película favorita: " + response.message());
                    Toast.makeText(getContext(), "Error al cargar detalle de película", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) { // Cambiado a Call<MovieResponse>
                Log.e("API_FAILURE_FAV", "Fallo al cargar detalle de película favorita: " + t.getMessage());
                Toast.makeText(getContext(), "Fallo al cargar detalle de película", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDetalleSerie(int serieId) {
        // Asumiendo que tienes un metodo getSerieDetails en tu TheMovieDbApi
        Call<SerieResponse> call = apiService.getSerieDetails(serieId, API_KEY, LANGUAGE);
        call.enqueue(new Callback<SerieResponse>() { // Cambiado a Callback<SerieResponse>
            @Override
            public void onResponse(Call<SerieResponse> call, Response<SerieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaFavoritosApi.add(response.body()); // Añade el SerieResponse a la lista
                    apiFavoritoAdapter.notifyItemInserted(listaFavoritosApi.size() - 1);
                } else {
                    Log.e("API_ERROR_FAV", "Error al cargar detalle de serie favorita: " + response.message());
                    Toast.makeText(getContext(), "Error al cargar detalle de serie", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SerieResponse> call, Throwable t) { // Cambiado a Call<SerieResponse>
                Log.e("API_FAILURE_FAV", "Fallo al cargar detalle de serie favorita: " + t.getMessage());
                Toast.makeText(getContext(), "Fallo al cargar detalle de serie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}