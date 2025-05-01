package com.example.gestionpeliculasseries;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_peliculas) { // <--- Aquí debe coincidir
                loadFragment(new PeliculasFragment());
                return true;
            } else if (item.getItemId() == R.id.navigation_series) { // <--- Y aquí
                loadFragment(new SeriesFragment());
                return true;
            } else if (item.getItemId() == R.id.navigation_favoritos) { // <--- Y aquí
                loadFragment(new FavoritosFragment());
                return true;
            }
            return false;
        });

        // Cargar el fragmento inicial (opcional, por ejemplo, la pantalla de películas al iniciar)
        if (savedInstanceState == null) {
            loadFragment(new PeliculasFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }
}