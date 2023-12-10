package com.example.proyectotatto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbTattos extends DBHelper {

    public DbTattos(@Nullable Context context) {
        super(context);
    }

    public ArrayList<Tatuaje> mostrarContactos() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Tatuaje> listaTatuajes = new ArrayList<>();
        Tatuaje tatuaje;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TATUAJES + " ORDER BY nombre ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                tatuaje = new Tatuaje();
                tatuaje.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                tatuaje.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                tatuaje.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow("categoria")));
                tatuaje.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("imagen_resource_id")));
                listaTatuajes.add(tatuaje);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listaTatuajes;
    }
}