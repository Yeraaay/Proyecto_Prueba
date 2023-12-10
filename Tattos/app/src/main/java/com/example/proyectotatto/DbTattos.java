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
        Cursor cursorTatto;

        cursorTatto = db.rawQuery("SELECT * FROM " + TABLE_TATUAJES + " ORDER BY nombre ASC", null);

        if (cursorTatto.moveToFirst()) {
            do {
                tatuaje = new Tatuaje();
                tatuaje.setId(cursorTatto.getInt(0));
                tatuaje.setNombre(cursorTatto.getString(1));
                tatuaje.setImagen(cursorTatto.getString(2));
                tatuaje.setPrecio(cursorTatto.getInt(3));
                tatuaje.setDescripcion(cursorTatto.getString(4));
                tatuaje.setCategoria(cursorTatto.getString(5));
                listaTatuajes.add(tatuaje);
            } while (cursorTatto.moveToNext());
        }
        return listaTatuajes;
    }

    public ArrayList<Tatuaje> obtenerTatuajesPorCategoria(String categoria) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Tatuaje> listaTatuajes = new ArrayList<>();
        Tatuaje tatuaje;
        Cursor cursorTatto;

        // Utiliza una cláusula WHERE para filtrar por categoría
        String query = "SELECT * FROM " + TABLE_TATUAJES + " WHERE categoria = ? ORDER BY nombre ASC";
        String[] selectionArgs = {categoria};

        cursorTatto = db.rawQuery(query, selectionArgs);

        if (cursorTatto.moveToFirst()) {
            do {
                tatuaje = new Tatuaje();
                tatuaje.setId(cursorTatto.getInt(0));
                tatuaje.setNombre(cursorTatto.getString(1));
                tatuaje.setImagen(cursorTatto.getString(2));
                tatuaje.setPrecio(cursorTatto.getInt(3));
                tatuaje.setDescripcion(cursorTatto.getString(4));
                tatuaje.setCategoria(cursorTatto.getString(5));
                listaTatuajes.add(tatuaje);
            } while (cursorTatto.moveToNext());
        }
        cursorTatto.close();
        return listaTatuajes;
    }

}