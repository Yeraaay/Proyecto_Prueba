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

