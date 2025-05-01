package com.example.gestionpeliculasseries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestionpeliculasseries.R;
import com.example.gestionpeliculasseries.models.ApiMovie;
import com.example.gestionpeliculasseries.models.ApiSerie;

import java.util.List;

public class ApiFavoritoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> favoritos; // Puede contener ApiMovie o ApiSerie
    private static final int VIEW_TYPE_MOVIE = 0;
    private static final int VIEW_TYPE_SERIE = 1;

    public ApiFavoritoAdapter(Context context, List<Object> favoritos) {
        this.context = context;
        this.favoritos = favoritos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_MOVIE) {
            View view = inflater.inflate(R.layout.item_api_movie, parent, false); // Necesitarás crear este layout
            return new MovieViewHolder(view);
        } else if (viewType == VIEW_TYPE_SERIE) {
            View view = inflater.inflate(R.layout.item_api_serie, parent, false); // Necesitarás crear este layout
            return new SerieViewHolder(view);
        }
        throw new IllegalArgumentException("Tipo de vista desconocido: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object favorito = favoritos.get(position);
        if (holder instanceof MovieViewHolder && favorito instanceof ApiMovie) {
            ApiMovie movie = (ApiMovie) favorito;
            ((MovieViewHolder) holder).bind(movie);
        } else if (holder instanceof SerieViewHolder && favorito instanceof ApiSerie) {
            ApiSerie serie = (ApiSerie) favorito;
            ((SerieViewHolder) holder).bind(serie);
        }
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (favoritos.get(position) instanceof ApiMovie) {
            return VIEW_TYPE_MOVIE;
        } else if (favoritos.get(position) instanceof ApiSerie) {
            return VIEW_TYPE_SERIE;
        }
        return -1; // Nunca debería llegar aquí
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView); // Asegúrate de que estos IDs existan en item_api_movie.xml
            titleTextView = itemView.findViewById(R.id.nameTextView);
        }

        void bind(ApiMovie movie) {
            titleTextView.setText(movie.getTitle());
            String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_launcher_foreground) // Imagen de carga
                    .error(R.drawable.ic_favorite) // Imagen en caso de error
                    .into(posterImageView);
        }
    }

    static class SerieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView nameTextView;

        SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView); // Asegúrate de que estos IDs existan en item_api_serie.xml
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }

        void bind(ApiSerie serie) {
            nameTextView.setText(serie.getName());
            String posterUrl = "https://image.tmdb.org/t/p/w500" + serie.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_favorite)
                    .into(posterImageView);
        }
    }
}