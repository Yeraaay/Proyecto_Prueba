package com.example.proyectotatto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentCartas extends Fragment {

    private RecyclerView recyclerView;
    private TatuajeAdapter tatuajeAdapter;
    private DbTattos dbTattos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartas, container, false);

        recyclerView = view.findViewById(R.id.cartas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crea una instancia de DbTattos utilizando el contexto del fragmento
        dbTattos = new DbTattos(getContext());

        // Obtén la lista de todos los tatuajes desde la base de datos
        ArrayList<Tatuaje> tatuajeList = dbTattos.mostrarContactos();

        // Crea un adaptador y configúralo con la lista de tatuajes
        tatuajeAdapter = new TatuajeAdapter(tatuajeList);

        // Establece el adaptador en el RecyclerView
        recyclerView.setAdapter(tatuajeAdapter);

        return view;
    }

    // Método para cambiar la categoría y actualizar la lista de tatuajes
    public void cambiarCategoria(String nuevaCategoria) {
        ArrayList<Tatuaje> tatuajeList;

        // Si la categoría es "Todos", muestra todos los tatuajes
        if (nuevaCategoria.equals("Todos")) {
            tatuajeList = dbTattos.mostrarContactos();
        } else {
            // Si la categoría no es "Todos", filtra por la categoría seleccionada
            tatuajeList = dbTattos.obtenerTatuajesPorCategoria(nuevaCategoria);
        }

        // Actualiza el adaptador con la nueva lista de tatuajes
        tatuajeAdapter.actualizarLista(tatuajeList);
    }
}