package com.example.proyectotatto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentCartas extends Fragment {
    Fragment InteriorCartasFragment,FragmentCategoria;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cartas, container, false);
        // Remove the unnecessary instantiation of FragmentCartas
        // cartasFragment = new FragmentCartas();
        InteriorCartasFragment = new InteriorCarta1();


        Button tatto1 = root.findViewById(R.id.tatto1);
        Button Categorias = root.findViewById(R.id.btnCategoria);

        tatto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the parent activity's getSupportFragmentManager() instead of getChildFragmentManager()
                requireActivity().getSupportFragmentManager().beginTransaction().hide(FragmentCartas.this).commit();

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, InteriorCartasFragment)
                        .addToBackStack(null) // This allows you to navigate back to the previous fragment
                        .commit();
            }
        });

        Categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText categoriaEditText = root.findViewById(R.id.categoria);
                String categoriaSeleccionada = categoriaEditText.getText().toString();

                // Filtra según la categoría seleccionada
                if (categoriaSeleccionada.toLowerCase().contains("animales")) {
                    // Cambia al fragmento correspondiente a la categoría de animales
                    FragmentCategoria = new FragmentAnimales();
                } else if (categoriaSeleccionada.toLowerCase().contains("ciencia ficción")||categoriaSeleccionada.toLowerCase().contains("ciencia") || categoriaSeleccionada.toLowerCase().contains("ficción")) {
                    // Cambia al fragmento correspondiente a la categoría de ciencia ficción
                    FragmentCategoria = new FragmentCienciaFiccion();
                }

                // Oculta el fragmento actual
                requireActivity().getSupportFragmentManager().beginTransaction().hide(FragmentCartas.this).commit();

                // Reemplaza el fragmento actual con el nuevo fragmento
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, FragmentCategoria)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return root;
    }
}
