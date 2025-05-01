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
import com.example.gestionpeliculasseries.models.ApiSerie;

import java.util.List;

public class ApiSerieAdapter extends RecyclerView.Adapter<ApiSerieAdapter.ViewHolder> {

    private Context context;
    private List<ApiSerie> popularSeries;

    public ApiSerieAdapter(Context context, List<ApiSerie> popularSeries) {
        this.context = context;
        this.popularSeries = popularSeries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_api_serie, parent, false); // Usamos el layout que creaste
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiSerie serie = popularSeries.get(position);
        holder.bind(serie);
    }

    @Override
    public int getItemCount() {
        return popularSeries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView nameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView); // Asegúrate de que estos IDs coincidan con tu layout item_api_serie.xml
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }

        void bind(ApiSerie serie) {
            nameTextView.setText(serie.getName());
            String posterUrl = "https://image.tmdb.org/t/p/w500" + serie.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_launcher_foreground) // Imagen de carga
                    .error(R.drawable.ic_favorite) // Imagen en caso de error
                    .into(posterImageView);
        }
    }
}