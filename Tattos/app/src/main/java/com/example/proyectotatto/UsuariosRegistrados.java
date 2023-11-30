package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UsuariosRegistrados extends AppCompatActivity {

    Fragment registradoCartas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_registrados);

        registradoCartas = new registradoFragmentCartas();

        // Replace the default fragment with FragmentCartas
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer2, registradoCartas)
                .commit();


    }



}