package com.example.proyectotatto;

import static com.example.proyectotatto.DBHelper.TABLE_TATUAJES;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

public class fragmentAnadirAdmin extends Fragment {

    private static final int REQUEST_IMAGE_PICK = 1;

    private ImageView imageView;
    private Button btnSeleccionarFoto, btnAniadirTatuaje;
    private EditText etnombre, etcategoria, etprecio, etdescripcion;
    private Uri imagenUri; // Agregar variable para almacenar la URI de la imagen seleccionada

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anadir_admin, container, false);

        // Inicialización de vistas
        btnSeleccionarFoto = view.findViewById(R.id.botonSelectFoto);
        btnAniadirTatuaje = view.findViewById(R.id.botonAniadirTatuaje);
        imageView = view.findViewById(R.id.imagenTatto);
        etnombre = view.findViewById(R.id.nombretatto);
        etcategoria = view.findViewById(R.id.categoriatatto);
        etprecio = view.findViewById(R.id.preciotatto);
        etdescripcion = view.findViewById(R.id.descripciontatto);

        // Configuración de clics
        btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFotoDesdeGaleria();
            }
        });

        btnAniadirTatuaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirTatuaje();
            }
        });

        return view;
    }

    public void anadirTatuaje() {
        String categoria = etcategoria.getText().toString();
        String descripcion = etdescripcion.getText().toString();
        String nombre = etnombre.getText().toString();
        double precio = Double.parseDouble(etprecio.getText().toString());
        String imagenConvertida = obtenerRutaDeArchivo(imagenUri); // Obtener la ruta de la imagen

        // Luego, llamar al método para insertar en la base de datos
        insertarDatosEnBaseDeDatos(nombre, imagenConvertida, precio, descripcion, categoria);
    }

    private String obtenerRutaDeArchivo(Uri imagenUri) {
        String rutaDeArchivo = "";
        if (imagenUri != null) {
            rutaDeArchivo = imagenUri.toString(); // Obtener la ruta de la imagen desde la URI
        }
        return rutaDeArchivo;
    }

    private void insertarDatosEnBaseDeDatos(String nombre, String imagen, double precio, String descripcion, String categoria) {
        DBHelper dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("imagen_resource_id", imagen); // Almacena la ruta de la imagen en la base de datos
        values.put("precio", precio);
        values.put("descripcion", descripcion);
        values.put("categoria", categoria);

        long result = db.insert(TABLE_TATUAJES, null, values);

        if (result != -1) {
            Toast.makeText(getActivity(), "Tatuaje insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Error al insertar los datos", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void seleccionarFotoDesdeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            imagenUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imagenUri);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}