package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.example.proyectotatto.OnCategoriaSelectedListener;

public class UsuariosRegistrados extends AppCompatActivity implements OnCategoriaSelectedListener {

    Button btnTodos, btnAnimales, btnCriaturas, btnPlantas, btnAnime;
    ImageButton btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_registrados);

        // Inicializar los botones
        btnTodos = findViewById(R.id.botonCategoriaTodos);
        btnAnimales = findViewById(R.id.botonCategoriaAnimales);
        btnCriaturas = findViewById(R.id.botonCategoriaCriaturas);
        btnPlantas = findViewById(R.id.botonCategoriaPlantas);
        btnAnime = findViewById(R.id.botonCategoriaAnime);
        btnPerfil = findViewById(R.id.imageButton);

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
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuariosRegistrados.this, EditarPerfil.class);
                startActivity(intent);
            }
        });

        // Mostrar el fragmento inicial solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView2, new registradoFragmentCartas())
                    .commit();
        }

        // Asegúrate de establecer el listener en el fragmento cuando lo creas o cuando sea apropiado
        // Por ejemplo, después de instanciar InteriorCarta1 en algún lugar de tu código
        InteriorCarta1 interiorCarta1Fragment = new InteriorCarta1();
        interiorCarta1Fragment.setOnCategoriaSelectedListener(this);
    }



    @Override
    public void onCategoriaSelected(String categoria) {
        // Obtener el fragmento actual
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);

        // Verificar si el fragmento es nulo y es de tipo registradoFragmentCartas
        if (currentFragment instanceof registradoFragmentCartas) {
            // Realizar el casting y llamar al método en el fragmento para cambiar la categoría
            registradoFragmentCartas fragmentCartas = (registradoFragmentCartas) currentFragment;
            fragmentCartas.cambiarCategoriaR(categoria);
        } else {
            // Manejar la situación cuando el fragmento actual no es del tipo esperado
            Log.e("UsuariosRegistrados", "El fragmento actual no es de tipo registradoFragmentCartas");
            // Puedes agregar un manejo específico o lanzar una excepción según tus necesidades
        }
    }
}
