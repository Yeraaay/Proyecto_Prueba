package com.example.proyectotatto;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    DBHelper dbHelper;
    private List<Tatuaje> listaTatuajes = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tatuaje_eliminar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Asignar datos a los elementos de la lista (puedes personalizarlo según tus necesidades)
        Tatuaje tatuaje = listaTatuajes.get(position);
        holder.bind(tatuaje, dbHelper, this);
    }

    @Override
    public int getItemCount() {
        return listaTatuajes.size();
    }

    public void addTatuaje(Tatuaje tatuaje) {
        listaTatuajes.add(tatuaje);
        notifyDataSetChanged(); // Notificar cambios al RecyclerView
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreTextView, precioTextView;
        private ImageView imagenImageView;
        private Button btnEliminar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar las vistas aquí (ajusta según tu diseño)
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            imagenImageView = itemView.findViewById(R.id.imagenImageView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
            btnEliminar = itemView.findViewById(R.id.botonEliminar);
        }

        public void bind(Tatuaje tatuaje, DBHelper dbHelper, CarritoAdapter adapter) {
            nombreTextView.setText(tatuaje.getNombre());
            precioTextView.setText("Precio: " + String.valueOf(tatuaje.getPrecio()));

            Glide.with(itemView.getContext())
                    .load(tatuaje.getImagen())
                    .into(imagenImageView);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Eliminar el producto del carrito
                        listaTatuajes.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener el ID del tatuaje que se va a eliminar
                    String nombretatuaje = tatuaje.getNombre();
                    long tatuajeId = dbHelper.obtenerIdDelTatuaje(nombretatuaje); // Asumo que puedes obtener el ID del tatuaje desde tu objeto Tatuaje
                    long usuarioId = dbHelper.obtenerIdDelUsuarioActual(itemView.getContext()); // Asegúrate de tener el método obtenerIdDelUsuarioActual implementado

                    // Eliminar el tatuaje visualmente
                    int posicion = getAdapterPosition();
                    adapter.eliminarTatuaje(posicion);

                    // Eliminar el tatuaje de la base de datos
                    dbHelper.eliminarTatuajeDelCarrito(tatuajeId, usuarioId);
                }
            });
        }
    }

    // Método para eliminar un tatuaje de la lista visualmente
    public void eliminarTatuaje(int posicion) {
        listaTatuajes.remove(posicion);
        notifyItemRemoved(posicion);
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}