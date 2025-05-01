package com.example.gestionpeliculasseries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpeliculasseries.R;
import com.example.gestionpeliculasseries.models.Pelicula;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private Context context;
    private List<Pelicula> peliculas;

    public PeliculaAdapter(Context context, List<Pelicula> peliculas) {
        this.context = context;
        this.peliculas = peliculas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pelicula, parent, false); // Necesitarás crear este layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pelicula pelicula = peliculas.get(position);
        holder.bind(pelicula);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView; // Cambia el ID si es diferente en tu layout

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.nameTextView); // Asegúrate de que este ID exista en item_pelicula.xml
        }

        void bind(Pelicula pelicula) {
            titleTextView.setText(pelicula.getTitulo()); // Asumiendo que tu modelo Pelicula tiene un campo "titulo"
        }
    }
}