package com.unicauca.citasmed.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.unicauca.citasmed.modelo.Paciente;

public class DbUsuarios extends DbHelper {

    Context context;

    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long InsertarUsuario(int id_paciente, String nombre,
                                String usuario, String correo, String password ){
        long id = 0;

        try {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_paciente", id_paciente);
        values.put("nombre", nombre);
        values.put("usuario", usuario);
        values.put("correo", correo);
        values.put("password", password);

        id = db.insert(TABLE_USUARIOS, null, values);

        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public Paciente LeerUsuarios(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Paciente pacienteLogueado = null;
        Cursor cursor;

       cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);

       if (cursor.moveToFirst()){
           pacienteLogueado = new Paciente();
           pacienteLogueado.setId_paciente(cursor.getInt(0));
           pacienteLogueado.setNombre(cursor.getString(1));
           pacienteLogueado.setUsuario(cursor.getString(2));
           pacienteLogueado.setCorreo(cursor.getString(3));
           pacienteLogueado.setPassword(cursor.getString(4));
       }

       cursor.close();

       return pacienteLogueado;
    }

    public void borrarDatos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_USUARIOS);
    }

}

