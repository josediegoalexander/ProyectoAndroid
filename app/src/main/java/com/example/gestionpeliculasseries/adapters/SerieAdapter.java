package com.example.gestionpeliculasseries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpeliculasseries.R;
import com.example.gestionpeliculasseries.models.Serie;

import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.ViewHolder> {

    private Context context;
    private List<Serie> series;

    public SerieAdapter(Context context, List<Serie> series) {
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_serie, parent, false); // Necesitarás crear este layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Serie serie = series.get(position);
        holder.bind(serie);
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView; // Cambia el ID si es diferente en tu layout

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.nameTextView); // Asegúrate de que este ID exista en item_serie.xml
        }

        void bind(Serie serie) {
            titleTextView.setText(serie.getTitulo()); // Asumiendo que tu modelo Serie tiene un campo "titulo"
        }
    }
}