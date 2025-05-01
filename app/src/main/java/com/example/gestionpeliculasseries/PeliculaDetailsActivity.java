package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionpeliculasseries.models.Pelicula;

public class PeliculaDetailsActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewDirector;
    private TextView textViewAnio;
    private Button buttonAddToFavorites;
    private DatabaseHelper dbHelper;
    private long peliculaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula_details);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewDirector = findViewById(R.id.textViewDirector);
        textViewAnio = findViewById(R.id.textViewAnio);
        buttonAddToFavorites = findViewById(R.id.buttonAddToFavorites);
        dbHelper = new DatabaseHelper(this);

        peliculaId = getIntent().getLongExtra("pelicula_id", -1);

        if (peliculaId != -1) {
            Pelicula pelicula = dbHelper.getPelicula(peliculaId);
            if (pelicula != null) {
                textViewTitulo.setText("Título: " + pelicula.getTitulo());
                textViewDirector.setText("Director: " + pelicula.getDirector());
                textViewAnio.setText("Año: " + pelicula.getAnio());

                buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // todo: Obtener el ID del usuario actual (esto requerira gestion de sesion)
                        long userId = 1; // Simulación de un usuario logueado
                        long id = dbHelper.addFavorito(userId, peliculaId, "pelicula");
                        if (id > 0) {
                            Toast.makeText(PeliculaDetailsActivity.this, "Añadido a Favoritos", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PeliculaDetailsActivity.this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                    });
                } else {
                    Toast.makeText(this, "Error al cargar la película", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }