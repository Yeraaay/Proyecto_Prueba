package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    EditText nombreR;
    EditText apellidosR;
    EditText dniR;
    EditText telefMovilR;
    EditText contrasenaR;
    EditText confirmContrasenaR;

    Button btnRegistrarse;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombreR = findViewById(R.id.nombreRegister);
        apellidosR = findViewById(R.id.apellidosRegister);
        dniR = findViewById(R.id.DNIRegister);
        telefMovilR = findViewById(R.id.telefonoRegister);
        contrasenaR = findViewById(R.id.contrasenaRegister);
        confirmContrasenaR = findViewById(R.id.ConfirmarContrasenaRegister);
        btnRegistrarse = findViewById(R.id.botonRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = new DBHelper(registerActivity.this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();

                String nombre = nombreR.getText().toString();
                String apellidos = apellidosR.getText().toString();
                String dni = dniR.getText().toString();
                String telefono = telefMovilR.getText().toString();
                String contrasena = contrasenaR.getText().toString();
                String confirmContra = confirmContrasenaR.getText().toString();

                // Verificar si las contraseñas coinciden
                if (contrasena.equals(confirmContra)) {
                    // Verificar si el usuario ya está registrado
                    if (!isUserRegistered(db, dni)) {
                        // El usuario no está registrado, proceder con la inserción
                        ContentValues valuesUsuarios = new ContentValues();
                        valuesUsuarios.put("name", nombre);
                        valuesUsuarios.put("password", contrasena);
                        valuesUsuarios.put("Tipo", "Registrado");  // Ajusta este valor según tus necesidades

                        long newRowIdUsuarios = db.insert("Usuarios", null, valuesUsuarios);

                        ContentValues valuesRegistrados = new ContentValues();
                        valuesRegistrados.put("name", nombre);
                        valuesRegistrados.put("apellidos", apellidos);
                        valuesRegistrados.put("dni", dni);
                        valuesRegistrados.put("telefonoMovil", telefono);
                        valuesRegistrados.put("password", contrasena);
                        valuesRegistrados.put("confirm_password", confirmContra);
                        valuesRegistrados.put("usuario_id",newRowIdUsuarios);

                        long newRowIdRegistrados = db.insert("Registrados", null, valuesRegistrados);

                        if (newRowIdUsuarios != -1 && newRowIdRegistrados != -1) {
                            Toast.makeText(registerActivity.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(registerActivity.this, loginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(registerActivity.this, "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                            nombreR.setText("");
                            apellidosR.setText("");
                            dniR.setText("");
                            telefMovilR.setText("");
                            contrasenaR.setText("");
                            confirmContrasenaR.setText("");
                        }
                    } else {
                        Toast.makeText(registerActivity.this, "El usuario ya está registrado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(registerActivity.this, loginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(registerActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    contrasenaR.setText("");
                    confirmContrasenaR.setText("");
                }

                // Cerrar la conexión a la base de datos
                db.close();
            }
        });




    }
    private boolean isUserRegistered(SQLiteDatabase db, String dni) {
        String[] projection = { "dni" };
        String selection = "dni=?";
        String[] selectionArgs = { dni };

        Cursor cursor = db.query("Registrados", projection, selection, selectionArgs, null, null, null);

        boolean isRegistered = cursor.getCount() > 0;

        cursor.close();

        return isRegistered;
    }

}