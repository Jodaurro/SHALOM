package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

public class Ingresar extends AppCompatActivity {

    private EditText celular;
    private EditText contrasena;
    private Button ingresar;
    private SQLiteOpenHelper helper;
    private ImageButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        // Obtener referencias a los elementos de la interfaz
        celular = findViewById(R.id.celular);
        contrasena = findViewById(R.id.clave);

        // Crear una instancia de SQLiteOpenHelper para administrar la base de datos
        helper = new SQLiteOpenHelper(this, "shalom", null, 1);

        atras = findViewById(R.id.atrasingresar);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener referencia al botón de ingresar
        ingresar = findViewById(R.id.ingre);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los campos de texto
                String cell = celular.getText().toString();
                String pass = contrasena.getText().toString();

                List<EditText> campos = new ArrayList<>();

                // Validar que los campos no estén vacíos
                if (cell.isEmpty()) {
                    celular.setError("RELLENE LOS CAMPOS, NO PUEDEN ESTAR VACÍOS");
                    return;
                }
                if (pass.isEmpty()) {
                    contrasena.setError("RELLENE LOS CAMPOS, NO PUEDEN ESTAR VACÍOS");
                    return;
                }

                // Verificar las credenciales de inicio de sesión
                if (!login(cell, pass)) {
                    celular.setError("CELULAR INCORRECTO");
                    contrasena.setError("CONTRASEÑA INCORRECTA");
                    return;
                }

                // Validar la longitud del campo "celular"
                if (cell.length() < 10) {
                    celular.setError("EL CAMPO 'CELULAR' DEBE TENER AL MENOS 10 NÚMEROS");
                    return;
                }

                // Validar la complejidad de la contraseña
                boolean tieneLongitudSuficiente = pass.length() >= 8;
                boolean tieneLetraMayuscula = pass.matches(".*[A-Z].*");
                boolean tieneLetraMinuscula = pass.matches(".*[a-z].*");
                boolean tieneNumero = pass.matches(".*\\d.*");
                boolean tieneCaracterEspecial = pass.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
                boolean tieneEspacios = pass.contains(" ");

                if (!tieneLongitudSuficiente || !tieneLetraMayuscula || !tieneLetraMinuscula || !tieneNumero || !tieneCaracterEspecial || tieneEspacios) {
                    if (!tieneLongitudSuficiente) {
                        contrasena.setError("LA CONTRASEÑA DEBE TENER AL MENOS 8 CARACTERES");
                    }
                    if (!tieneLetraMayuscula) {
                        contrasena.setError("LA CONTRASEÑA DEBE CONTENER AL MENOS UNA LETRA MAYÚSCULA");
                    }
                    if (!tieneLetraMinuscula) {
                        contrasena.setError("LA CONTRASEÑA DEBE CONTENER AL MENOS UNA LETRA MINÚSCULA");
                    }
                    if (!tieneNumero) {
                        contrasena.setError("LA CONTRASEÑA DEBE CONTENER AL MENOS UN NÚMERO");
                    }
                    if (!tieneCaracterEspecial) {
                        contrasena.setError("LA CONTRASEÑA DEBE CONTENER AL MENOS UN CARÁCTER ESPECIAL");
                    }
                    if (tieneEspacios) {
                        contrasena.setError("LA CONTRASEÑA NO DEBE CONTENER ESPACIOS");
                    }
                    return;
                }

                // Obtener una instancia de SQLiteDatabase para consultar la base de datos
                SQLiteDatabase db = helper.getReadableDatabase();

                // Realizar una consulta para obtener el nombre y apellido del usuario
                String traer = "SELECT nombre, apellido FROM usuario where celular = " + cell;
                Cursor cursor = db.rawQuery(traer, null);

                // Almacenar los datos del usuario en un ArrayList
                ArrayList<String> datosnombre = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                    datosnombre.add("Bienvenido \n" + nombre + " " + apellido);
                }
                cursor.close();

                // Iniciar la actividad de InterfazPrincipal y pasar los datos necesarios
                Intent intent = new Intent(Ingresar.this, InterfazPrincipal.class);
                intent.putStringArrayListExtra("datosnombre", datosnombre);
                intent.putExtra("cell", cell);
                startActivity(intent);
                finish();
            }
        });
    }

    // Verificar las credenciales de inicio de sesión en la base de datos
    private boolean login(String celular, String contraseña) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {"celular", "contraseña"};
        String selection = "celular = ? AND contraseña = ?";
        String[] selectionArgs = {celular, contraseña};

        Cursor cursor = db.query(
                "usuario",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isLogin = cursor.getCount() > 0;
        cursor.close();

        return isLogin;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}

