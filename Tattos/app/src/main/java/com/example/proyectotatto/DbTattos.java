package com.example.proyectotatto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbTattos extends DBHelper {

    Context context;

    public DbTattos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Tatuaje> mostrarContactos() {
        SQLiteDatabase db = getWritableDatabase(); // Utiliza el método de la clase padre

        ArrayList<Tatuaje> listaTatuajes = new ArrayList<>();
        Tatuaje contacto;
        Cursor cursorTatto;

        cursorTatto = db.rawQuery("SELECT * FROM " + TABLE_TATUAJES + " ORDER BY nombre ASC", null);

        if (cursorTatto.moveToFirst()) {
            do {
                contacto = new Tatuaje();
                contacto.setId(cursorTatto.getInt(0));
                contacto.setNombre(cursorTatto.getString(1));
                contacto.setImagen(cursorTatto.getString(2));
                contacto.setCategoria(cursorTatto.getString(3));
                listaTatuajes.add(contacto);
            } while (cursorTatto.moveToNext());
        }

        cursorTatto.close();

        return listaTatuajes;
    }
}

