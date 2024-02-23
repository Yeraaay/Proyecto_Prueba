package com.example.proyectotatto;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
        // Realizar una consulta a la base de datos para obtener los datos del usuario
        Cursor cursor = mDatabase.query(
                "Usuarios",
                new String[]{"name", "password"}, // Columnas que deseas recuperar
                null,
                null,
                null,
                null,
                null
        );

        // Verificar si se obtuvo algún resultado
        if (cursor != null && cursor.moveToFirst()) {
            // Obtener el nombre y la contraseña actual del usuario desde el cursor
            String nombreActual = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String contraseñaActual = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            // Mostrar el nombre y la contraseña actual en los campos de entrada
            editTextName.setText(nombreActual);
            editTextPassword.setText(contraseñaActual);

            // Cerrar el cursor
            cursor.close();
        } else {
            // No se encontraron datos en la base de datos
            Toast.makeText(this, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarDatosUsuario() {
        // Obtener el nuevo nombre y contraseña ingresados por el usuario
        String nuevoNombre = editTextName.getText().toString();
        String nuevaContraseña = editTextPassword.getText().toString();

        // Modificar los datos del usuario en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", nuevoNombre);
        contentValues.put("password", nuevaContraseña);

        // Ejecutar una consulta de actualización en la base de datos
        int filasActualizadas = mDatabase.update(
                "Usuarios",
                contentValues,
                null,
                null
        );

        // Verificar si la actualización fue exitosa
        if (filasActualizadas > 0) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo actualizar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
