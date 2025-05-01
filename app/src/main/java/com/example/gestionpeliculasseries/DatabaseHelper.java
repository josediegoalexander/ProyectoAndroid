package com.example.gestionpeliculasseries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestionpeliculasseries.models.Favorito;
import com.example.gestionpeliculasseries.models.Pelicula;
import com.example.gestionpeliculasseries.models.Serie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GestionPeliculasSeries.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de USERS
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL" +
                    ");";

    // Tabla de Películas
    private static final String TABLE_PELICULAS = "peliculas";
    private static final String COLUMN_PELICULA_ID = "id";
    private static final String COLUMN_PELICULA_TITULO = "titulo";
    private static final String COLUMN_PELICULA_DIRECTOR = "director";
    private static final String COLUMN_PELICULA_ANIO = "anio"; // ¡Define esta constante!
    // ... otras columnas de películas ...

    // Tabla de Series
    private static final String TABLE_SERIES = "series";
    private static final String COLUMN_SERIE_ID = "id";
    private static final String COLUMN_SERIE_TITULO = "titulo";
    private static final String COLUMN_SERIE_TEMPORADAS = "temporadas";
    // ... otras columnas de series ...

    // Tabla de Favoritos
    private static final String TABLE_FAVORITOS = "favoritos";
    private static final String COLUMN_FAVORITO_ID = "id";
    private static final String COLUMN_FAVORITO_USER_ID = "user_id";
    private static final String COLUMN_FAVORITO_ITEM_ID = "item_id";
    private static final String COLUMN_FAVORITO_TIPO = "tipo"; // "pelicula" o "serie"

    // Sentencia SQL para crear la tabla de Películas
    private static final String CREATE_TABLE_PELICULAS =
            "CREATE TABLE " + TABLE_PELICULAS + "("
                    + COLUMN_PELICULA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PELICULA_TITULO + " TEXT,"
                    + COLUMN_PELICULA_DIRECTOR + " TEXT"
                    // ... otras columnas ...
                    + ")";

    // Sentencia SQL para crear la tabla de Series
    private static final String CREATE_TABLE_SERIES =
            "CREATE TABLE " + TABLE_SERIES + "("
                    + COLUMN_SERIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SERIE_TITULO + " TEXT,"
                    + COLUMN_SERIE_TEMPORADAS + " INTEGER"
                    // ... otras columnas ...
                    + ")";

    // Sentencia SQL para crear la tabla de Favoritos
    private static final String CREATE_TABLE_FAVORITOS =
            "CREATE TABLE " + TABLE_FAVORITOS + "("
                    + COLUMN_FAVORITO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FAVORITO_USER_ID + " INTEGER,"
                    + COLUMN_FAVORITO_ITEM_ID + " INTEGER,"
                    + COLUMN_FAVORITO_TIPO + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PELICULAS);
        db.execSQL(CREATE_TABLE_SERIES);
        db.execSQL(CREATE_TABLE_FAVORITOS);
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si necesitas actualizar la base de datos, aquí puedes escribir la lógica
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELICULAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITOS);
        onCreate(db);
    }

    // Métodos para la tabla de Películas
    public long addPelicula(Pelicula pelicula) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PELICULA_TITULO, pelicula.getTitulo());
        values.put(COLUMN_PELICULA_DIRECTOR, pelicula.getDirector());
        values.put(COLUMN_PELICULA_ANIO, pelicula.getAnio()); // Asegúrate de tener esta línea si agregaste el año
        // ... otras columnas ...
        long id = db.insert(TABLE_PELICULAS, null, values);
        db.close();
        return id;
    }

    public List<Pelicula> getAllPeliculas() {
        List<Pelicula> peliculas = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PELICULAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_ID)));
                pelicula.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_TITULO)));
                pelicula.setDirector(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_DIRECTOR)));
                // ... otros campos ...
                peliculas.add(pelicula);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return peliculas;
    }

    public Pelicula getPelicula(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PELICULAS,
                new String[]{COLUMN_PELICULA_ID, COLUMN_PELICULA_TITULO, COLUMN_PELICULA_DIRECTOR, COLUMN_PELICULA_ANIO},
                COLUMN_PELICULA_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Pelicula pelicula = new Pelicula();
        if (cursor != null && cursor.getCount() > 0) {
            pelicula.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_ID)));
            pelicula.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_TITULO)));
            pelicula.setDirector(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_DIRECTOR)));
            pelicula.setAnio(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PELICULA_ANIO)));
            cursor.close();
        } else {
            pelicula = null; // O maneja el caso de que no se encuentre la película
        }
        db.close();
        return pelicula;
    }

    // Métodos para la tabla de Series
    public long addSerie(Serie serie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERIE_TITULO, serie.getTitulo());
        values.put(COLUMN_SERIE_TEMPORADAS, serie.getTemporadas());
        // ... otros valores ...
        long id = db.insert(TABLE_SERIES, null, values);
        db.close();
        return id;
    }

    public List<Serie> getAllSeries() {
        List<Serie> series = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SERIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Serie serie = new Serie();
                serie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIE_ID)));
                serie.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERIE_TITULO)));
                serie.setTemporadas(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIE_TEMPORADAS)));
                // ... otros campos ...
                series.add(serie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return series;
    }

    public Serie getSerie(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SERIES,
                new String[]{COLUMN_SERIE_ID, COLUMN_SERIE_TITULO, COLUMN_SERIE_TEMPORADAS},
                COLUMN_SERIE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Serie serie = new Serie();
        if (cursor != null && cursor.getCount() > 0) {
            serie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIE_ID)));
            serie.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERIE_TITULO)));
            serie.setTemporadas(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIE_TEMPORADAS)));
            cursor.close();
        } else {
            serie = null; // O maneja el caso de que no se encuentre la serie
        }
        db.close();
        return serie;
    }

    // Métodos para la tabla de Favoritos
    public long addFavorito(long userId, long itemId, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAVORITO_USER_ID, userId);
        values.put(COLUMN_FAVORITO_ITEM_ID, itemId);
        values.put(COLUMN_FAVORITO_TIPO, tipo);
        long id = db.insert(TABLE_FAVORITOS, null, values);
        db.close();
        return id;
    }

    public List<Favorito> getAllFavoritosDeUsuario(long userId) {
        List<Favorito> favoritos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITOS + " WHERE " + COLUMN_FAVORITO_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Favorito favorito = new Favorito();
                favorito.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITO_ID)));
                favorito.setUserId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FAVORITO_USER_ID)));
                favorito.setItemId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITO_ITEM_ID)));
                favorito.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FAVORITO_TIPO)));
                favoritos.add(favorito);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoritos;
    }

    // Metodo para actualizar la información de una película
    public int updatePelicula(long id, String titulo, String director, int anio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PELICULA_TITULO, titulo);
        values.put(COLUMN_PELICULA_DIRECTOR, director);
        values.put(COLUMN_PELICULA_ANIO, anio);

        // La cláusula WHERE para identificar la fila a actualizar
        String whereClause = COLUMN_PELICULA_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        int rowsAffected = db.update(TABLE_PELICULAS, values, whereClause, whereArgs);
        db.close();
        return rowsAffected;
    }

    public int updateSerie(long id, String titulo, int temporadas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERIE_TITULO, titulo);
        values.put(COLUMN_SERIE_TEMPORADAS, temporadas);

        String whereClause = COLUMN_SERIE_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        int rowsAffected = db.update(TABLE_SERIES, values, whereClause, whereArgs);
        db.close();
        return rowsAffected;
    }

    // Metodo para eliminar un favorito por su ID (¡Corregido!)
    public void deleteFavorito(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_FAVORITO_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.delete(TABLE_FAVORITOS, whereClause, whereArgs);
        db.close();
    }

    // Metodo para verificar si un usuario existe por su nombre de usuario
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users", // Reemplaza "users" con el nombre de tu tabla de usuarios
                new String[]{"username"},
                "username=?",
                new String[]{username},
                null, null, null
        );
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }


    // Metodo para verificar si un usuario existe y la contraseña es correcta
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users", // Reemplaza "users" con el nombre de tu tabla de usuarios
                new String[]{"username", "password"}, // Selecciona tanto el nombre de usuario como la contraseña
                "username=?", // Busca por nombre de usuario
                new String[]{username},
                null, null, null
        );

        boolean isValid = false;
        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password")); // Reemplaza "password" con el nombre de tu columna de contraseña
            // Aquí deberías comparar la contraseña proporcionada con la contraseña almacenada (¡usando hashing en una app real!)
            if (password.equals(storedPassword)) {
                isValid = true;
            }
            cursor.close();
        }
        db.close();
        return isValid;
    }

    // Metodo para registrar un nuevo usuario
    public long registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username); // Reemplaza "username" con el nombre de tu columna de usuario
        values.put("password", password); // Reemplaza "password" con el nombre de tu columna de contraseña

        // Insertar la nueva fila
        long newRowId = db.insert("users", null, values); // Reemplaza "users" con el nombre de tu tabla de usuarios
        db.close();
        return newRowId; // Devuelve el ID de la nueva fila insertada
    }

}