package com.example.gestionpeliculasseries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.util.Log.d("LoginActivity", "Botón Registrar clickeado");
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkUser(username)) {
                    Toast.makeText(LoginActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    long userId = dbHelper.registerUser(username, password);
                    if (userId > 0) {
                        Toast.makeText(LoginActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                        // Opcional: Puedes iniciar sesión automáticamente después del registro
                        // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // startActivity(intent);
                        // finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}