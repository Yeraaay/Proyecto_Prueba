package com.example.proyectotatto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentCartas extends Fragment {
    Fragment InteriorCartasFragment,FragmentCategoria;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartas, container, false);



        return view;
    }


    // Método estático para crear una nueva instancia del Fragment con la categoría como argumento
    public static FragmentCartas newInstance(String categoria) {
        FragmentCartas fragment = new FragmentCartas();
        Bundle args = new Bundle();
        args.putString("categoria", categoria);
        fragment.setArguments(args);
        return fragment;
    }
}