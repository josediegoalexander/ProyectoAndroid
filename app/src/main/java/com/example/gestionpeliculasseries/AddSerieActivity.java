package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddSerieActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextTemporadas;
    private Button         buttonGuardar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextTemporadas = findViewById(R.id.editTextTemporadas);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        dbHelper = new DatabaseHelper(this);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString();
                String temporadasStr = editTextTemporadas.getText().toString();

                if (titulo.isEmpty() || temporadasStr.isEmpty()) {
                    Toast.makeText(AddSerieActivity.this, "Por favor, ingrese título y número de temporadas", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int temporadas = Integer.parseInt(temporadasStr);
                    long id = dbHelper.addSerie(titulo, temporadas);
                    if (id > 0) {
                        Toast.makeText(AddSerieActivity.this, "Serie guardada con ID: " + id, Toast.LENGTH_SHORT).show();
                        finish(); // Volver al fragmento de series
                    } else {
                        Toast.makeText(AddSerieActivity.this, "Error al guardar la serie", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AddSerieActivity.this, "Número de temporadas inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class DatabaseHelper {
        public DatabaseHelper(AddSerieActivity addSerieActivity) {
        }

        public long addSerie(String titulo, int temporadas) {
            return 0;
        }
    }
}