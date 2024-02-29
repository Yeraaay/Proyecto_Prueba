package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UsuariosRegistrados extends AppCompatActivity implements OnCategoriaSelectedListener {

    Button btnTodos, btnAnimales, btnCriaturas, btnPlantas, btnAnime;
    ImageButton btnPerfil, btnCarrito, btnPedidos;
    OnMenuButtonClickListener menuBtnClickListener;
    DrawerLayout drawerlayout;

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
        btnCarrito = findViewById(R.id.botonCarrito);
        btnPedidos = findViewById(R.id.botonPedidos);


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
                if (menuBtnClickListener != null) {
                    menuBtnClickListener.onMenuButtonClicked();
                }
            }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new carritoFragment());
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PedidosFragment());
            }
        });

        // Mostrar el fragmento inicial solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView2, new registradoFragmentCartas())
                    .commit();
        }

        InteriorCarta1 interiorCarta1Fragment = new InteriorCarta1();
        interiorCarta1Fragment.setOnCategoriaSelectedListener(this);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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


    public void setOnMenuButtonClickListener(OnMenuButtonClickListener listener) {
        this.menuBtnClickListener = listener;
    }

    public interface OnMenuButtonClickListener {
        void onMenuButtonClicked();
    }

}
