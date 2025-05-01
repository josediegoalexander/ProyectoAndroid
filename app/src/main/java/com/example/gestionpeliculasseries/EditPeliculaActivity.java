package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionpeliculasseries.models.Pelicula;

public class EditPeliculaActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDirector;
    private EditText editTextAnio;
    private Button buttonGuardar;
    private DatabaseHelper dbHelper;
    private long peliculaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pelicula);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextAnio = findViewById(R.id.editTextAnio);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        dbHelper = new DatabaseHelper(this);

        peliculaId = getIntent().getLongExtra("pelicula_id", -1);

        if (peliculaId != -1) {
            Pelicula pelicula = dbHelper.getPelicula(peliculaId);
            if (pelicula != null) {
                editTextTitulo.setText(pelicula.getTitulo());
                editTextDirector.setText(pelicula.getDirector());
                editTextAnio.setText(String.valueOf(pelicula.getAnio()));
            }
        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString();
                String director = editTextDirector.getText().toString();
                String anioStr = editTextAnio.getText().toString();

                if (titulo.isEmpty() || anioStr.isEmpty()) {
                    Toast.makeText(EditPeliculaActivity.this, "Por favor, ingrese título y año", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int anio = Integer.parseInt(anioStr);
                    int rowsAffected = dbHelper.updatePelicula(peliculaId, titulo, director, anio);
                    if (rowsAffected > 0) {
                        Toast.makeText(EditPeliculaActivity.this, "Película actualizada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditPeliculaActivity.this, "Error al actualizar la película", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditPeliculaActivity.this, "Año inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}