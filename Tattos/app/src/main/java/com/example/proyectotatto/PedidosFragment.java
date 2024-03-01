package com.example.proyectotatto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PedidosFragment extends Fragment {

    private RecyclerView recyclerViewPedidos;
    private PedidosAdapter pedidosAdapter;

    public PedidosFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        // Inicializar RecyclerView y adaptador
        recyclerViewPedidos = view.findViewById(R.id.recyclerViewPedidos);
        pedidosAdapter = new PedidosAdapter();

        // Configurar RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPedidos.setLayoutManager(layoutManager);
        recyclerViewPedidos.setAdapter(pedidosAdapter);

        // Cargar datos de pedidos (puedes cambiar esto según cómo obtengas tus datos)
        cargarDatosPedidos();

        return view;
    }

    private void cargarDatosPedidos() {
        // Suponiendo que ya tienes la instancia de DBHelper configurada
        DBHelper dbHelper = new DBHelper(getContext());

        // Obtener el ID del usuario actual, asegúrate de tener un método para obtenerlo en DBHelper
        int usuarioId = (int) dbHelper.obtenerIdDelUsuarioActual(getContext());

        // Reemplaza "obtenerPedidosDelUsuario" con tu método real para obtener los pedidos del usuario
        List<Pedido> listaPedidos = dbHelper.obtenerPedidosDelUsuario(usuarioId);

        // Actualizar el adaptador con la lista de pedidos
        pedidosAdapter.setListaPedidos(listaPedidos);
        dbHelper.close();
    }

}