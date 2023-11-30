package com.example.proyectotatto;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InteriorCarta1 extends Fragment {

    Button invitadoCompra;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_interior_carta1, container, false);

        invitadoCompra = root.findViewById(R.id.botonCarrito);

        invitadoCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
