package com.example.gestionpeliculasseries.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestionpeliculasseries.R;
import com.example.gestionpeliculasseries.models.Movie;

import java.util.List;

public class ApiMovieAdapter extends RecyclerView.Adapter<ApiMovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> popularMovies;

    public ApiMovieAdapter(Context context, List<Movie> popularMovies) {
        this.context = context;
        this.popularMovies = popularMovies;
        Log.d("ApiMovieAdapter", "Constructor: recibidas " + popularMovies.size() + " películas");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_api_movie, parent, false); // Reutilizamos el layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = popularMovies.get(position);
        Log.d("onBindViewHolder", "Película a mostrar: " + movie.getTitle());
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return popularMovies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.nameTextView);
        }

        void bind(Movie movie) {
			Log.d("ApiMovieAdapter", "Configurando película: " + movie.getTitle());

            titleTextView.setText(movie.getTitle());
            String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_favorite)
                    .into(posterImageView);
        }
    }
}