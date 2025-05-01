package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionpeliculasseries.models.Serie;

public class SerieDetailsActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewTemporadas;
    private Button buttonAddToFavorites;
    private DatabaseHelper dbHelper;
    private long serieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_details);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewTemporadas = findViewById(R.id.textViewTemporadas);
        buttonAddToFavorites = findViewById(R.id.buttonAddToFavorites);
        dbHelper = new DatabaseHelper(this);

        serieId = getIntent().getLongExtra("serie_id", -1);

        if (serieId != -1) {
            Serie serie = dbHelper.getSerie(serieId);
            if (serie != null) {
                textViewTitulo.setText("Título: " + serie.getTitulo());
                textViewTemporadas.setText("Temporadas: " + serie.getNumTemporadas());

                buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: Obtener el ID del usuario actual
                        long userId = 1; // Simulación

                        long id = dbHelper.addFavorito(userId, serieId, "serie");
                        if (id > 0) {
                            Toast.makeText(SerieDetailsActivity.this, "Añadido a Favoritos", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SerieDetailsActivity.this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}