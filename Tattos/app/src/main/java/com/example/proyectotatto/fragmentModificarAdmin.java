package com.example.proyectotatto;

import static com.example.proyectotatto.DBHelper.TABLE_TATUAJES;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.checkerframework.common.subtyping.qual.Bottom;

public class fragmentModificarAdmin extends Fragment {
    private static final int REQUEST_IMAGE_PICK = 1;
    EditText etNombreModificar, etPrecioModificar, etDescripcionModificar, etCategoriaModificar;
    ImageView imagenModificar;
    Button btnModificarTatuaje, btnSeleccionarImagen;
    String rutaImagen;
    int tatuajeID; //Variable donde se almacenará el ID del tatuaje

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_admin, container, false);

        etNombreModificar = view.findViewById(R.id.nombreModificar);
        etPrecioModificar = view.findViewById(R.id.precioModificar);
        etDescripcionModificar = view.findViewById(R.id.descripcionModificar);
        etCategoriaModificar = view.findViewById(R.id.categoriaModificar);
        btnModificarTatuaje = view.findViewById(R.id.botonModificarTatuaje);

        imagenModificar = view.findViewById(R.id.imagenModificar);
        btnSeleccionarImagen = view.findViewById(R.id.botonSeleccionarImagen);

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarNuevaImagen();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) tatuajeID = bundle.getInt("id");

        btnModificarTatuaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarTatuaje();
            }
        });

        return view;
    }

    private void seleccionarNuevaImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Obtener la ruta de la imagen a partir de la URI
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = requireActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                rutaImagen = cursor.getString(columnIndex);
                cursor.close();

                // Actualizar la imagen en el ImageView
                imagenModificar.setImageURI(selectedImageUri);
            }
        }
    }

    private void modificarTatuaje() {
        String nuevoNombre = etNombreModificar.getText().toString();
        String nuevaCategoria = etCategoriaModificar.getText().toString();
        String nuevaDescripcion = etDescripcionModificar.getText().toString();
        double nuevoPrecio = Double.parseDouble(etPrecioModificar.getText().toString());

        // Actualizar los datos del tatuaje en la base de datos
        actualizarTatuajeEnBaseDeDatos(nuevoNombre, nuevaCategoria, nuevaDescripcion, nuevoPrecio, rutaImagen, tatuajeID);
    }

    private void actualizarTatuajeEnBaseDeDatos(String nombre, String categoria, String descripcion, double precio, String rutaImagen, int tatuajeID) {
        DBHelper dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("categoria", categoria);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("imagen_resource_id", rutaImagen);

        // Actualizar el tatuaje con el ID correspondiente
        int rowsAffected = db.update(TABLE_TATUAJES, values, "id=?", new String[]{String.valueOf(tatuajeID)});
        db.close();

        if (rowsAffected > 0) {
            Toast.makeText(getActivity(), "Tatuaje actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Error al actualizar el tatuaje", Toast.LENGTH_SHORT).show();
        }
    }
}