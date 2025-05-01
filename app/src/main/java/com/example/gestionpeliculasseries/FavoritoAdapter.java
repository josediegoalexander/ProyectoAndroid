package com.example.gestionpeliculasseries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpeliculasseries.models.Favorito;

import java.util.List;


public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder> {

    private Context context;
    private List<Favorito> favoritos;
    private DatabaseHelper dbHelper;

    public FavoritoAdapter(Context context, List<Favorito> favoritos) {
        this.context = context;
        this.favoritos = favoritos;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorito, parent, false);
        return new FavoritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        Favorito favorito = favoritos.get(position);
        String titulo = "";

        if (favorito.getTipo().equals("pelicula")) {
            com.example.gestionpeliculasseries.models.Pelicula pelicula = dbHelper.getPelicula(favorito.getItemId());
            if (pelicula != null) {
                titulo = pelicula.getTitulo();
            }
        } else if (favorito.getTipo().equals("serie")) {
            com.example.gestionpeliculasseries.models.Serie serie = dbHelper.getSerie(favorito.getItemId());
            if (serie != null) {
                titulo = serie.getTitulo();
            }
        }

        holder.textViewTitulo.setText(titulo);
        String tipoTexto = favorito.getTipo().equals("pelicula") ? "Película" : "Serie";
        holder.textViewTipo.setText(tipoTexto);

        holder.buttonDelete.setOnClickListener(v -> {
            long favoritoId = favorito.getId();
            dbHelper.deleteFavorito(favoritoId);
            // Importante: Eliminar el elemento de la lista ANTES de notificar el adaptador
            int index = favoritos.indexOf(favorito);
            if (index != -1) {
                favoritos.remove(index);
                notifyItemRemoved(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    public static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitulo;
        public TextView textViewTipo;
        public Button buttonDelete;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewTipo = itemView.findViewById(R.id.textViewTipo);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}