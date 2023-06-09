package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InterfazPrincipal extends AppCompatActivity {

    private TextView usuario;
    private ImageButton carreras;
    private ImageButton estrategia;
    private ImageButton deportes;
    private ImageButton puzzles;
    private ImageButton historial;
    private ImageButton lougout;
    private SQLiteOpenHelper helper;
    private static final int REQUEST_CODE_COMPRA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_principal);

        // Inicialización de SQLiteOpenHelper
        helper = new SQLiteOpenHelper(this, "shalom", null, 1);

        // Obtener el número de celular del intent
        String cell2 = getIntent().getStringExtra("cell");

        SQLiteDatabase db = helper.getReadableDatabase();
        String fec = String.valueOf(helper.consulfecha(cell2));

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fecha = dateFormat.format(calendar.getTime());

        // Comprobar si la fecha actual es diferente a la fecha almacenada en la base de datos
        if (!fecha.equals(fec)) {
            // Actualizar el saldo de todos los usuarios en la tabla "usuario"
            helper.actualizarSaldoUsuarios();

            // Actualizar la fecha en la tabla "usuario"
            ContentValues regis = new ContentValues();
            regis.put("fecha", fecha);
            db.update("usuario", regis, null, null);
            db.close();
        }

        // Obtener los datos del nombre del usuario
        Intent intent = getIntent();
        ArrayList<String> datosnombre = intent.getStringArrayListExtra("datosnombre");
        usuario = findViewById(R.id.vistanombre);
        StringBuilder stringBuilder = new StringBuilder();
        for (String fila : datosnombre) {
            stringBuilder.append(fila).append("\n");
        }
        usuario.setText(stringBuilder.toString());

        // Actualizar el saldo del usuario en la vista
        actualizarSaldo(cell2);

        // Configurar el botón "carreras" para abrir la actividad "Carreras"

        carreras = findViewById(R.id.carrera);
        carreras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActividad(Carreras.class, cell2);
            }
        });

        // Configurar el botón "estrategia" para abrir la actividad "Estrategia"
        estrategia = findViewById(R.id.estrategia);
        estrategia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActividad(Estrategia.class, cell2);
            }
        });

        // Configurar el botón "deportes" para abrir la actividad "Deportes"
        deportes = findViewById(R.id.deportes);
        deportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActividad(Deportes.class, cell2);
            }
        });

        // Configurar el botón "puzzles" para abrir la actividad "Puzzles"
        puzzles = findViewById(R.id.puzzles);
        puzzles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActividad(Puzzles.class, cell2);
            }
        });

        // Configurar el botón "historial" para abrir la actividad "Historial"
        historial = findViewById(R.id.histori);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pase = new Intent(InterfazPrincipal.this, Historial.class);
                pase.putExtra("cell2", cell2);
                startActivity(pase);
            }
        });

        lougout = findViewById(R.id.cerrarcesion);
        lougout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarConfirmacionDialog();

            }
        });

    }

    // Método para actualizar el saldo del usuario en la vista
    private void actualizarSaldo(String cell) {
        double saldos = helper.consultarsaldo(cell);
        DecimalFormat formato = new DecimalFormat("#,###.##");
        String numero = formato.format(saldos);
        usuario = findViewById(R.id.vistasaldo);
        usuario.setText(numero);
    }

    // Método para abrir una actividad y pasar el número de celular como parámetro
    private void abrirActividad(Class<?> cls, String cell2) {
        Intent pase = new Intent(InterfazPrincipal.this, cls);
        pase.putExtra("cell2", cell2);
        startActivityForResult(pase, REQUEST_CODE_COMPRA);
    }

    private void cerrarsecion(){
        Intent cerrar = new Intent(this, Ingresar.class);
        startActivity(cerrar);
        finish();
    }

    private void mostrarConfirmacionDialog() {
        // Mostrar un diálogo de confirmación antes de realizar la compra
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de cerrar secion?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarsecion();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMPRA && resultCode == RESULT_OK && data != null) {
            boolean actualizarSaldo = data.getBooleanExtra("actualizarSaldo", false);
            if (actualizarSaldo) {
                String cell2 = getIntent().getStringExtra("cell");
                actualizarSaldo(cell2);
            }
        }
    }

    @Override
    public void onBackPressed() {mostrarConfirmacionDialog();}
}
