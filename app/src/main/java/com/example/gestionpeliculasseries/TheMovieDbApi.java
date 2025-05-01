package com.example.gestionpeliculasseries;

import com.example.gestionpeliculasseries.models.MovieResponse;
import com.example.gestionpeliculasseries.models.SerieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbApi {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/popular")
    Call<SerieResponse> getPopularSeries(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/{tv_id}") // Reemplaza "tv/{tv_id}" con el endpoint correcto para detalles de series
    Call<SerieResponse> getSerieDetails(
            @Path("tv_id") int tvId, // El ID de la serie va en la ruta
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}