package com.example.ivalion.colegio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;


public class myDBAdapter {

    // Definiciones y constantes
    private static final String DATABASE_NAME = "dbcole.db";
    private static final int DATABASE_VERSION = 1;

    //tablas
    private static final String DATABASE_TABLE_ESTU = "Estudiantes";
    private static final String DATABASE_TABLE_PROF = "Profesores";

    //columnas
    private static final String NOMBRE = "nombre";
    private static final String CICLO = "ciclo";
    private static final String CURSO = "curso";
    private static final String NOTA = "nota";
    private static final String DESPACHO = "despacho";
    private static final String EDAD = "edad";


    //instrucciones para crear y borrar las tablas de estudiantes y profesores
    private static final String DATABASE_CREATE_ESTU = "CREATE TABLE " + DATABASE_TABLE_ESTU + " (_id integer primary key autoincrement, nombre text, ciclo text, curso text, nota float, edad integer);";
    private static final String DATABASE_DROP_ESTU = "DROP TABLE IF EXISTS " + DATABASE_TABLE_ESTU + ";";

    private static final String DATABASE_CREATE_PROF = "CREATE TABLE " + DATABASE_TABLE_PROF + " (_id integer primary key autoincrement, nombre text, ciclo text, curso text, despacho text, edad integer);";
    private static final String DATABASE_DROP_PROF = "DROP TABLE IF EXISTS " + DATABASE_TABLE_PROF + ";";


    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    public myDBAdapter(Context c) {
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {

        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = dbHelper.getReadableDatabase();
        }

    }

    public void insertarEstudiante(String nom, String cic, String cur, float not, int ed) {

        ContentValues newValues = new ContentValues();

        //Asignamos los valores de cada campo
        newValues.put(NOMBRE, nom);
        newValues.put(CICLO, cic);
        newValues.put(CURSO, cur);
        newValues.put(NOTA, not);
        newValues.put(EDAD, ed);
        db.insert(DATABASE_TABLE_ESTU, null, newValues);
    }

    public Cursor getEstudiantes() {
        Cursor c;
        c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_ESTU, null);
        return c;
    }

    public boolean deleteEstudiante(int id) {

        //al borrar solo un unico id, el metodo delete debe devolver 1
        if (db.delete(DATABASE_TABLE_ESTU, "_id=" + id, null) == 1)
            return true;
        else
            return false;
    }


    public void insertarProfesores(String nom, String cic, String cur, String des, int ed) {

        ContentValues newValues = new ContentValues();

        newValues.put(NOMBRE, nom);
        newValues.put(CICLO, cic);
        newValues.put(CURSO, cur);
        newValues.put(DESPACHO, des);
        newValues.put(EDAD, ed);
        db.insert(DATABASE_TABLE_PROF, null, newValues);
    }

    public Cursor getProfesores() {
        Cursor c;
        c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_PROF, null);
        return c;
    }

    public void vaciarTabla(String tabla, View view) {
        //metodo que intenta borrar y recrear una tabla en funcion de la tabla introducia por parametro
        try {

            switch (tabla) {
                case "Estudiantes":
                    db.execSQL(DATABASE_DROP_ESTU);
                    db.execSQL(DATABASE_CREATE_ESTU);
                    Toast.makeText(context, "Se ha vaciado " + DATABASE_TABLE_ESTU, Toast.LENGTH_SHORT).show();
                    break;
                case "Profesores":
                    db.execSQL(DATABASE_DROP_PROF);
                    db.execSQL(DATABASE_CREATE_PROF);
                    Toast.makeText(context, "Se ha vaciado " + DATABASE_TABLE_PROF, Toast.LENGTH_SHORT).show();
                    break;
                case "Armaggedon":
                    dbHelper.onUpgrade(db,1,1);
                    break;
                default:
                    Toast.makeText(context, "No se ha seleccionado ninguna tabla", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(context, "No se ha podido vaciar y crear la tabla " + tabla, Toast.LENGTH_SHORT).show();
        }
    }

    private static class MyDbHelper extends SQLiteOpenHelper {
        //contexto necesario para el toast de "onUpgrade"
        Context c;
        public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            c = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_ESTU);
            db.execSQL(DATABASE_CREATE_PROF);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP_ESTU);
            db.execSQL(DATABASE_DROP_PROF);
            onCreate(db);
            Toast.makeText(c, "Upgrade realizado", Toast.LENGTH_SHORT).show();
        }

    }
}
