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

import com.example.gestionpeliculasseries.adapters.ApiSerieAdapter;
import com.example.gestionpeliculasseries.adapters.SerieAdapter;
import com.example.gestionpeliculasseries.models.ApiClient;
import com.example.gestionpeliculasseries.models.ApiSerie;
import com.example.gestionpeliculasseries.models.Serie;
import com.example.gestionpeliculasseries.models.SerieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesFragment extends Fragment {

    private Button buttonAddSerie;
    private RecyclerView recyclerViewSeries;
    private SerieAdapter serieAdapter;
    private ApiSerieAdapter apiSerieAdapter; // Adaptador para series de la API
    private DatabaseHelper  dbHelper;
    private List<ApiSerie>  popularSeriesList; // Lista para las series de la API

    private static final String API_KEY = "TU_API_KEY"; // ¡Reemplaza con tu API key!
    private static final String LANGUAGE = "es";

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, container, false);

        buttonAddSerie = view.findViewById(R.id.buttonAddSerie);
        recyclerViewSeries = view.findViewById(R.id.recyclerViewSeries);
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadLocalSeries(); // Cambié el nombre del metodo a loadLocalSeries para claridad

        buttonAddSerie.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSerieActivity.class);
            startActivity(intent);
        });

        loadPopularSeriesFromApi(); // Llama al metodo para cargar series de la API

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLocalSeries();
    }

    private void loadLocalSeries() {
        List<Serie> seriesLocales = dbHelper.getAllSeries();
        serieAdapter = new SerieAdapter(getContext(), seriesLocales);
        recyclerViewSeries.setAdapter(serieAdapter);
    }

    private void loadPopularSeriesFromApi() {
        TheMovieDbApi apiService = ApiClient.getTheMovieDbApi(API_KEY);
        Call<SerieResponse> call = apiService.getPopularSeries(API_KEY, LANGUAGE); // Asumiendo que tienes este metodo en tu interfaz

        call.enqueue(new Callback<SerieResponse>() {
            @Override
            public void onResponse(Call<SerieResponse> call, Response<SerieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    popularSeriesList = response.body().getResults();
                    Log.d("API_RESPONSE_SERIES", "Series populares cargadas: " + popularSeriesList.size());
                    apiSerieAdapter = new ApiSerieAdapter(getContext(), popularSeriesList);
                    recyclerViewSeries.setAdapter(apiSerieAdapter);
                } else {
                    Log.e("API_ERROR_SERIES", "Error al cargar series populares: " + response.message());
                    Toast.makeText(getContext(), "Error al cargar series de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SerieResponse> call, Throwable t) {
                Log.e("API_FAILURE_SERIES", "Fallo en la llamada a la API de series: " + t.getMessage());
                Toast.makeText(getContext(), "Fallo en la llamada a la API de series", Toast.LENGTH_SHORT).show();
            }
        });
    }
}