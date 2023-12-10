package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class invitadoActivity extends AppCompatActivity {

    private Button btnTodos, btnAnimales, btnCriaturas, btnPlantas, btnAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);

        // Inicializar los botones
        btnTodos = findViewById(R.id.botonCategoriaTodos);
        btnAnimales = findViewById(R.id.botonCategoriaAnimales);
        btnCriaturas = findViewById(R.id.botonCategoriaCriaturas);
        btnPlantas = findViewById(R.id.botonCategoriaPlantas);
        btnAnime = findViewById(R.id.botonCategoriaAnime);

        // Manejar clics en los botones
        btnTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("Todos");
            }
        });

        btnAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("animales");
            }
        });

        btnCriaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("criaturas");
            }
        });

        btnPlantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("plantas");
            }
        });

        btnAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoriaSelected("anime");
            }
        });

        // Mostrar el fragmento inicial solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView2, new FragmentCartas())
                    .commit();
        }
    }

    public void onCategoriaSelected(String categoria) {
        // Obtener el fragmento actual
        FragmentCartas fragment = (FragmentCartas) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);

        // Verificar si el fragmento es nulo
        if (fragment != null) {
            // Llamar al método en el fragmento para cambiar la categoría
            fragment.cambiarCategoria(categoria);
        }
    }
}
