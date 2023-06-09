package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Puzzles extends AppCompatActivity {

    private ImageButton tetrix;
    private ImageButton bubble;
    private ImageButton picro;
    private ImageButton dragon;
    private ImageButton atras;
    private static final int REQUEST_CODE_COMPRA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles);

        // Obtener el número de celular de la actividad anterior
        String cuel = getIntent().getStringExtra("cell2");

        // Configurar el botón "tetrix" para abrir la actividad "Compra" con el juego Tetrix como parámetro
        tetrix = findViewById(R.id.tetrix);
        tetrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.TETRIX);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "bubble" para abrir la actividad "Compra" con el juego Bubble como parámetro
        bubble = findViewById(R.id.bobble);
        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.BOBBLE);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "picro" para abrir la actividad "Compra" con el juego Picross como parámetro
        picro = findViewById(R.id.picross);
        picro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.PICROSS);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "dragon" para abrir la actividad "Compra" con el juego Dragon como parámetro
        dragon = findViewById(R.id.dragon);
        dragon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.DRAGON);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "atras" para finalizar la actividad y volver a la actividad anterior
        atras = findViewById(R.id.atraspuzzles);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Propagar el resultado de la actividad "Compra" a la actividad anterior y finalizar la actividad actual
        if (requestCode == REQUEST_CODE_COMPRA) {
            setResult(resultCode, data);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
