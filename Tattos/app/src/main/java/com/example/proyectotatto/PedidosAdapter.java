package com.example.proyectotatto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {
    public List<Pedido> listaPedidos = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de cada elemento de la lista de pedidos
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Asignar datos a los elementos de la lista de pedidos
        Pedido pedido = listaPedidos.get(position);
        holder.bind(pedido);
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView numeroPedidoTextView;
        private TextView listaTatuajesTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas aquí (ajusta según tu diseño)
            numeroPedidoTextView = itemView.findViewById(R.id.numeroPedidoTextView);
            listaTatuajesTextView = itemView.findViewById(R.id.listaTatuajesTextView);
        }

        public void bind(Pedido pedido) {
            // Asignar datos a las vistas
            numeroPedidoTextView.setText("Pedido " + pedido.getNumeroPedido());

            // Recorrer la lista de nombres de tatuajes y construir el texto
            StringBuilder tatuajesText = new StringBuilder();
            for (String nombreTatuaje : pedido.getNombresTatuajes()) {
                tatuajesText.append("Tatuaje: ").append(nombreTatuaje).append(", ");
            }

            // Eliminar la coma y el espacio extra al final
            if (tatuajesText.length() > 0) {
                tatuajesText.setLength(tatuajesText.length() - 2);
            }

            listaTatuajesTextView.setText(tatuajesText.toString());
        }
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
        notifyDataSetChanged();
    }
}