package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Historial extends AppCompatActivity {

    private ListView history;
    private ImageButton atras;
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        helper = new SQLiteOpenHelper(this, "shalom", null, 1);

        history = findViewById(R.id.listahistori);
        vistahistorial();
        atras = findViewById(R.id.atrashistori);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void vistahistorial() {
        String celuhistori = getIntent().getStringExtra("cell2");
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM historial WHERE celular= " + celuhistori + " ORDER BY fecha DESC ", null);


        List<String> datos = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {

            do {
                String fecharegistro = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                String juegonombre = cursor.getString(cursor.getColumnIndexOrThrow("nombrejuego"));
                double juegoprecio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"));
                DecimalFormat formato = new DecimalFormat("#,###.##");
                String numero = formato.format(juegoprecio);
                String registrocompra ="\n" + "Fecha: " + fecharegistro + "\n" + "Nombre Juego: " + juegonombre + "\n" + "Precio: " + numero + "\n";

                datos.add(registrocompra);

            }while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        header.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        history.addHeaderView(header);

        history.setAdapter(adapter);
        history.setDivider(getResources().getDrawable(R.drawable.list_divider));
        history.setDividerHeight(10);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}