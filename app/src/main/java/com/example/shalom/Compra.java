package com.example.shalom;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Compra extends AppCompatActivity {

    private String key = "";
    private SQLiteOpenHelper helper;
    private String pre;
    private SQLiteDatabase db;
    private ImageButton atras;
    private final Map<String, Integer> juegoIdMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        // Obtener referencias a los elementos de la interfaz de usuario
        TextView texto = findViewById(R.id.textoa);
        TextView precio = findViewById(R.id.textob);
        helper = new SQLiteOpenHelper(this, "shalom", null, 1);
        db = helper.getReadableDatabase();
        key = getIntent().getStringExtra("key");

        // Inicializar el mapeo de nombres de juego a IDs de juego
        initializeJuegoIdMap();

        // Obtener el ID del juego seleccionado
        int juegoId = juegoIdMap.containsKey(key) ? juegoIdMap.get(key) : -1;
        if (juegoId != 0) {
            // Consultar los detalles del juego en la base de datos
            String query = "SELECT * FROM juego WHERE id = " + juegoId;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToNext()) {
                String nom = cursor.getString(1);
                String pro = cursor.getString(2);
                String des = cursor.getString(3);
                pre = cursor.getString(4);

                // Mostrar los detalles del juego en la interfaz de usuario
                texto.setText("\nNOMBRE: " + nom + "\n\n" + "PROVEEDOR: " + pro + "\n\n" + "DESCRIPCION: \n" + des);
                precio.setText("Precio: " + pre);
            }
        }

        // Configurar el botón "compra" para mostrar el diálogo de confirmación
        Button res = findViewById(R.id.compra);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarConfirmacionDialog();
            }
        });

        atras = findViewById(R.id.atrascompra);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // Inicializar el mapeo de nombres de juego a IDs de juego
    private void initializeJuegoIdMap() {
        juegoIdMap.put(Constans.ASPHALT, 1);
        juegoIdMap.put(Constans.NEDDFORSPEED, 2);
        juegoIdMap.put(Constans.FORZAHORIZON, 3);
        juegoIdMap.put(Constans.MOTOGP22, 4);
        juegoIdMap.put(Constans.MINECRAFT, 13);
        juegoIdMap.put(Constans.AGEOFWONDER, 14);
        juegoIdMap.put(Constans.TACTISOGRE, 15);
        juegoIdMap.put(Constans.DIVINITY, 16);
        juegoIdMap.put(Constans.TETRIX, 9);
        juegoIdMap.put(Constans.BOBBLE, 10);
        juegoIdMap.put(Constans.PICROSS, 11);
        juegoIdMap.put(Constans.DRAGON, 12);
        juegoIdMap.put(Constans.FIFA19, 5);
        juegoIdMap.put(Constans.NBA2K23, 6);
        juegoIdMap.put(Constans.W2K23, 7);
        juegoIdMap.put(Constans.TENIS, 8);
    }

    public void comprar() {

        // Obtener el número de celular de la actividad anterior
        String cel = getIntent().getStringExtra("cuel");

        // Obtener la fecha y hora actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // Insertar los detalles de la compra en la tabla "historial"
        ContentValues regis = new ContentValues();
        regis.put("celular", cel);
        regis.put("fecha", currentDate);
        regis.put("nombrejuego", key);
        regis.put("precio", pre);
        db.insert("historial", null, regis);
        db.close();

        // Realizar la transacción de compra
        double preciojuego = Double.parseDouble(pre);
        double saldousuario = helper.consultarsaldo(cel);
        if (saldousuario < preciojuego) {
            Toast.makeText(this, "SALDO INSUFICIENTE", Toast.LENGTH_SHORT).show();
            return;
        }
        double saldorestante = saldousuario - preciojuego;

        helper.actualizarsaldo(cel, saldorestante);

        Toast.makeText(this, "COMPRA EXITOSA", Toast.LENGTH_SHORT).show();

        // Informar a la actividad anterior que se debe actualizar el saldo
        Intent intent = new Intent();
        intent.putExtra("actualizarSaldo", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void mostrarConfirmacionDialog() {
        // Mostrar un diálogo de confirmación antes de realizar la compra
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de hacer la compra?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comprar();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}