package com.example.gestionpeliculasseries;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainer = findViewById(R.id.fragmentContainer);

        // Cargar el fragmento de películas por defecto al iniciar
        loadFragment(new PeliculasFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.navigation_peliculas) {
                    fragment = new PeliculasFragment();
                } else if (item.getItemId() == R.id.navigation_series) {
                    fragment = new com.example.gestionpeliculasseries.SeriesFragment();
                } else if (item.getItemId() == R.id.navigation_favoritos) {
                    fragment = new FavoritosFragment();
                } else if (item.getItemId() == R.id.navigation_logout) {
                    Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true; // Indica que el item fue manejado
                }

                if (fragment != null) {
                    loadFragment(fragment);
                    return true; // Indica que el item fue manejado
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }
}