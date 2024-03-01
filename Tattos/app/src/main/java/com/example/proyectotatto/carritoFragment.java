package com.example.proyectotatto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class carritoFragment extends Fragment implements OnCarritoItemAddedListener {

    private RecyclerView recyclerView;
    private static CarritoAdapter carritoAdapter;
    Button btnPedirProductos;
    DBHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrito, container, false);

        dbHelper = new DBHelper(getContext());

        // Inicializa tu RecyclerView y su adaptador
        recyclerView = root.findViewById(R.id.recyclerViewCarrito);
        carritoAdapter = new CarritoAdapter();
        carritoAdapter.setDbHelper(dbHelper);
        recyclerView.setAdapter(carritoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnPedirProductos = root.findViewById(R.id.botonPedirProductos);
        btnPedirProductos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirProductos(requireContext());
                vaciarCarrito();
                actualizarVista();
            }
        });

        long usuarioId = obtenerIdDelUsuarioActual();

        // Carga los tatuajes en el carrito del usuario desde la base de datos
        cargarTatuajesEnCarrito();

        return root;
    }

    public void cargarTatuajesEnCarrito() {
        // Obtén el ID del usuario actual usando el contexto del fragmento
        long usuarioId = dbHelper.obtenerIdDelUsuarioActual(requireContext());

        if (usuarioId != -1) {
            // Utiliza la misma instancia de DBHelper para todas las operaciones
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM Carrito WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});

            while (cursor.moveToNext()) {
                long tatuajeId = cursor.getLong(cursor.getColumnIndexOrThrow("tatuaje_id"));
                Tatuaje tatuaje = dbHelper.obtenerTatuajePorId(tatuajeId);
                if (tatuaje != null) {
                    carritoAdapter.addTatuaje(tatuaje);
                }
            }

            cursor.close();
            db.close(); // Cierra explícitamente la conexión de la base de datos
            carritoAdapter.notifyDataSetChanged();
        } else {
            // Manejo de error, el usuario no está autenticado
            dbHelper.mostrarToast(requireContext(), "Error: Usuario no autenticado");
        }
    }




    @Override
    public void onCarritoItemAdded(Tatuaje tatuaje) {
        // Agrega el tatuaje al adaptador y notifica cambios
        carritoAdapter.addTatuaje(tatuaje);
        carritoAdapter.notifyDataSetChanged();
    }

    // Método para realizar la acción de "Pedir Productos"
    private void pedirProductos(Context context) {

        try {
            // Obtén los productos del carrito
            List<Tatuaje> productosEnCarrito = obtenerProductosEnCarrito(context);

            if (!productosEnCarrito.isEmpty()) {
                // Guarda los productos en la tabla de "Pedidos"
                long usuarioId = obtenerIdDelUsuarioActual();

                // Crear un nuevo pedido
                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setNombresTatuajes(new ArrayList<>());

                // Agrega los nombres de los tatuajes al pedido
                for (Tatuaje tatuaje : productosEnCarrito) {
                    nuevoPedido.getNombresTatuajes().add(tatuaje.getNombre());
                }

                // Agrega el pedido a la base de datos
                long idPedido = dbHelper.agregarPedido(nuevoPedido, usuarioId);

                if (idPedido != -1) {
                    // Actualizar tu RecyclerView u otras acciones necesarias
                    actualizarVista();
                    Toast.makeText(context, "Productos pedidos correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private List<Tatuaje> obtenerProductosEnCarrito(Context context) {
        long usuarioId = obtenerIdDelUsuarioActual();

        Cursor cursor = dbHelper.obtenerTatuajesEnCarritoPorUsuario(context);
        Log.e("Cursor", "Cursor obtenido: " + cursor);
        List<Tatuaje> productosEnCarrito = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // El cursor contiene al menos una fila de datos
                do {
                    long tatuajeId = cursor.getLong(cursor.getColumnIndexOrThrow("tatuaje_id"));
                    Tatuaje tatuaje = dbHelper.obtenerTatuajePorId(tatuajeId);
                    if (tatuaje != null) {
                        productosEnCarrito.add(tatuaje);
                    }
                } while (cursor.moveToNext());
            } else {
                // El cursor está vacío
                Log.d("Cursor", "El cursor está vacío");
            }
            cursor.close();
        } else {
            // Manejo de un cursor nulo
            Log.d("Cursor", "El cursor es nulo");
        }

        return productosEnCarrito;
    }


    private void vaciarCarrito() {
        long usuarioId = obtenerIdDelUsuarioActual();
        dbHelper.eliminarTodosTatuajesEnCarritoPorUsuario(usuarioId);
    }

    private void actualizarVista() {
        cargarTatuajesEnCarrito();
    }

    private long obtenerIdDelUsuarioActual() {
        return dbHelper.obtenerIdDelUsuarioActual(getContext());
    }


}