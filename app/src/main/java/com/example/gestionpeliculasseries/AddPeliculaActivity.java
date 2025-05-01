package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionpeliculasseries.models.Pelicula;

public class AddPeliculaActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDirector;
    private EditText editTextAnio;
    private Button  buttonGuardar;
    private com.example.gestionpeliculasseries.DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pelicula);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextAnio = findViewById(R.id.editTextAnio);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        dbHelper = new com.example.gestionpeliculasseries.DatabaseHelper(this);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString();
                String director = editTextDirector.getText().toString();
                String anioStr = editTextAnio.getText().toString();

                if (titulo.isEmpty() || anioStr.isEmpty()) {
                    Toast.makeText(AddPeliculaActivity.this, "Por favor, ingrese título y año", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int anio = Integer.parseInt(anioStr);
                    Pelicula nuevaPelicula = new Pelicula();
                    nuevaPelicula.setTitulo(titulo);
                    nuevaPelicula.setDirector(director);
                    nuevaPelicula.setAnio(anio); // Asegúrate de tener este setter en tu modelo Pelicula

                    long id = dbHelper.addPelicula(nuevaPelicula);
                    if (id > 0) {
                        Toast.makeText(AddPeliculaActivity.this, "Película guardada con ID: " + id, Toast.LENGTH_SHORT).show();
                        finish(); // Volver al fragmento de películas
                    } else {
                        Toast.makeText(AddPeliculaActivity.this, "Error al guardar la película", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AddPeliculaActivity.this, "Año inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}