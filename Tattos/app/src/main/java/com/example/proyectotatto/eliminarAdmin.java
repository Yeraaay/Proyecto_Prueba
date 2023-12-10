package com.example.proyectotatto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class eliminarAdmin extends Fragment {

    Button btnBorrarTatto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eliminar_admin, container, false);

        btnBorrarTatto = view.findViewById(R.id.botonEliminacion);

        btnBorrarTatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}