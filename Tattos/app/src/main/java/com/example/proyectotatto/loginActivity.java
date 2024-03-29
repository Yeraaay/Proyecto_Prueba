package com.example.proyectotatto;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    EditText nombre;
    EditText contrasena;

    Button botonLogin;
    Button botonRegistrarse;

    Button invitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = findViewById(R.id.nombreLogin);
        contrasena = findViewById(R.id.contrasenaLogin);
        botonLogin = findViewById(R.id.btLogin);
        botonRegistrarse = findViewById(R.id.btRegister);
        invitado = findViewById(R.id.botonInvitado);

        // Crear la base de datos y el usuario administrador al iniciar la actividad de login
        DBHelper dbhelper = new DBHelper(loginActivity.this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        dbhelper.createAdminUser(db);

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = nombre.getText().toString();
                String contrasenaUsuario = contrasena.getText().toString();

                Cursor cursor = db.rawQuery("SELECT * FROM Usuarios WHERE name = ? AND password = ?",
                        new String[]{nombreUsuario, contrasenaUsuario});

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow("Tipo"));

                    if ("Admin".equals(tipoUsuario)) {
                        Intent intent1 = new Intent(loginActivity.this, AdminActivity.class);
                        startActivity(intent1);
                    } else if ("Registrado".equals(tipoUsuario)) {
                        Intent intent2 = new Intent(loginActivity.this, UsuariosRegistrados.class);
                        intent2.putExtra("nombreUsuario", nombreUsuario);
                        intent2.putExtra("contrasenaUsuario", contrasenaUsuario);
                        @SuppressLint("Range") long idUsuario = cursor.getLong(cursor.getColumnIndex("id"));
                        establecerIdUsuarioActual(loginActivity.this, idUsuario);
                        startActivity(intent2);

                        // Guardar el ID del usuario actual en las preferencias
                        long usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                        guardarIdUsuario(usuarioId);
                        cambiarDeCuenta(usuarioId);
                    }
                } else {
                    Toast.makeText(loginActivity.this, "Credenciales incorrectas. Por favor, inténtalo de nuevo o regístrate si eres un nuevo usuario.", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(loginActivity.this, registerActivity.class);
                    startActivity(intent3);
                }

                cursor.close();
            }
        });

        invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, invitadoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void cambiarDeCuenta(long nuevoIdUsuario) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.actualizarIdUsuarioActual(this, nuevoIdUsuario);
    }

    private void guardarIdUsuario(long usuarioId) {
        SharedPreferences sharedPreferences = getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("id", usuarioId);
        editor.apply();
    }

    public void establecerIdUsuarioActual(Context context, long idUsuario) {
        Log.e("Miapp",String.valueOf(idUsuario));
        SharedPreferences preferences = context.getSharedPreferences("usuario_id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("usuario_id", idUsuario);
        editor.apply();
    }

}
