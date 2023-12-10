package com.example.proyectotatto;


import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="ejemplo.db";
    private static final String TABLE_ADMIN="Admin";

    private static final String TABLE_USUARIOS="Usuarios";
    private static final String TABLE_REGISTRADOS = "Registrados";
    public static final String TABLE_TATUAJES = "Tatuajes";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USUARIOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "Tipo TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ADMIN + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "usuario_id INTEGER," +  // Nueva columna para la clave foránea
                "FOREIGN KEY (usuario_id) REFERENCES " + TABLE_USUARIOS + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_REGISTRADOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "apellidos TEXT," +
                "dni TEXT," +
                "telefonoMovil TEXT," +
                "password TEXT," +
                "confirm_password TEXT," +
                "usuario_id INTEGER," +  // Nueva columna para la clave foránea
                "FOREIGN KEY (usuario_id) REFERENCES " + TABLE_USUARIOS + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_TATUAJES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "imagen_resource_id BLOB," +  // Puedes almacenar la referencia de la imagen
                "categoria TEXT)");


        ContentValues valuesTatuaje1 = new ContentValues();
        valuesTatuaje1.put("nombre", "ONI");
        valuesTatuaje1.put("imagen_resource_id", R.drawable.oni1);
        valuesTatuaje1.put("categoria", "criaturas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje1);


        ContentValues valuesTatuaje2 = new ContentValues();
        valuesTatuaje2.put("nombre", "FLOR");
        valuesTatuaje2.put("imagen_resource_id", R.drawable.flor1);
        valuesTatuaje2.put("categoria", "plantas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje2);

        ContentValues valuesTatuaje3 = new ContentValues();
        valuesTatuaje3.put("nombre", "SPIDER");
        valuesTatuaje3.put("imagen_resource_id", R.drawable.arana1);
        valuesTatuaje3.put("categoria", "animales");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje3);

        ContentValues valuesTatuaje4 = new ContentValues();
        valuesTatuaje4.put("nombre", "SNAKE");
        valuesTatuaje4.put("imagen_resource_id", R.drawable.serpiente1);
        valuesTatuaje4.put("categoria", "animales");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje4);

    }


    public void createAdminUser(SQLiteDatabase db) {
        // Verificar si ya existe un usuario administrador
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE Tipo = 'Admin'", null);
        if (cursor.getCount() == 0) {
            // No hay usuario administrador, se puede crear
            ContentValues adminValues = new ContentValues();
            adminValues.put("name", "usuario");
            adminValues.put("password", "usuario");
            adminValues.put("Tipo", "Admin");

            long adminId = db.insert(TABLE_USUARIOS, null, adminValues);

            if (adminId != -1) {
                ContentValues adminTableValues = new ContentValues();
                adminTableValues.put("id", adminId);
                adminTableValues.put("name", "usuario");
                adminTableValues.put("password", "usuario");
                adminTableValues.put("usuario_id", adminId);

                db.insert(TABLE_ADMIN, null, adminTableValues);
            }
        }

        cursor.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Añade las condiciones para actualizar según la versión de la base de datos
        if (oldVersion < 2) {
            // Ejecuta las consultas de actualización necesarias
            db.execSQL("ALTER TABLE " + TABLE_REGISTRADOS + " ADD COLUMN nuevoCampo TEXT;");
        }

        // Puedes agregar más bloques if para otras versiones, si es necesario

        // Siempre asegúrate de llamar al onCreate al final para recrear la tabla si es necesario
        onCreate(db);
    }}