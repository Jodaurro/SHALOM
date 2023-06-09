package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Estrategia extends AppCompatActivity {

    private ImageButton mine;
    private ImageButton age;
    private ImageButton tactic;
    private ImageButton divi;
    private ImageButton atras;
    private static final int REQUEST_CODE_COMPRA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estrategia);

        // Obtener el número de celular de la actividad anterior
        String cuel = getIntent().getStringExtra("cell2");

        // Configurar el botón "mine" para abrir la actividad "Compra" con el juego Minecraft como parámetro
        mine = findViewById(R.id.mine);
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.MINECRAFT);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "age" para abrir la actividad "Compra" con el juego Age of Wonder como parámetro
        age = findViewById(R.id.age);
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.AGEOFWONDER);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "tactic" para abrir la actividad "Compra" con el juego Tactics Ogre como parámetro
        tactic = findViewById(R.id.tactic);
        tactic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.TACTISOGRE);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "divi" para abrir la actividad "Compra" con el juego Divinity como parámetro
        divi = findViewById(R.id.divinity);
        divi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Compra.class);
                intent.putExtra("key", Constans.DIVINITY);
                intent.putExtra("cuel", cuel);
                startActivityForResult(intent, REQUEST_CODE_COMPRA);
            }
        });

        // Configurar el botón "atras" para finalizar la actividad y volver a la actividad anterior
        atras = findViewById(R.id.atrasestrategia);
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
