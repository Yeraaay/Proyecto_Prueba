package com.example.proyectotatto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class registradoFragmentCartas extends Fragment {

    TextView tvTatuajes;
    DBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registrado_cartas, container, false);

        tvTatuajes = root.findViewById(R.id.textViewTatuajes);

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String queryTodos = "SELECT * FROM " + DBHelper.TABLE_TATUAJES;
        Cursor cursor = db.rawQuery(queryTodos, null);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                byte[] imagenEnBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("imagen_resource_id"));

                Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagenEnBytes, 0, imagenEnBytes.length);
                Drawable drawable = new BitmapDrawable(getResources(), imagenBitmap);

                // Agregar el nombre al builder
                stringBuilder.append("Nombre: ").append(nombre).append("\n");

                // Agregar la imagen al builder
                if (imagenBitmap != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    SpannableString spannableString = new SpannableString("Imagen: ");
                    spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    stringBuilder.append(spannableString).append("\n");
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        tvTatuajes.setText(stringBuilder);

        return root;
    }

}
