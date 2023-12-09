package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoriaActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private LinearLayout linearLayoutTatuajes;
    Button btnTodos, btnAnimales, btnCriaturas, btnPlantas, btnAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        dbHelper = new DBHelper(this);

        btnTodos = findViewById(R.id.botonCategoriaTodos);
        btnAnimales = findViewById(R.id.botonCategoriaAnimales);
        btnCriaturas = findViewById(R.id.botonCategoriaCriaturas);
        btnPlantas = findViewById(R.id.botonCategoriaPlantas);
        btnAnime = findViewById(R.id.botonCategoriaAnime);

    }

    public void onCategoriaSelected(String categoria) {
        // Crear un nuevo Fragment y pasar la categoría seleccionada como argumento
        FragmentCartas tatuajesFragment = FragmentCartas.newInstance(categoria);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, tatuajesFragment)
                .commit();
    }
}