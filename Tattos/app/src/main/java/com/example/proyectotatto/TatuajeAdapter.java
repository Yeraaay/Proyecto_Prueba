package com.example.proyectotatto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TatuajeAdapter extends RecyclerView.Adapter<TatuajeAdapter.ViewHolder> {

    ArrayList<Tatuaje> listaTatto;

    public TatuajeAdapter(ArrayList<Tatuaje> listaTattos){
        this.listaTatto = listaTattos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tatuaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tatuaje tatuaje = listaTatto.get(position);

        holder.viewNombre.setText(tatuaje.getNombre());
        holder.viewCategoria.setText(tatuaje.getCategoria());
        holder.viewPrecio.setText(String.valueOf(tatuaje.getPrecio()));  // Agregar el precio
        holder.viewDescripcion.setText(tatuaje.getDescripcion());  // Agregar la descripción

        // Obtener el nombre de la imagen desde el objeto Tatuaje
        String nombreImagen = tatuaje.getImagen();

        // Obtener el ID del recurso drawable utilizando el nombre de la imagen
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(
                nombreImagen, "drawable", holder.itemView.getContext().getPackageName());

        // Cargar la imagen en el ImageView
        holder.imagen.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return listaTatto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCategoria, viewPrecio, viewDescripcion;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.nombreTextView);
            viewCategoria = itemView.findViewById(R.id.categoriaTextview);
            viewPrecio = itemView.findViewById(R.id.precioTextView);  // Agregar el TextView para el precio
            viewDescripcion = itemView.findViewById(R.id.descripcionTextView);  // Agregar el TextView para la descripción
            imagen = itemView.findViewById(R.id.imagenImageView);
        }
    }
}