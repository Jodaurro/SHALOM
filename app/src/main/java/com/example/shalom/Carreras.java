package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Carreras extends AppCompatActivity {

    private static final int REQUEST_CODE_COMPRA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras);

        // Obtener el número de celular de la actividad anterior
        String cuel = getIntent().getStringExtra("cell2");

        // Configurar el botón "asphal" para abrir la actividad "Compra" con el juego Asphalt como parámetro
        ImageButton asphal = findViewById(R.id.asphal);
        asphal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.ASPHALT);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "nedd" para abrir la actividad "Compra" con el juego Need for Speed como parámetro
        ImageButton nedd = findViewById(R.id.needforspeed);
        nedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.NEDDFORSPEED);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "forza" para abrir la actividad "Compra" con el juego Forza Horizon como parámetro
        ImageButton forza = findViewById(R.id.needforza);
        forza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.FORZAHORIZON);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "moto" para abrir la actividad "Compra" con el juego MotoGP 22 como parámetro
        ImageButton moto = findViewById(R.id.motogp);
        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.MOTOGP22);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "atras" para finalizar la actividad y volver a la actividad anterior
        ImageButton atras = findViewById(R.id.atrascarrera);
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
