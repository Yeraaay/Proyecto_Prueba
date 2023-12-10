package com.example.proyectotatto;

import static com.example.proyectotatto.DBHelper.TABLE_TATUAJES;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class fragmentObtenerIdModificar extends Fragment {

    Button btnComprobarID;
    EditText etComprobarID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_obtener_id_modificar, container, false);

        etComprobarID = view.findViewById(R.id.obtenerIDaModificar);
        btnComprobarID = view.findViewById(R.id.botonComprobarID);

        btnComprobarID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarExistenciaID();
            }
        });

        return view;
    }

    private void comprobarExistenciaID() {
        String idString = etComprobarID.getText().toString();

        if (!idString.isEmpty()) {
            // Obtener el ID introducido como entero
            int id = Integer.parseInt(idString);

            // Verificar la existencia del ID en la base de datos
            boolean existe = verificarIDExistente(id);

            if (existe) {
                // El ID existe en la base de datos, puedes proceder con la modificación
                Toast.makeText(getActivity(), "ID Correcto!", Toast.LENGTH_SHORT).show();
                // Al encontrar un ID válido, se cambia de fragment para completar el apartado de modificado de tatuajes
                Fragment fragmentModificar = new fragmentModificarAdmin();

                // Si necesitas pasar el ID al nuevo fragmento, puedes utilizar un Bundle
                Bundle args = new Bundle();
                args.putInt("id", id);
                fragmentModificar.setArguments(args);

                // Reemplazar el fragmento actual por el nuevo fragmento fragmentModificarAdmin
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameL, fragmentModificar)
                            .addToBackStack(null)
                            .commit();
                }
            } else {
                Toast.makeText(getActivity(), "ID no encontrado, vuelve a intentarlo!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verificarIDExistente(int id) {
        DBHelper dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TATUAJES + " WHERE id = ?", new String[]{String.valueOf(id)});

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }
}