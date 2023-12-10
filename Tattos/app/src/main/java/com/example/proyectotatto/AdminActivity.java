package com.example.proyectotatto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    Button btnAñadir,btnMostrar,btnEliminar,btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_prueba);

        btnMostrar = findViewById(R.id.botonMostrar);

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmentoMostarProductoAdmin();
            }
        });





    }

    private void mostrarFragmentoMostarProductoAdmin() {
        // Crea una instancia del fragmento
        MostarProductoAdmin mostarProductoAdminFragment = new MostarProductoAdmin();

        // Puedes pasar datos al fragmento si es necesario utilizando Bundle
        Bundle bundle = new Bundle();
        // bundle.putString("Categoria", "TusDatos"); // Agrega datos si es necesario
        mostarProductoAdminFragment.setArguments(bundle);

        // Reemplaza el contenido actual del contenedor de fragmentos con el nuevo fragmento
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameL, mostarProductoAdminFragment)
                .addToBackStack(null)  // Agrega a la pila de retroceso para permitir regresar
                .commit();
    }

}