package com.example.gestionpeliculasseries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class EditSerieActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextTemporadas;
    private Button buttonGuardar;
    private DatabaseHelper dbHelper;
    private long serieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_serie);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextTemporadas = findViewById(R.id.editTextTemporadas);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        dbHelper = new DatabaseHelper(this);

        serieId = getIntent().getLongExtra("serie_id", -1);

        if (serieId != -1) {
            com.example.gestionpeliculasseries.models.Serie serie = dbHelper.getSerie(serieId);
            if (serie != null) {
                editTextTitulo.setText(serie.getTitulo());
                editTextTemporadas.setText(String.valueOf(serie.getNumTemporadas()));
            }
        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString();
                String temporadasStr = editTextTemporadas.getText().toString();

                if (titulo.isEmpty() || temporadasStr.isEmpty()) {
                    Toast.makeText(EditSerieActivity.this, "Por favor, ingrese título y número de temporadas", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int temporadas = Integer.parseInt(temporadasStr);
                    int rowsAffected = dbHelper.updateSerie(serieId, titulo, temporadas);
                    if (rowsAffected > 0) {
                        Toast.makeText(EditSerieActivity.this, "Serie actualizada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditSerieActivity.this, "Error al actualizar la serie", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditSerieActivity.this, "Número de temporadas inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}