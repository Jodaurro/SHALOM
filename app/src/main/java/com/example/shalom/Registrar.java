package com.example.shalom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {
    private EditText nombres;
    private EditText apellidos;
    private EditText celulars;
    private EditText correos;
    private EditText contraseñas;
    private EditText confirmars;
    private Button registrar;
    private ImageButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Obtener referencias a los elementos de la interfaz
        nombres = findViewById(R.id.nom);
        apellidos = findViewById(R.id.ape);
        celulars = findViewById(R.id.celu);
        correos = findViewById(R.id.correo);
        contraseñas = findViewById(R.id.pass);
        confirmars = findViewById(R.id.comf);

        atras = findViewById(R.id.atrasregistro);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener referencia al botón de registrar
        registrar = findViewById(R.id.regis);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los campos de texto
                String nombre = nombres.getText().toString().trim();
                String apellido = apellidos.getText().toString().trim();
                String celular = celulars.getText().toString();
                String correo = correos.getText().toString();
                String contraseña = contraseñas.getText().toString();
                String confirmar = confirmars.getText().toString();

                List<EditText> camposVacios = new ArrayList<>(); //ARAY PARA QUE ME LEA TODA LA LISTA AL MISMO TIEMPO EL SET ERROR

                //VERIFICA CAMPOS VACIOS
                if (nombre.isEmpty()) {
                    nombres.setError("Ingrese su nombre");
                    camposVacios.add(nombres);//se guardan datos en el array
                }

                if (apellido.isEmpty()) {
                    apellidos.setError("Ingrese su apellido");
                    camposVacios.add(apellidos);
                }

                if (celular.isEmpty()) {
                    celulars.setError("Ingrese su número de celular");
                    camposVacios.add(celulars);
                }

                if (correo.isEmpty()) {
                    correos.setError("Ingrese su correo electrónico");
                    camposVacios.add(correos);
                }

                if (contraseña.isEmpty()) {
                    contraseñas.setError("Ingrese su contraseña");
                    camposVacios.add(contraseñas);
                }

                if (confirmar.isEmpty()) {
                    confirmars.setError("Confirme su contraseña");
                    camposVacios.add(confirmars);
                }
                // Validar la longitud del número de celular
                if (celular.length() != 10) {
                    celulars.setError("El número de celular debe tener 10 dígitos");
                    camposVacios.add(celulars);
                }
                // Validar el formato del correo electrónico
                if (!valcorreo(correo)) {
                    correos.setError("Correo electrónico inválido");
                    camposVacios.add(correos);
                }
                if (!camposVacios.isEmpty()) {
                    camposVacios.get(0).requestFocus(); // Enfocar el primer campo vacío Y LONGITUD DEL NUMERO CELULAR Y LA VALIDACION DEL CORREO
                    // Aquí puedes mostrar un mensaje general de error o realizar alguna otra acción necesaria
                    return;
                }

                // Verificar si el número de celular o el correo ya están registrados
                int campoRegistrado = existeUsuario(celular, correo);
                if (campoRegistrado == 1) {
                    celulars.setError("El número de celular ya está registrado");
                } else if (campoRegistrado == 2) {
                    correos.setError("El correo electrónico ya está registrado");
                } else {
                    // Validar la complejidad de la contraseña
                    boolean tieneLongitudSuficiente = contraseña.length() >= 8;
                    boolean tieneLetraMayuscula = contraseña.matches(".*[A-Z].*");
                    boolean tieneLetraMinuscula = contraseña.matches(".*[a-z].*");
                    boolean tieneNumero = contraseña.matches(".*\\d.*");
                    boolean tieneCaracterEspecial = contraseña.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
                    boolean tieneEspacio = contraseña.matches(".*\\s.*");

                    if (!tieneLongitudSuficiente || !tieneLetraMayuscula || !tieneLetraMinuscula || !tieneNumero || !tieneCaracterEspecial || tieneEspacio) {
                        // Mostrar mensajes de error según los criterios de validación
                        if (!tieneLongitudSuficiente) {
                            contraseñas.setError("La contraseña debe tener al menos 8 caracteres");
                        }
                        if (!tieneLetraMayuscula) {
                            contraseñas.setError("La contraseña debe contener al menos una letra mayúscula");
                        }
                        if (!tieneLetraMinuscula) {
                            contraseñas.setError("La contraseña debe contener al menos una letra minúscula");
                        }
                        if (!tieneNumero) {
                            contraseñas.setError("La contraseña debe contener al menos un número");
                        }
                        if (!tieneCaracterEspecial) {
                            contraseñas.setError("La contraseña debe contener al menos un carácter especial");
                        }
                        if (tieneEspacio) {
                            contraseñas.setError("La contraseña no puede contener espacios");
                        }
                        return;
                    }

                    // Verificar que la contraseña y la confirmación coincidan
                    if (!contraseña.equals(confirmar)) {
                        confirmars.setError("La contraseña y la confirmación no coinciden");
                        return;
                    }

                    // Crear una instancia de SQLiteOpenHelper para interactuar con la base de datos
                    SQLiteOpenHelper helper = new SQLiteOpenHelper(getApplicationContext(), "shalom", null, 1);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    // Obtener la fecha actual
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = dateFormat.format(calendar.getTime());

                    // Crear un objeto ContentValues para insertar los valores en la tabla
                    ContentValues registro = new ContentValues();
                    registro.put("celular", celular);
                    registro.put("nombre", nombre);
                    registro.put("apellido", apellido);
                    registro.put("correo", correo);
                    registro.put("contraseña", contraseña);
                    registro.put("saldo", 1000000);
                    registro.put("fecha", currentDate);

                    // Insertar el registro en la tabla "usuario"
                    db.insert("usuario", null, registro);
                    db.close();

                    // Limpiar los campos de texto y mostrar un mensaje de éxito
                    nombres.setText("");
                    celulars.setText("");
                    contraseñas.setText("");
                    correos.setText("");
                    apellidos.setText("");
                    nombres.setError(null);
                    celulars.setError(null);
                    contraseñas.setError(null);
                    correos.setError(null);
                    apellidos.setError(null);
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(Registrar.this, "REGISTRO EXITOSO", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    // Validar el formato del correo electrónico
    private boolean valcorreo(String email) {
        // Verificar espacios en blanco
        if (email.contains(" ")) {
            return false; // Si contiene espacios en blanco, retorna false
        }

        // Validación con expresiones regulares
        String pattern = "^[A-Za-z0-9_.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern regex = Pattern.compile(pattern);
        return regex.matcher(email).matches();
    }

    // Verificar si el número de celular o el correo ya están registrados en la base de datos
    private int existeUsuario(String celular, String correo) {
        SQLiteOpenHelper helper = new SQLiteOpenHelper(getApplicationContext(), "shalom", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                "celular",
                "correo"
        };

        String selection = "celular = ?";
        String[] selectionArgs = {celular};

        // Consultar registros en la tabla "usuario" que tengan el número de celular especificado
        Cursor cursor = db.query(
                "usuario",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean celularRegistrado = cursor.getCount() > 0;
        cursor.close();
        db.close();

        if (celularRegistrado) {
            return 1; // El número de celular está registrado
        } else {
            // Si el número de celular no está registrado, verificar el correo electrónico
            helper = new SQLiteOpenHelper(getApplicationContext(), "shalom", null, 1);
            db = helper.getReadableDatabase();

            selection = "correo = ?";
            selectionArgs = new String[]{correo};

            // Consultar registros en la tabla "usuario" que tengan el correo electrónico especificado
            cursor = db.query(
                    "usuario",
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            boolean correoRegistrado = cursor.getCount() > 0;
            cursor.close();
            db.close();

            if (correoRegistrado) {
                return 2; // El correo electrónico está registrado
            } else {
                return 0; // Ninguno de los dos está registrado
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}