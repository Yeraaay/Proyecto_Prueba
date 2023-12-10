package com.example.proyectotatto;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class registradoFragmentCartas extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_registrado_cartas, container, false);

        CardView cardView1 = root.findViewById(R.id.carta1Registrada);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cambiar al fragmento FragmentInteriorCarta1 al hacer clic en CardView
                InteriorCarta1 fragmentInteriorCarta1 = new InteriorCarta1();
                replaceFragment(fragmentInteriorCarta1);
            }
        });

        return root;
    }

    private void replaceFragment(Fragment fragment) {
        // Obtener el FragmentManager
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        // Comenzar la transacción de fragmentos
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.fragmentContainerView2, fragment);

        // Agregar la transacción a la pila de retroceso
        transaction.addToBackStack(null);

        // Realizar la transacción
        transaction.commit();
    }
}
