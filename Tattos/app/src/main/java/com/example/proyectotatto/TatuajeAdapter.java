package com.example.proyectotatto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

    public TatuajeAdapter(ArrayList<Tatuaje> listaTattos,String fragmentType) {
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

                // Lógica condicional según el fragmento
                if ("FragmentCartas".equals(fragmentType)) {
                    Intent intent = new Intent(holder.itemView.getContext(), loginActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                    Toast.makeText(holder.itemView.getContext(), "Debes iniciar sesion para continuar", Toast.LENGTH_SHORT).show();
                } else if ("registradoFragmentCartas".equals(fragmentType) && "ONI".equals(categoria)) {
                    // Reemplazar el fragmento actual con interiorCarta1
                    FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView2, new InteriorCarta1());
                    fragmentTransaction.addToBackStack(null);  // Para permitir retroceder al fragmento anterior
                    fragmentTransaction.commit();
                }
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