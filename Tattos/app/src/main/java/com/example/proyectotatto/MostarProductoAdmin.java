package com.example.proyectotatto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MostarProductoAdmin extends Fragment {

    public RecyclerView recyclerView;
    private List<Tatuaje> tatuajeList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostar_producto_admin, container, false);

        recyclerView = view.findViewById(R.id.recycletatto);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Cambiado a getContext()

        DbTattos dbTattos = new DbTattos(getContext()); // Cambiado a getContext()

        tatuajeList = new ArrayList<>();

        TatuajeAdapter tatuajeAdapter = new TatuajeAdapter(dbTattos.mostrarContactos());
        recyclerView.setAdapter(tatuajeAdapter);

        return view;
    }


}
