package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    Button btnAniadir,btnMostrar,btnEliminar,btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_prueba);

        btnMostrar = findViewById(R.id.botonMostrar);
        btnAniadir = findViewById(R.id.botonAñadir);
        btnEliminar = findViewById(R.id.botonEliminar);
        btnModificar = findViewById(R.id.botonModificar);

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmentoMostarTatuajeAdmin();
            }
        });

        btnAniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmentAnadirTatuajeAdmin();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mostrarFragmentEliminarTatuajeAdmin(); }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mostrarFragmentModificarTatuajeAdmin(); }
        });

    }

    private void mostrarFragmentoMostarTatuajeAdmin() {
        // Crea una instancia del fragmento
        MostarProductoAdmin mostarProductoAdminFragment = new MostarProductoAdmin();

        // Puedes pasar datos al fragmento si es necesario utilizando Bundle
        Bundle bundle = new Bundle();

        mostarProductoAdminFragment.setArguments(bundle);

        // Reemplaza el contenido actual del contenedor de fragmentos con el nuevo fragmento
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameL, mostarProductoAdminFragment)
                .addToBackStack(null)
                .commit();
    }

    private void mostrarFragmentAnadirTatuajeAdmin(){
        fragmentAnadirAdmin fragmentAnadirAdmin = new fragmentAnadirAdmin();

        Bundle bundle = new Bundle();

        fragmentAnadirAdmin.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameL, fragmentAnadirAdmin)
                .addToBackStack(null)
                .commit();

    }

    private void mostrarFragmentEliminarTatuajeAdmin(){
        fragmentEliminarAdmin fragmentEliminarAdmin = new fragmentEliminarAdmin();

        Bundle bundle = new Bundle();

        fragmentEliminarAdmin.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameL, fragmentEliminarAdmin)
                .addToBackStack(null)
                .commit();

    }

    private void mostrarFragmentModificarTatuajeAdmin(){
        fragmentObtenerIdModificar fragmentObtenerIdModificar = new fragmentObtenerIdModificar();

        Bundle bundle = new Bundle();

        fragmentObtenerIdModificar.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameL, fragmentObtenerIdModificar)
                .addToBackStack(null)
                .commit();

    }

}