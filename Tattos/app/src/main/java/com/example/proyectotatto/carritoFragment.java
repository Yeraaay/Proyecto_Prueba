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

        dbHelper = new DBHelper(requireContext());

        // Inicializa tu RecyclerView y su adaptador
        recyclerView = root.findViewById(R.id.recyclerViewCarrito);
        carritoAdapter = new CarritoAdapter();
        carritoAdapter.setDbHelper(dbHelper);
        recyclerView.setAdapter(carritoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnPedirProductos = root.findViewById(R.id.botonPedirProductos);
        btnPedirProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirProductos(requireContext());
                vaciarCarrito();
                actualizarVista();
            }
        });

        long usuarioId = obtenerIdDelUsuarioActual();

        // Carga los tatuajes en el carrito del usuario desde la base de datos
        cargarTatuajesEnCarrito(requireContext());

        return root;
    }

    public void cargarTatuajesEnCarrito(Context context) {
        // Obtén el ID del usuario actual
        long usuarioId = dbHelper.obtenerIdDelUsuarioActual(context);

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
            // No cierres dbHelper aquí, ya que se cerrará en su debido momento
            carritoAdapter.notifyDataSetChanged();
        } else {
            // Manejo de error, el usuario no está autenticado
            dbHelper.mostrarToast(context, "Error: Usuario no autenticado");
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
            List<Tatuaje> productosEnCarrito = obtenerProductosEnCarrito(requireContext());

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
                    Toast.makeText(getContext(), "Productos pedidos correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "El carrito está vacío", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }


    private List<Tatuaje> obtenerProductosEnCarrito(Context context) {
        long usuarioId = obtenerIdDelUsuarioActual();
        Cursor cursor = dbHelper.obtenerTatuajesEnCarritoPorUsuario(context);

        List<Tatuaje> productosEnCarrito = new ArrayList<>();

        while (cursor.moveToNext()) {
            long tatuajeId = cursor.getLong(cursor.getColumnIndexOrThrow("tatuaje_id"));
            Tatuaje tatuaje = dbHelper.obtenerTatuajePorId(tatuajeId);
            if (tatuaje != null) {
                productosEnCarrito.add(tatuaje);
            }
        }

        cursor.close();
        return productosEnCarrito;
    }

    private void vaciarCarrito() {
        long usuarioId = obtenerIdDelUsuarioActual();
        dbHelper.eliminarTodosTatuajesEnCarritoPorUsuario(usuarioId);
    }

    private void actualizarVista() {
        cargarTatuajesEnCarrito(getContext());
    }

    private long obtenerIdDelUsuarioActual() {
        return dbHelper.obtenerIdDelUsuarioActual(getContext());
    }


}