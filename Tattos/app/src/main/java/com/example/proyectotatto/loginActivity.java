package com.example.proyectotatto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    EditText nombre;
    EditText contrasena;

    Button botonLogin;
    Button botonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = findViewById(R.id.nombreLogin);
        contrasena = findViewById(R.id.contrasenaLogin);
        botonLogin = findViewById(R.id.btLogin);
        botonRegistrarse = findViewById(R.id.btRegister);

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
                    @SuppressLint("Range") String tipoUsuario = cursor.getString(cursor.getColumnIndex("Tipo"));

                    if ("Admin".equals(tipoUsuario)) {
                        Intent intent1 = new Intent(loginActivity.this, AdminActivity.class);
                        startActivity(intent1);
                    } else if ("Registrado".equals(tipoUsuario)) {
                        Intent intent2 = new Intent(loginActivity.this, UsuariosRegistrados.class);
                        startActivity(intent2);
                    }

                } else {
                    Intent intent3 = new Intent(loginActivity.this, registerActivity.class);
                    startActivity(intent3);
                }

                cursor.close();
            }
        });
    }
}
