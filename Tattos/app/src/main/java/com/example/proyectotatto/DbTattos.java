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

        if (cursor != null && cursor.moveToFirst()) {
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
}
