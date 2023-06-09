package com.example.shalom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    public SQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla "usuario" con las columnas especificadas
        db.execSQL("CREATE TABLE usuario(celular INTEGER PRIMARY KEY, nombre TEXT, apellido TEXT, correo TEXT, contraseña TEXT, saldo REAL, fecha TEXT)");

        // Crea la tabla "juego" con las columnas especificadas
        db.execSQL("CREATE TABLE juego(id INTEGER PRIMARY KEY, nombrejuego TEXT, proveedor TEXT, descripcion TEXT, precio REAL)");

        // Crea la tabla "historial" con las columnas especificadas
        db.execSQL("CREATE TABLE historial(id INTEGER PRIMARY KEY AUTOINCREMENT,celular INTEGER, fecha TEXT, nombrejuego TEXT, precio REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No se realiza ninguna acción al actualizar la base de datos
    }

    //Metodo para llamar saldo por medio de celular de la tabla usuario
    public double consultarsaldo(String celular) {
        SQLiteDatabase helper = this.getReadableDatabase();
        double saldo = 0;
        // Realiza una consulta a la tabla "usuario" para obtener el saldo del usuario correspondiente al número de celular
        Cursor cursor = helper.query("usuario", new String[]{"saldo"}, "celular = ?", new String[]{celular}, null, null, null);
        if (cursor.moveToFirst()) {
            saldo = cursor.getDouble(0);
        }
        cursor.close();
        return saldo;
    }

    //metodo para actualizar el saldo de un usuario por medio del celular
    public void actualizarsaldo(String celular, double nuevosaldo) {
        SQLiteDatabase helper = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("saldo", nuevosaldo);
        // Actualiza el saldo del usuario en la tabla "usuario" utilizando el número de celular como filtro
        helper.update("usuario", values, "celular = ?", new String[]{celular});
        helper.close();
    }

    //Metodo para llamar la fehca de registro de un usuario por medio del celular
    public String consulfecha(String celular) {
        SQLiteDatabase helper = this.getReadableDatabase();
        String fecha = "";
        // Realiza una consulta a la tabla "usuario" para obtener la fecha correspondiente al número de celular
        Cursor cursor = helper.query("usuario", new String[]{"fecha"}, "celular = ?", new String[]{celular}, null, null, null);
        while (cursor.moveToNext()) {
            fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
        }
        cursor.close();
        return fecha;
    }

    //Metodo para actualizar el saldo de ls usuarios al cambiar el dia de todos
    public void actualizarSaldoUsuarios() {
        SQLiteDatabase db = this.getWritableDatabase();
        double nuevoSaldo = 1000000;
        ContentValues values = new ContentValues();
        values.put("saldo", nuevoSaldo);
        // Actualiza el saldo de todos los usuarios en la tabla "usuario"
        db.update("usuario", values, null, null);
    }
}
