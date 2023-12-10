package com.example.proyectotatto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TatuajeAdapter extends RecyclerView.Adapter<TatuajeAdapter.ViewHolder> {

    ArrayList<Tatuaje> listaTatto;

    public TatuajeAdapter(ArrayList<Tatuaje> listaTattos){
        this.listaTatto = listaTattos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tatuaje,null,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewNombre.setText(listaTatto.get(position).getNombre());
        holder.viewCategoria.setText(listaTatto.get(position).getCategoria());

        // Obtener el nombre de la imagen desde el objeto Tatuaje
        String nombreImagen = listaTatto.get(position).getImagen();

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

        TextView viewNombre,viewCategoria;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.nombreTextView);
            viewCategoria = itemView.findViewById(R.id.categoriaTextview);
            imagen = itemView.findViewById(R.id.imagenImageView);
        }
    }
}
