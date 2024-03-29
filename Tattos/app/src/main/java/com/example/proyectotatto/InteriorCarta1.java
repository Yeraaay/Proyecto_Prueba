package com.example.proyectotatto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class InteriorCarta1 extends Fragment {

    private OnCategoriaSelectedListener categoriaSelectedListener;
    private SQLiteDatabase db;
    private ArrayList<Tatuaje> listaTatto = new ArrayList<>();
    ImageView imagen;
    TextView descrip;
    TextView precio;

    AppCompatButton botonCarrito;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_interior_carta1, container, false);

        DBHelper dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        imagen = root.findViewById(R.id.imagen);
        descrip = root.findViewById(R.id.textViewDescripción);
        precio = root.findViewById(R.id.textViewPrecio);

        botonCarrito = root.findViewById(R.id.botonCarrito);
        botonCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el nombre del tatuaje seleccionado del Bundle
                String nombreTatuaje = getArguments().getString("nombreTatuaje");

                if (nombreTatuaje != null) {
                    // Obtener el ID del tatuaje desde la base de datos
                    long tatuajeId = dbHelper.obtenerIdDelTatuaje(nombreTatuaje);

                    if (tatuajeId != -1) {
                        // Obtener el ID del usuario actual
                        long usuarioId = dbHelper.obtenerIdDelUsuarioActual(getContext());

                        if (usuarioId != -1) {
                            // Agregar el tatuaje al carrito en la base de datos
                            long carritoId = dbHelper.agregarTatuajeAlCarrito(tatuajeId, getContext());

                            if (carritoId != -1) {
                                Toast.makeText(getContext(), "Tatuaje agregado al carrito", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Error al agregar al carrito", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Manejo de error, el usuario no está autenticado
                            Toast.makeText(getContext(), "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Manejo de error, no se encontró el ID del tatuaje
                        Toast.makeText(getContext(), "Error: Tatuaje no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Manejo de error, el nombre del tatuaje es nulo
                    Toast.makeText(getContext(), "Error: Nombre de tatuaje nulo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        // Obtener el nombre del tatuaje seleccionado del Bundle
        String nombreTatuaje = getArguments().getString("nombreTatuaje");

        // Consultar los datos del tatuaje según el nombre
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_TATUAJES + " WHERE nombre = ?", new String[]{nombreTatuaje});
        if (cursor.moveToNext()) {
            @SuppressLint("Range") String imagenResourceId = cursor.getString(cursor.getColumnIndex("imagen_resource_id"));
            @SuppressLint("Range") double precioTatuaje = cursor.getDouble(cursor.getColumnIndex("precio"));
            @SuppressLint("Range") String descripcionTatuaje = cursor.getString(cursor.getColumnIndex("descripcion"));

            Tatuaje tatuaje = new Tatuaje(nombreTatuaje, "", imagenResourceId, precioTatuaje, descripcionTatuaje);
            listaTatto.add(tatuaje);
            cargarDatosTatuaje(tatuaje);
        }

        cursor.close();

        return root;
    }

    // Método para establecer el listener desde la actividad
    public void setOnCategoriaSelectedListener(OnCategoriaSelectedListener listener) {
        this.categoriaSelectedListener = listener;
    }


    private void cargarDatosTatuaje(Tatuaje tatuaje) {
        String nombreTatuajeE = tatuaje.getNombre();
        if ("ONI".equals(nombreTatuajeE)) {
            // Cargar datos para el tatuaje ONI
            imagen.setImageResource(R.drawable.oni1);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("FLOR".equals(nombreTatuajeE)) {
            // Cargar datos para el tatuaje FLOR
            imagen.setImageResource(R.drawable.flor1);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("SPIDER".equals(nombreTatuajeE)) {
            // Cargar datos para el tatuaje SPIDER
            imagen.setImageResource(R.drawable.arana1);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("SNAKE".equals(nombreTatuajeE)) {
            // Cargar datos para el tatuaje SNAKE
            imagen.setImageResource(R.drawable.serpiente1);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("GRIFO".equals(nombreTatuajeE)) {
            imagen.setImageResource(R.drawable.grifo);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("MARIHUANA".equals(nombreTatuajeE)) {
            imagen.setImageResource(R.drawable.marihuana);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("GOKU".equals(nombreTatuajeE)) {
            imagen.setImageResource(R.drawable.goku);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        } else if ("NARUTO".equals(nombreTatuajeE)) {
            imagen.setImageResource(R.drawable.naruto);
            descrip.setText(tatuaje.getDescripcion());
            precio.setText(String.valueOf(tatuaje.getPrecio()));
        }
    }
}
