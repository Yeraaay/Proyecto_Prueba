package com.example.proyectotatto;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class fragmentEliminarAdmin extends Fragment {

    Button btnBorrarTatto;
    EditText etborrarTatto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eliminar_admin, container, false);

        btnBorrarTatto = view.findViewById(R.id.botonEliminacionTatuaje);
        etborrarTatto = view.findViewById(R.id.botonEliminarTatuaje);

        btnBorrarTatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarTatuajePorNombre();
            }
        });

        return view;
    }

    private void eliminarTatuajePorNombre() {
        String nombreTatuaje = etborrarTatto.getText().toString();

        // Lógica para eliminar el tatuaje de la base de datos por su nombre
        boolean eliminado = eliminarTatuajeDeBaseDeDatos(nombreTatuaje);

        if (eliminado) {
            Toast.makeText(getActivity(), "Tatuaje eliminado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "No se pudo eliminar el tatuaje", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean eliminarTatuajeDeBaseDeDatos(String nombreTatuaje) {
        DBHelper dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Ejemplo de eliminación del tatuaje por su nombre (ajusta las columnas y la tabla según tu esquema real)
        int filasEliminadas = db.delete(DBHelper.TABLE_TATUAJES, "nombre = ?", new String[]{nombreTatuaje});

        db.close();

        // Si se eliminó al menos una fila, se considera como eliminación exitosa
        return filasEliminadas > 0;
    }

}