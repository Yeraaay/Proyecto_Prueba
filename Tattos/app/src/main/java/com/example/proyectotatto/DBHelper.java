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

    public static final String TABLE_USUARIOS="Usuarios";
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
                "imagen_resource_id String," +
                "precio DOUBLE," +  // Asumiendo que el precio es un valor numérico (REAL)
                "descripcion TEXT," +  // Añadiendo la columna para la descripción
                "categoria TEXT)");


        //Criatura
        ContentValues valuesTatuaje1 = new ContentValues();
        valuesTatuaje1.put("nombre", "ONI");
        valuesTatuaje1.put("imagen_resource_id", R.drawable.oni1);
        valuesTatuaje1.put("precio",20);
        valuesTatuaje1.put("descripcion","Demonio de la cultura japonesa");
        valuesTatuaje1.put("categoria", "criaturas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje1);

        //Planta
        ContentValues valuesTatuaje2 = new ContentValues();
        valuesTatuaje2.put("nombre", "FLOR");
        valuesTatuaje2.put("imagen_resource_id", R.drawable.flor1);
        valuesTatuaje2.put("precio", 50);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje2.put("descripcion", "Descripción de la flor");  // Ajusta la descripción según sea necesario
        valuesTatuaje2.put("categoria", "plantas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje2);

        //Animal
        ContentValues valuesTatuaje3 = new ContentValues();
        valuesTatuaje3.put("nombre", "SPIDER");
        valuesTatuaje3.put("imagen_resource_id", R.drawable.arana1);
        valuesTatuaje3.put("precio", 25);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje3.put("descripcion", "Descripción de la araña");  // Ajusta la descripción según sea necesario
        valuesTatuaje3.put("categoria", "animales");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje3);

        //Animal
        ContentValues valuesTatuaje4 = new ContentValues();
        valuesTatuaje4.put("nombre", "SNAKE");
        valuesTatuaje4.put("imagen_resource_id", R.drawable.serpiente1);
        valuesTatuaje4.put("precio", 35);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje4.put("descripcion", "Descripción de la serpiente");  // Ajusta la descripción según sea necesario
        valuesTatuaje4.put("categoria", "animales");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje4);

        //Criatura
        ContentValues valuesTatuaje5 = new ContentValues();
        valuesTatuaje5.put("nombre", "GRIFO");
        valuesTatuaje5.put("imagen_resource_id", R.drawable.grifo);
        valuesTatuaje5.put("precio", 45);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje5.put("descripcion", "Descripción del Grifo");  // Ajusta la descripción según sea necesario
        valuesTatuaje5.put("categoria", "criaturas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje5);

        //Planta
        ContentValues valuesTatuaje6 = new ContentValues();
        valuesTatuaje6.put("nombre", "MARIHUANA");
        valuesTatuaje6.put("imagen_resource_id", R.drawable.marihuana);
        valuesTatuaje6.put("precio", 25);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje6.put("descripcion", "Descripción de la marihuana");  // Ajusta la descripción según sea necesario
        valuesTatuaje6.put("categoria", "plantas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje6);

        //Anime
        ContentValues valuesTatuaje7 = new ContentValues();
        valuesTatuaje7.put("nombre", "GOKU");
        valuesTatuaje7.put("imagen_resource_id", R.drawable.goku);
        valuesTatuaje7.put("precio", 50);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje7.put("descripcion", "Descripción de Goku");  // Ajusta la descripción según sea necesario
        valuesTatuaje7.put("categoria", "anime");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje7);

        //Anime
        ContentValues valuesTatuaje8 = new ContentValues();
        valuesTatuaje8.put("nombre", "NARUTO");
        valuesTatuaje8.put("imagen_resource_id", R.drawable.naruto);
        valuesTatuaje8.put("precio", 50);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje8.put("descripcion", "Descripción de Naruto");  // Ajusta la descripción según sea necesario
        valuesTatuaje8.put("categoria", "anime");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje8);

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
            db.execSQL("ALTER TABLE " + TABLE_REGISTRADOS + " ADD COLUMN nuevaColumna TEXT;");
        }

        // Puedes agregar más bloques if para otras versiones, si es necesario

        // Siempre asegúrate de llamar al onCreate al final para recrear la tabla si es necesario
        onCreate(db);
    }}
