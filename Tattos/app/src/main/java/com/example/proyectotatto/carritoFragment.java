package com.example.proyectotatto;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class carritoFragment extends Fragment implements OnCarritoItemAddedListener {

    private RecyclerView recyclerView;
    private static CarritoAdapter carritoAdapter;
    private static DBHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrito, container, false);

        dbHelper = new DBHelper(requireContext());

        // Inicializa tu RecyclerView y su adaptador
        recyclerView = root.findViewById(R.id.recyclerViewCarrito);
        carritoAdapter = new CarritoAdapter();
        carritoAdapter.setDbHelper(dbHelper);
        recyclerView.setAdapter(carritoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        long usuarioId = obtenerIdDelUsuarioActual();

        // Carga los tatuajes en el carrito del usuario desde la base de datos
        cargarTatuajesEnCarrito();

        return root;
    }

    public void cargarTatuajesEnCarrito() {
        Cursor cursor = null;
        SQLiteDatabase db = null;

        try {
            long usuarioId = dbHelper.obtenerIdDelUsuarioActual(requireContext());

            if (usuarioId != -1) {
                // Utiliza la instancia existente de DBHelper para obtener la base de datos
                db = dbHelper.getWritableDatabase();

                cursor = db.rawQuery("SELECT * FROM Carrito WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});

                while (cursor.moveToNext()) {
                    long tatuajeId = cursor.getLong(cursor.getColumnIndexOrThrow("tatuaje_id"));
                    Tatuaje tatuaje = dbHelper.obtenerTatuajePorId(tatuajeId);
                    if (tatuaje != null) {
                        carritoAdapter.addTatuaje(tatuaje);
                    }
                }

                // No cierres la base de datos aquí, ciérrala después de todas las operaciones.
                carritoAdapter.notifyDataSetChanged();
            } else {
                dbHelper.mostrarToast(requireContext(), "Error: Usuario no autenticado");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Cierra la base de datos después de todas las operaciones
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    @Override
    public void onCarritoItemAdded(Tatuaje tatuaje) {
        // Agrega el tatuaje al adaptador y notifica cambios
        carritoAdapter.addTatuaje(tatuaje);
        carritoAdapter.notifyDataSetChanged();
    }

    // Método para realizar la acción de "Pedir Productos"
    // Método para realizar la acción de "Pedir Productos"
    public void pedirProductos(final Context context) {
        try {
            // Obtener la instancia de la base de datos
            SQLiteDatabase db = dbHelper.obtenerInstanciaBaseDatos();

            List<Tatuaje> productosEnCarrito = obtenerProductosEnCarrito(getContext());

            if (!productosEnCarrito.isEmpty()) {
                long usuarioId = obtenerIdDelUsuarioActual();

                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setNombresTatuajes(new ArrayList<>());

                for (Tatuaje tatuaje : productosEnCarrito) {
                    nuevoPedido.getNombresTatuajes().add(tatuaje.getNombre());
                }

                long idPedido = dbHelper.agregarPedido(nuevoPedido, usuarioId);

                if (idPedido != -1) {
                    // Vacía el carrito después de realizar el pedido para evitar duplicados
                    vaciarCarrito();
                    actualizarVista();

                    // Mostrar un mensaje emergente (Snackbar) con los detalles del pedido
                    mostrarDetallesPedido(context, nuevoPedido);

                } else {
                    Log.e("Pedido", "Error al crear el pedido");
                    Toast.makeText(context, "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("Pedido", "El carrito está vacío");
                Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Pedido", "Error al procesar el pedido: " + e.getMessage());
            // Capturar y mostrar excepciones
            Toast.makeText(context, "Error al procesar el pedido", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para mostrar un mensaje emergente (Snackbar) con los detalles del pedido
    private void mostrarDetallesPedido(Context context, Pedido pedido) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Pedido realizado con éxito.\nDetalles del pedido:\n");

        // Agregar los detalles del pedido al mensaje
        mensaje.append("Número de pedido: ").append(pedido.getNumeroPedido()).append("\n");
        mensaje.append("Tatuajes pedidos: ").append(TextUtils.join(", ", pedido.getNombresTatuajes())).append("\n");

        // Mostrar un Snackbar con los detalles del pedido
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, mensaje.toString(), Snackbar.LENGTH_LONG).show();
    }


    private List<Tatuaje> obtenerProductosEnCarrito(Context context) {
        long usuarioId = obtenerIdDelUsuarioActual();

        Cursor cursor = dbHelper.obtenerTatuajesEnCarritoPorUsuario(context);
        Log.e("Cursor", "Cursor obtenido: " + cursor);
        List<Tatuaje> productosEnCarrito = new ArrayList<>();

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long tatuajeId = cursor.getLong(cursor.getColumnIndexOrThrow("tatuaje_id"));
                    Tatuaje tatuaje = dbHelper.obtenerTatuajePorId(tatuajeId);
                    if (tatuaje != null) {
                        productosEnCarrito.add(tatuaje);
                    }
                } while (cursor.moveToNext());
            } else {
                Log.d("Cursor", "El cursor está vacío");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones específicas si es posible
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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