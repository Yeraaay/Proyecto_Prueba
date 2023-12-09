package com.example.proyectotatto;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoriaActivity extends AppCompatActivity {
    private DBHelper dbHelper;
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

        btnTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("Todos");
            }
        });

        btnAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("Animales");
            }
        });

        btnCriaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("Criaturas");
            }
        });

        btnPlantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("Plantas");
            }
        });

        btnAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("Anime");
            }
        });
    }

    public void onCategoriaSelected(String categoria) {
        // Crear un nuevo Fragment y pasar la categoría seleccionada como argumento
        Fragment fragment;

        if ("Animales".equals(categoria)) {
            fragment = new FragmentAnimales();
        } else if ("Criaturas".equals(categoria)) {
            fragment = new Fragment_criaturas();
        } else if ("Plantas".equals(categoria)) {
            fragment = new Fragment_plantas();
        } else {
            fragment = new FragmentCartas();
        }

        // Reemplazar el fragmento solo una vez después de la estructura de control
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .commit();
    }
}
