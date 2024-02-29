package com.example.proyectotatto;
import static com.example.proyectotatto.DBHelper.TABLE_USUARIOS;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.StringReader;

public class EditarPerfil extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private Button buttonGuardar;

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        // Inicializar la base de datos
        DBHelper dbHelper = new DBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        // Cargar y mostrar los datos actuales del usuario
        cargarDatosUsuario();

        // Configurar el botón de guardar
        buttonGuardar.setOnClickListener(view -> guardarDatosUsuario());
    }

    private void cargarDatosUsuario() {
        // Obtener la ID del usuario actual
        long idUsuario = obtenerIdDelUsuarioActual(this);
        Log.e("dad",String.valueOf(idUsuario));

        // Verificar si la ID del usuario actual es válida
        if (idUsuario != -1) {
            // Realizar una consulta a la base de datos para obtener los datos del usuario actual
            Cursor cursor = mDatabase.rawQuery("SELECT name, password FROM Usuarios WHERE id = ?", new String[]{String.valueOf(idUsuario)});

            // Verificar si se obtuvo algún resultado
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener el nombre y la contraseña actual del usuario desde el cursor
                @SuppressLint("Range") String nombreActual = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String contrasenaActual = cursor.getString(cursor.getColumnIndex("password"));

                // Mostrar el nombre y la contraseña actual en los campos de entrada
                editTextName.setText(nombreActual);
                editTextPassword.setText(contrasenaActual);

                // Cerrar el cursor
                cursor.close();
            } else {
                // No se encontraron datos en la base de datos para el usuario actual
                Toast.makeText(this, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show();
            }
        } else {
            // No se pudo obtener la ID del usuario actual
            Toast.makeText(this, "Error: No se pudo obtener el ID del usuario actual", Toast.LENGTH_SHORT).show();
        }
    }




    private void guardarDatosUsuario() {
        // Obtener el nuevo nombre y contraseña ingresados por el usuario
        String nuevoNombre = editTextName.getText().toString();
        String nuevaContrasena = editTextPassword.getText().toString();

        // Obtener el id del usuario logeado
        long idUsuario = obtenerIdDelUsuarioActual(this);

        if (idUsuario != -1) {
            // Modificar los datos del usuario en la base de datos
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", nuevoNombre);
            contentValues.put("password", nuevaContrasena);

            // Ejecutar una consulta de actualización en la base de datos, filtrando por el id del usuario
            int filasActualizadas = mDatabase.update(
                    "Usuarios",
                    contentValues,
                    "id = ?",
                    new String[]{String.valueOf(idUsuario)}
            );

            // Verificar si la actualización fue exitosa
            if (filasActualizadas > 0) {
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudo actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se pudo obtener el ID del usuario actual", Toast.LENGTH_SHORT).show();
        }
    }


    public long obtenerIdDelUsuarioActual(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("usuario_id", Context.MODE_PRIVATE);


        // Obtén el ID del usuario almacenado en las preferencias
        long usuarioId = preferences.getLong("usuario_id", -1);

        if (usuarioId == -1) {
            // Si el ID del usuario no está almacenado, significa que el usuario no está autenticado
            mostrarToast(context, "Error: Usuario no autenticado");
        }

        return usuarioId;
    }

    public void mostrarToast(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }



}