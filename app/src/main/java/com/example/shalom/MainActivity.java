package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "shalom"; // Nombre de la base de datos
    private static final int DATABASE_VERSION = 1; // Versión de la base de datos
    private SQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear una tarea que se ejecutará después de 2 segundos
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                // Crear un intent para iniciar la actividad de inicio de sesión
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 2000);

        // Inicializar el objeto SQLiteOpenHelper para acceder a la base de datos
        helper = new SQLiteOpenHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Verificar si el juego con ID  existe en la base de datos
        if (!existeJuego(db, 1)) {
            // Si no existe, insertar un nuevo juego en la tabla "j
            // uego"
            insertarJuego(db, 1, "Asphalt 9: Legends", "Steam", "Producción: Gameloft\n" +
                    "Desarrollador: Gameloft\n" +
                    "Jugadores: 1-Online\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: -\n" +
                    "Online: Sí", 120000);
        }
        if (!existeJuego(db, 2)) {
            insertarJuego(db, 2, "Need for Speed: Most Wanted", "Electronic Arts", "Producción: Electronic Arts\n" +
                    "Desarrollador: Criterion Games\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Español\n" +
                    "Online: Sí", 130000);
            }
        if (!existeJuego(db, 3)) {
            insertarJuego(db, 3, "Forza Horizon 5", "Steam", "Producción: Xbox Game Studios\n" +
                    "Desarrollador: Playground Games\n" +
                    "Jugadores: 1-Online\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Español\n" +
                    "Online: Sí", 170000);
        }
        if (!existeJuego(db, 4)) {
            insertarJuego(db, 4, "MotoGP 22", "Steam", "Producción: Milestone S.r.l.\n" +
                    "Desarrollador: Milestone S.r.l.\n" +
                    "Jugadores: 1-2\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Español\n" +
                    "Online: Sí", 110000);
        }
        if (!existeJuego(db, 5)) {
            insertarJuego(db, 5, "FIFA 19", "Electronic Arts", "Producción: EA Sports\n" +
                    "Desarrollador: EA Sports\n" +
                    "Jugadores: 1-4\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Español\n" +
                    "Online: Sí", 150000);
        }
        if (!existeJuego(db, 6)) {
            insertarJuego(db, 6, "NBA 2K23", "Steam", "Producción: 2K\n" +
                    "Desarrollador: Visual Concepts\n" +
                    "Jugadores: 1-4\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Inglés\n" +
                    "Online: Sí", 160000);
        }
        if (!existeJuego(db, 7)) {
            insertarJuego(db, 7, "WWE 2K23", "Take Two", "Producción: 2K\n" +
                    "Desarrollador: Visual Concepts\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Español\n" +
                    "Online: No", 135000);
        }
        if (!existeJuego(db, 8)) {
            insertarJuego(db, 8, "Tennis World Tour 2", "Nacon", "Producción: Nacon\n" +
                    "Desarrollador: Big Ant Studios\n" +
                    "Jugadores: 1-4\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Inglés\n" +
                    "Online: Sí", 145000);
        }
        if (!existeJuego(db, 9)) {
            insertarJuego(db, 9, "Tetris: The Grand Master", "PlayStation Store", "Producción: HAMSTER CORPORATION\n" +
                    "Desarrollador: Arika\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: -\n" +
                    "Online: NO", 125000);
        }
        if (!existeJuego(db, 10)) {
            insertarJuego(db, 10, "Puzzle Bobble Everybubble!", "Meridiem Games", "Producción: ININ Games\n" +
                    "Desarrollador: ININ Games\n" +
                    "Jugadores: 1-4\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: -\n" +
                    "Online: Sí", 115000);
        }
        if (!existeJuego(db, 11)) {
            insertarJuego(db, 11, "Picross S8", "eShop", "Producción: JUPITER\n" +
                    "Desarrollador: JUPITER\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: -\n" +
                    "Online: NO", 105000);
        }
        if (!existeJuego(db, 12)) {
            insertarJuego(db, 12, "Puzzle & Dragons", "eShop", "Producción: GungHo Online Entertainment\n" +
                    "Desarrollador: GungHo Online Entertainment\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: -\n" +
                    "Online: NO", 100000);
        }
        if (!existeJuego(db, 13)) {
            insertarJuego(db, 13, "Minecraft Legends", "Steam", "Producción: Xbox Game Studios\n" +
                    "Desarrollador: Mojang Studios / Blackbird Interactive\n" +
                    "Jugadores: 1-Online\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Español\n" +
                    "Online: Sí", 140000);
        }
        if (!existeJuego(db, 14)) {
            insertarJuego(db, 14, "Age of Wonders 4", "Steam", "Producción: Paradox Interactive\n" +
                    "Desarrollador: Triumph Studios\n" +
                    "Jugadores: 1-Online\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Inglés\n" +
                    "Online: Sí", 155000);
        }
        if (!existeJuego(db, 15)) {
            insertarJuego(db, 15, "Tactics Ogre: Reborn", "PlayStation Store", "Producción: Square Enix\n" +
                    "Desarrollador: Square Enix\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Español\n" +
                    "Voces: Inglés\n" +
                    "Online: -", 165000);
        }
        if (!existeJuego(db, 16)) {
            insertarJuego(db, 16, "Divinity: Original Sin II", "Steam", "Producción: Larian Studios\n" +
                    "Desarrollador: Larian Studios\n" +
                    "Jugadores: 1\n" +
                    "Formato: Descarga\n" +
                    "Textos: Inglés\n" +
                    "Voces: Inglés\n" +
                    "Online: Sí", 175000);
        }
        db.close(); // Cerrar la conexión con la base de datos
    }

    // Verificar si un juego con el ID dado existe en la base de datos por medio del id
    private boolean existeJuego(SQLiteDatabase db, int id) {
        String[] projection = {"id"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String limit = "1";
        Cursor cursor = db.query("juego", projection, selection, selectionArgs, null, null, null, limit);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Insertar un nuevo juego en la tabla "juego"
    private void insertarJuego(SQLiteDatabase db, int id, String nombre, String proveedor, String descripcion, int precio) {
        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("nombrejuego", nombre);
        registro.put("proveedor", proveedor);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        db.insert("juego", null, registro);
    }
}