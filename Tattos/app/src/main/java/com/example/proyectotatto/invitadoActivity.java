package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class invitadoActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    Fragment InicioFragment, CartasFragment;
    Button btnTodos, btnAnimales, btnCriaturas, btnPlantas, btnAnime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);

        InicioFragment = new InicioFragment();
        CartasFragment = new FragmentCartas();


        // Replace the default fragment with FragmentCartas
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, CartasFragment)
                .commit();

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
        Fragment fragment = null;


        // Reemplazar el fragmento solo una vez después de la estructura de control
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .commit();
    }
    }


