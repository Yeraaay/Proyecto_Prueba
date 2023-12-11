package com.example.proyectotatto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class registradoFragmentCartas extends Fragment {

    private RecyclerView recyclerView;
    private TatuajeAdapter tatuajeAdapter;
    private DbTattos dbTattos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrado_cartas, container, false);

        recyclerView = view.findViewById(R.id.recicleRegistrado);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crea una instancia de DbTattos utilizando el contexto del fragmento
        dbTattos = new DbTattos(getContext());

        // Obtén la lista de todos los tatuajes desde la base de datos
        ArrayList<Tatuaje> tatuajeList = dbTattos.mostrarContactos();

        // Crea un adaptador y configúralo con la lista de tatuajes y el tipo de fragmento
        tatuajeAdapter = new TatuajeAdapter(tatuajeList,"registradoFragmentCartas");

        // Establece el adaptador en el RecyclerView
        recyclerView.setAdapter(tatuajeAdapter);

        return view;
    }

    // Método para cambiar la categoría y actualizar la lista de tatuajes
    public void cambiarCategoriaR(String nuevaCategoria) {
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
