package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Deportes extends AppCompatActivity {

    private ImageButton fifa;
    private ImageButton nba;
    private ImageButton wwe;
    private ImageButton tenis;
    private ImageButton atras;
    private static final int REQUEST_CODE_COMPRA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deportes);

        // Obtener el número de celular de la actividad anterior
        String cuel = getIntent().getStringExtra("cell2");

        // Configurar el botón "fifa" para abrir la actividad "Compra" con el juego FIFA 19 como parámetro
        fifa = findViewById(R.id.fifa19);
        fifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.FIFA19);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "nba" para abrir la actividad "Compra" con el juego NBA 2K23 como parámetro
        nba = findViewById(R.id.nba);
        nba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.NBA2K23);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "wwe" para abrir la actividad "Compra" con el juego WWE 2K23 como parámetro
        wwe = findViewById(R.id.wwe);
        wwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.W2K23);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "tenis" para abrir la actividad "Compra" con el juego de tenis como parámetro
        tenis = findViewById(R.id.tenis);
        tenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.TENIS);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "atras" para finalizar la actividad y volver a la actividad anterior
        atras = findViewById(R.id.atrasdeportes);
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
