package com.example.proyectotatto;


import android.content.ContentValues;
import android.content.Context;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ejemplo.db";
    private static final String TABLE_ADMIN = "Admin";

    public static final String TABLE_USUARIOS = "Usuarios";
    private static final String TABLE_REGISTRADOS = "Registrados";
    public static final String TABLE_TATUAJES = "Tatuajes";
    private static final String TABLE_CARRITO = "Carrito";
    private static final String TABLE_PEDIDOS = "Pedidos";
    private Context mContext;

    private SQLiteDatabase database;
    private final ReentrantLock databaseLock = new ReentrantLock();


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USUARIOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "Tipo TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ADMIN + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "usuario_id INTEGER," +  // Nueva columna para la clave foránea
                "FOREIGN KEY (usuario_id) REFERENCES " + TABLE_USUARIOS + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_REGISTRADOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "apellidos TEXT," +
                "dni TEXT," +
                "telefonoMovil TEXT," +
                "password TEXT," +
                "confirm_password TEXT," +
                "usuario_id INTEGER," +  // Nueva columna para la clave foránea
                "FOREIGN KEY (usuario_id) REFERENCES " + TABLE_USUARIOS + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_TATUAJES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "imagen_resource_id String," +
                "precio DOUBLE," +  // Asumiendo que el precio es un valor numérico (REAL)
                "descripcion TEXT," +  // Añadiendo la columna para la descripción
                "categoria TEXT)");


        db.execSQL("CREATE TABLE " + TABLE_CARRITO + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tatuaje_id INTEGER," +
                "usuario_id INTEGER," +
                "carrito_id INTEGER," +  // Nueva columna para el identificador único del carrito
                "FOREIGN KEY (tatuaje_id) REFERENCES " + TABLE_TATUAJES + "(id)," +
                "FOREIGN KEY (usuario_id) REFERENCES " + TABLE_USUARIOS + "(id)," +
                "FOREIGN KEY (carrito_id) REFERENCES " + TABLE_CARRITO + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_PEDIDOS + " (" +
                "pedido_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tatuaje_id INTEGER," +
                "usuario_id INTEGER," +
                "nombresTatuajes TEXT);");

        //Criatura
        ContentValues valuesTatuaje1 = new ContentValues();
        valuesTatuaje1.put("nombre", "ONI");
        valuesTatuaje1.put("imagen_resource_id", R.drawable.oni1);
        valuesTatuaje1.put("precio", 20);
        valuesTatuaje1.put("descripcion", "Demonio de la cultura japonesa");
        valuesTatuaje1.put("categoria", "criaturas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje1);

        //Planta
        ContentValues valuesTatuaje2 = new ContentValues();
        valuesTatuaje2.put("nombre", "FLOR");
        valuesTatuaje2.put("imagen_resource_id", R.drawable.flor1);
        valuesTatuaje2.put("precio", 50);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje2.put("descripcion", "Descripción de la flor");  // Ajusta la descripción según sea necesario
        valuesTatuaje2.put("categoria", "plantas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje2);

        //Animal
        ContentValues valuesTatuaje3 = new ContentValues();
        valuesTatuaje3.put("nombre", "SPIDER");
        valuesTatuaje3.put("imagen_resource_id", R.drawable.arana1);
        valuesTatuaje3.put("precio", 25);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje3.put("descripcion", "Descripción de la araña");  // Ajusta la descripción según sea necesario
        valuesTatuaje3.put("categoria", "animales");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje3);

        //Animal
        ContentValues valuesTatuaje4 = new ContentValues();
        valuesTatuaje4.put("nombre", "SNAKE");
        valuesTatuaje4.put("imagen_resource_id", R.drawable.serpiente1);
        valuesTatuaje4.put("precio", 35);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje4.put("descripcion", "Descripción de la serpiente");  // Ajusta la descripción según sea necesario
        valuesTatuaje4.put("categoria", "animales");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje4);

        //Criatura
        ContentValues valuesTatuaje5 = new ContentValues();
        valuesTatuaje5.put("nombre", "GRIFO");
        valuesTatuaje5.put("imagen_resource_id", R.drawable.grifo);
        valuesTatuaje5.put("precio", 45);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje5.put("descripcion", "Descripción del Grifo");  // Ajusta la descripción según sea necesario
        valuesTatuaje5.put("categoria", "criaturas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje5);

        //Planta
        ContentValues valuesTatuaje6 = new ContentValues();
        valuesTatuaje6.put("nombre", "MARIHUANA");
        valuesTatuaje6.put("imagen_resource_id", R.drawable.marihuana);
        valuesTatuaje6.put("precio", 25);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje6.put("descripcion", "Descripción de la marihuana");  // Ajusta la descripción según sea necesario
        valuesTatuaje6.put("categoria", "plantas");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje6);

        //Anime
        ContentValues valuesTatuaje7 = new ContentValues();
        valuesTatuaje7.put("nombre", "GOKU");
        valuesTatuaje7.put("imagen_resource_id", R.drawable.goku);
        valuesTatuaje7.put("precio", 50);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje7.put("descripcion", "Descripción de Goku");  // Ajusta la descripción según sea necesario
        valuesTatuaje7.put("categoria", "anime");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje7);

        //Anime
        ContentValues valuesTatuaje8 = new ContentValues();
        valuesTatuaje8.put("nombre", "NARUTO");
        valuesTatuaje8.put("imagen_resource_id", R.drawable.naruto);
        valuesTatuaje8.put("precio", 50);  // Ajusta el valor del precio según sea necesario
        valuesTatuaje8.put("descripcion", "Descripción de Naruto");  // Ajusta la descripción según sea necesario
        valuesTatuaje8.put("categoria", "anime");
        db.insert(TABLE_TATUAJES, null, valuesTatuaje8);

    }


    public void createAdminUser(SQLiteDatabase db) {
        // Verificar si ya existe un usuario administrador
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE Tipo = 'Admin'", null);
        if (cursor.getCount() == 0) {
            // No hay usuario administrador, se puede crear
            ContentValues adminValues = new ContentValues();
            adminValues.put("name", "usuario");
            adminValues.put("password", "usuario");
            adminValues.put("Tipo", "Admin");

            long adminId = db.insert(TABLE_USUARIOS, null, adminValues);

            if (adminId != -1) {
                ContentValues adminTableValues = new ContentValues();
                adminTableValues.put("id", adminId);
                adminTableValues.put("name", "usuario");
                adminTableValues.put("password", "usuario");
                adminTableValues.put("usuario_id", adminId);

                db.insert(TABLE_ADMIN, null, adminTableValues);
            }
        }

        cursor.close();
    }

    public long agregarTatuajeAlCarrito(long tatuajeId, Context context) {
        long usuarioId = obtenerIdDelUsuarioActual(context);

        if (usuarioId != -1) {
            try {
                open();
                ContentValues values = new ContentValues();
                values.put("tatuaje_id", tatuajeId);
                values.put("usuario_id", usuarioId);
                return database.insert(TABLE_CARRITO, null, values);
            } catch (Exception e) {
                e.printStackTrace();
                // Manejo de excepciones
                return -1;
            } finally {
                close();
            }
        } else {
            // Manejo de error, el usuario no está autenticado
            return -1;
        }
    }

    public Cursor obtenerTatuajesEnCarritoPorUsuario(Context context) {
        long usuarioId = obtenerIdDelUsuarioActual(context);

        if (usuarioId != -1) {
            try {
                open();
                return database.rawQuery("SELECT * FROM " + TABLE_CARRITO + " WHERE usuario_id = ?", new String[]{String.valueOf(usuarioId)});
            } catch (Exception e) {
                e.printStackTrace();
                // Manejo de excepciones
                return null;
            } finally {
                close();
            }
        } else {
            // Manejo de error, el usuario no está autenticado
            return null;
        }
    }


    public long obtenerIdDelTatuaje(String nombreTatuaje) {
        long tatuajeId = -1;

        try {
            open();  // Abre la base de datos antes de realizar operaciones

            String[] projection = {"id"};
            String selection = "nombre = ?";
            String[] selectionArgs = {nombreTatuaje};

            Cursor cursor = database.query(
                    TABLE_TATUAJES,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                tatuajeId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones
        } finally {
            close();  // Cierra la base de datos después de realizar operaciones
        }

        return tatuajeId;
    }

    public long obtenerIdDelUsuarioActual(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // Obtén el ID del usuario almacenado en las preferencias
        long usuarioId = preferences.getLong("usuario_id", -1);

        if (usuarioId == -1) {
            // Si el ID del usuario no está almacenado, significa que el usuario no está autenticado
            mostrarToast(context, "Error: Usuario no autenticado");
        }

        return usuarioId;
    }

    public void actualizarIdUsuarioActual(Context context, long nuevoIdUsuario) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("usuario_id", nuevoIdUsuario);
        editor.apply();
    }

    public void mostrarToast(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }


    public Tatuaje obtenerTatuajePorId(long tatuajeId) {
        Tatuaje tatuaje = null;

        try {
            open();  // Abre la base de datos antes de realizar operaciones

            String[] projection = {
                    "id",
                    "nombre",
                    "imagen_resource_id",
                    "precio",
                    "descripcion",
                    "categoria"
            };

            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(tatuajeId)};

            Cursor cursor = database.query(
                    TABLE_TATUAJES,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String imagenResourceId = cursor.getString(cursor.getColumnIndexOrThrow("imagen_resource_id"));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));

                tatuaje = new Tatuaje(nombre, categoria, imagenResourceId, precio, descripcion);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones
        } finally {
            close();  // Cierra la base de datos después de realizar operaciones
        }

        return tatuaje;
    }

    public long agregarPedido(Pedido pedido, long usuarioId) {
        long idPedido = -1;

        try {
            open();  // Abre la base de datos antes de realizar operaciones

            // Comienza una transacción
            database.beginTransaction();

            // Inserta el pedido en la tabla de pedidos
            ContentValues values = new ContentValues();
            values.put("usuario_id", usuarioId);
            idPedido = database.insert(TABLE_PEDIDOS, null, values);

            if (idPedido != -1) {
                // Inserta los tatuajes asociados al pedido en la tabla de pedidos
                for (String nombreTatuaje : pedido.getNombresTatuajes()) {
                    long tatuajeId = obtenerIdDelTatuaje(nombreTatuaje);
                    ContentValues pedidoTatuajeValues = new ContentValues();
                    pedidoTatuajeValues.put("pedido_id", idPedido);
                    pedidoTatuajeValues.put("tatuaje_id", tatuajeId);
                    database.insert(TABLE_PEDIDOS, null, pedidoTatuajeValues);
                }

                // Establece la transacción como exitosa
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja excepciones según tus necesidades
        } finally {
            // Finaliza la transacción y cierra la base de datos
            database.endTransaction();
            close();  // Cierra la base de datos después de realizar operaciones
        }

        return idPedido;
    }


    public Cursor obtenerPedidosPorUsuario(long usuarioId) {
        try {
            open();  // Abre la base de datos antes de realizar operaciones
            SQLiteDatabase db = database;

            return db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE usuario_id = ?",
                    new String[]{String.valueOf(usuarioId)});
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja excepciones según tus necesidades
            return null;
        }
    }

    public void eliminarTodosTatuajesEnCarritoPorUsuario(long usuarioId) {
        try {
            open();  // Abre la base de datos antes de realizar operaciones
            SQLiteDatabase db = database;

            db.delete(TABLE_CARRITO, "usuario_id = ?", new String[]{String.valueOf(usuarioId)});
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja excepciones según tus necesidades
        } finally {
            close();  // Cierra la base de datos después de realizar operaciones
        }
    }

    public void eliminarTatuajeDelCarrito(long tatuajeId, long usuarioId) {
        try {
            open();  // Abre la base de datos antes de realizar operaciones
            SQLiteDatabase db = database;

            // Define la cláusula WHERE para seleccionar el tatuaje por su id y el usuario
            String selection = "tatuaje_id = ? AND usuario_id = ?";
            String[] selectionArgs = {String.valueOf(tatuajeId), String.valueOf(usuarioId)};

            // Elimina el tatuaje del carrito
            db.delete(TABLE_CARRITO, selection, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja excepciones según tus necesidades
        } finally {
            close();  // Cierra la base de datos después de realizar operaciones
        }
    }

    public List<Pedido> obtenerPedidosDelUsuario(long usuarioId) {
        List<Pedido> listaPedidos = new ArrayList<>();

        try {
            open();  // Abre la base de datos antes de realizar operaciones
            SQLiteDatabase db = database;

            String query = "SELECT " +
                    "p.pedido_id, " +
                    "GROUP_CONCAT(t.nombre) AS nombresTatuajes " +
                    "FROM " + TABLE_PEDIDOS + " p " +
                    "LEFT JOIN " + TABLE_PEDIDOS + " pt ON p.pedido_id = pt.pedido_id " +
                    "LEFT JOIN " + TABLE_TATUAJES + " t ON pt.tatuaje_id = t.id " +
                    "WHERE p.usuario_id = ? " +
                    "GROUP BY p.pedido_id";

            try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuarioId)})) {
                while (cursor.moveToNext()) {
                    Pedido pedido = new Pedido();
                    pedido.setNumeroPedido(cursor.getInt(cursor.getColumnIndexOrThrow("pedido_id")));

                    // Obtén la lista de nombres de tatuajes y agrégala al pedido
                    String nombresTatuajes = cursor.getString(cursor.getColumnIndexOrThrow("nombresTatuajes"));
                    List<String> listaNombresTatuajes = Arrays.asList(nombresTatuajes.split(","));
                    pedido.setNombresTatuajes(listaNombresTatuajes);

                    listaPedidos.add(pedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones
        } finally {
            close();  // Cierra la base de datos después de realizar operaciones
        }

        return listaPedidos;
    }


    private List<String> obtenerNombresTatuajesPorPedido(int pedidoId) {
        List<String> nombresTatuajes = new ArrayList<>();

        try {
            open();  // Abre la base de datos antes de realizar operaciones
            SQLiteDatabase db = database;

            String query = "SELECT t.nombre " +
                    "FROM " + TABLE_PEDIDOS + " p " +
                    "LEFT JOIN " + TABLE_PEDIDOS + " pt ON p.pedido_id = pt.pedido_id " +
                    "LEFT JOIN " + TABLE_TATUAJES + " t ON pt.tatuaje_id = t.id " +
                    "WHERE p.pedido_id = ?";

            try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(pedidoId)})) {
                while (cursor.moveToNext()) {
                    nombresTatuajes.add(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones
        } finally {
            close();  // Cierra la base de datos después de realizar operaciones
        }

        return nombresTatuajes;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Añade las condiciones para actualizar según la versión de la base de datos
        if (oldVersion < 2) {
            // Ejecuta las consultas de actualización necesarias
            db.execSQL("ALTER TABLE " + TABLE_REGISTRADOS + " ADD COLUMN nuevaColumna TEXT;");

            // Agrega la columna para el identificador único del carrito si no existe
            if (!columnExists(db, TABLE_CARRITO, "carrito_id")) {
                db.execSQL("ALTER TABLE " + TABLE_CARRITO + " ADD COLUMN carrito_id INTEGER;");
            }
        }

        // Siempre asegúrate de llamar al onCreate al final para recrear la tabla si es necesario
        onCreate(db);
    }

    // Método auxiliar para verificar si una columna existe en una tabla
    private boolean columnExists(SQLiteDatabase db, String tableName, String columnName) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String currentColumnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    if (columnName.equals(currentColumnName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public void open() throws SQLException {
        database = getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public void doSomeDatabaseOperation() {
        databaseLock.lock();
        try {

        } finally {
            databaseLock.unlock();
        }
    }
}