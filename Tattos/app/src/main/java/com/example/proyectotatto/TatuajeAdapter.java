package com.example.proyectotatto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TatuajeAdapter extends RecyclerView.Adapter<TatuajeAdapter.ViewHolder> {

    private ArrayList<Tatuaje> listaTatto;
    private String fragmentType;

    public TatuajeAdapter(ArrayList<Tatuaje> listaTattos, String fragmentType) {
        this.listaTatto = listaTattos;
        this.fragmentType = fragmentType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tatuaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Tatuaje tatuaje = listaTatto.get(position);

        holder.viewNombre.setText(tatuaje.getNombre());
        holder.viewCategoria.setText(tatuaje.getCategoria());
        holder.viewPrecio.setText(String.valueOf(tatuaje.getPrecio()));
        holder.viewDescripcion.setText(tatuaje.getDescripcion());

        String nombreImagen = tatuaje.getImagen();
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(
                nombreImagen, "drawable", holder.itemView.getContext().getPackageName());

        holder.imagen.setImageResource(resourceId);

        // Agregar un OnClickListener a la tarjeta
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = tatuaje.getNombre();

                // Obtener el FragmentManager
                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                // Iniciar una nueva transacción de fragmento
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Crear una instancia de InteriorCarta1
                InteriorCarta1 interiorCarta1 = new InteriorCarta1();
                // Crear un Bundle para pasar datos al fragmento
                Bundle args = new Bundle();
                // Pasar el nombre del tatuaje seleccionado al fragmento
                args.putString("nombreTatuaje", categoria);
                interiorCarta1.setArguments(args);

                // Reemplazar el fragmento actual con InteriorCarta1
                fragmentTransaction.replace(R.id.fragmentContainerView2, interiorCarta1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTatto.size();
    }

    public void actualizarLista(ArrayList<Tatuaje> nuevaLista) {
        this.listaTatto.clear();  // Limpia la lista actual
        this.listaTatto.addAll(nuevaLista);  // Añade la nueva lista
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCategoria, viewPrecio, viewDescripcion;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.nombreTextView);
            viewCategoria = itemView.findViewById(R.id.categoriaTextview);
            viewPrecio = itemView.findViewById(R.id.precioTextView);
            viewDescripcion = itemView.findViewById(R.id.descripcionTextView);
            imagen = itemView.findViewById(R.id.imagenImageView);
        }
    }
}
