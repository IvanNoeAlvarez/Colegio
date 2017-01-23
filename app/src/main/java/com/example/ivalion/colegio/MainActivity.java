package com.example.ivalion.colegio;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private myDBAdapter dbAdapter;

    Button estu_ins, estu_del, prof_ins, prof_del, estu_vaciar, prof_vaciar, armageddon;
    ImageButton verEstu, verProf;
    FloatingActionButton ver, filtrar;
    Intent i;
    static final int INS_ESTUDIANTE = 1;
    static final int DEL_ESTUDIANTE = 3;
    static final int INS_PROFESOR = 2;
    static final int DEL_PROFESOR = 4;
    static final int FILTRO = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new myDBAdapter(this);
        dbAdapter.open();

        //los botones estan inicializados en orden de aparicion en el activity; arriba->abajo y  izquierda->derecha
        ver = (FloatingActionButton) findViewById(R.id.btn_ver);
        filtrar = (FloatingActionButton) findViewById(R.id.btn_filtrar);

        estu_ins = (Button) findViewById(R.id.btn_estu_insert);
        verEstu = (ImageButton) findViewById(R.id.btn_ver_estu);

        prof_ins = (Button) findViewById(R.id.btn_prof_insert);
        verProf = (ImageButton) findViewById(R.id.btn_ver_prof);

        estu_del = (Button) findViewById(R.id.btn_estu_borrar);
        estu_vaciar = (Button) findViewById(R.id.btn_estus_borrar);

        prof_del = (Button) findViewById(R.id.btn_prof_borrar);
        prof_vaciar = (Button) findViewById(R.id.btn_profs_borrar);

        armageddon = (Button) findViewById(R.id.btn_borrar_db);

        //ONCLICKLISTENERS

        //ESTUDIANTES
        estu_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), Insertar.class);
                Bundle b = new Bundle();
                b.putBoolean("eleccion", true);
                i.putExtras(b);
                startActivityForResult(i, INS_ESTUDIANTE);
            }
        });
        estu_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), Eliminar.class);
                Bundle b = new Bundle();
                b.putBoolean("eleccion", true);
                i.putExtras(b);
                startActivityForResult(i, DEL_ESTUDIANTE);
            }
        });
        estu_vaciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaciarEstudiantes(view);
            }
        });


        //PROFESORES
        prof_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), Insertar.class);
                Bundle b = new Bundle();
                b.putBoolean("eleccion", false);
                i.putExtras(b);
                startActivityForResult(i, INS_PROFESOR);
            }
        });
        prof_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), Eliminar.class);
                Bundle b = new Bundle();
                b.putBoolean("eleccion", false);
                i.putExtras(b);
                startActivityForResult(i, DEL_PROFESOR);
            }
        });
        prof_vaciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaciarProfesores(view);
            }
        });


        //MIXTOS
        verEstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verEstudiantesOProfesores(true);
            }
        });

        verProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verEstudiantesOProfesores(false);
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verTodos();
            }
        });

        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), CursoOCiclo.class);
                startActivityForResult(i, FILTRO);

            }
        });

        armageddon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbAdapter.vaciarTabla("Armaggedon");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INS_ESTUDIANTE:
                if (resultCode == RESULT_OK) {

                    dbAdapter.insertarEstudiante(
                            data.getExtras().getString("nombre"),
                            data.getExtras().getString("ciclo"),
                            data.getExtras().getString("curso"),
                            data.getExtras().getInt("nota"),
                            data.getExtras().getInt("edad")
                    );
                    Toast.makeText(MainActivity.this, "Alumno agregado", Toast.LENGTH_SHORT).show();
                }
                break;
            case INS_PROFESOR:
                if (resultCode == RESULT_OK) {

                    dbAdapter.insertarProfesores(
                            data.getExtras().getString("nombre"),
                            data.getExtras().getString("ciclo"),
                            data.getExtras().getString("curso"),
                            data.getExtras().getString("despacho"),
                            data.getExtras().getInt("edad")
                    );
                    Toast.makeText(MainActivity.this, "Profesor agregado", Toast.LENGTH_SHORT).show();
                }
                break;
            case DEL_ESTUDIANTE:
                if (resultCode == RESULT_OK) {
                    //intento borrar un estudiante; muestro mensaje si lo consigo
                    if (dbAdapter.delete(data.getExtras().getInt("id"), true))
                        Toast.makeText(MainActivity.this, "Estudiante eliminado", Toast.LENGTH_SHORT).show();
                }
                break;
            case DEL_PROFESOR:
                if (resultCode == RESULT_OK) {
                    if (dbAdapter.delete(data.getExtras().getInt("id"), false))
                        Toast.makeText(MainActivity.this, "Profesor eliminado", Toast.LENGTH_SHORT).show();
                }
                break;
            case FILTRO:
                if (resultCode == RESULT_OK) {
                    verFiltrado(dbAdapter.filtrar(data.getExtras().getString("curso"), data.getExtras().getString("ciclo")));
                }
                break;
            default:
                Toast.makeText(MainActivity.this, "Algo has hecho mal", Toast.LENGTH_SHORT).show();
        }
    }

    private void verFiltrado(Cursor[] cursor) {

        Bundle b = new Bundle();
        i = new Intent(getApplicationContext(), VerEstudiantesOProfesores.class);
        int contador = 0;
        ArrayList<String[]> datos = new ArrayList<String[]>();


        if (cursor[1] != null) {
            if (cursor[1].moveToFirst()) {
                //Recorro el cursor hasta que no haya más registros
                do {
                    //Guardo los valores en un array que guardare en un arraylist
                    String[] asdf = {cursor[1].getInt(0) + ":", cursor[1].getString(1)};

                    datos.add(asdf);
                    b.putStringArray("" + contador, asdf);

                    contador++;

                } while (cursor[1].moveToNext());

            }
        }
        //int que registra cuantos estudiantes hay, para poner un separador
        b.putInt("separador", contador);
        if (cursor[0] != null) {
            if (cursor[0].moveToFirst()) {
                //Recorro el cursor hasta que no haya más registros
                do {
                    //Guardo los valores en un array que guardare en un arraylist
                    String[] asdf = {cursor[0].getInt(0) + ":", cursor[0].getString(1)};

                    datos.add(asdf);
                    b.putStringArray("" + contador, asdf);

                    contador++;

                } while (cursor[0].moveToNext());
                i = new Intent(getApplicationContext(), VerEstudiantesOProfesores.class);
                b.putInt("contador", contador);

                i.putExtras(b);
                startActivity(i);
            }
        }
    }

    //metodo que carga el activity con los datos una tabla en funcion del booleano
    private void verEstudiantesOProfesores(boolean eleccion) {
        //eleccion: 1=estudiantes   0=profesores
        Bundle b = new Bundle();
        int contador = 0;
        ArrayList<String[]> datos = new ArrayList<String[]>();

        Cursor c = null;
        if (eleccion)
            c = dbAdapter.getEstudiantes();
        else
            c = dbAdapter.getProfesores();
        if (c != null) {
            if (c.moveToFirst()) {
                //Recorro el cursor hasta que no haya más registros
                do {
                    //Guardo los valores en un array que guardare en un arraylist
                    String asdf[]=null;
                    //creo asdf2 por que no me deja inicializar el array
                    if (eleccion) {
                        String [] asdf2 = {c.getString(0) + ":", c.getString(1), c.getString(2), c.getString(3), "" + c.getInt(4), "" + c.getInt(5)};
                        asdf = asdf2;
                    }else{
                       String[] asdf2 = {c.getString(0) + ":", c.getString(1), c.getString(2), c.getString(3), c.getString(4), "" + c.getInt(5)};
                        asdf= asdf2;
                    }

                        datos.add(asdf);
                    b.putStringArray("" + contador, asdf);

                    contador++;

                } while (c.moveToNext());
                i = new Intent(getApplicationContext(), VerEstudiantesOProfesores.class);
                b.putInt("contador", contador);

                i.putExtras(b);
                startActivity(i);
            }
        }
    }

    private void verTodos() {
        Cursor estudiantes = dbAdapter.getEstudiantes();
        Cursor profesores = dbAdapter.getProfesores();
        Bundle b = new Bundle();
        int contador = 0;
        ArrayList<String[]> datos = new ArrayList<String[]>();


        if (estudiantes != null) {
            if (estudiantes.moveToFirst()) {
                //Recorro el cursor hasta que no haya más registros
                do {
                    //Guardo los valores en un array que guardare en un arraylist
                    String[] asdf = {estudiantes.getString(0) + ":", estudiantes.getString(1), estudiantes.getString(2), estudiantes.getString(3), "" + estudiantes.getInt(4), "" + estudiantes.getInt(5)};

                    datos.add(asdf);
                    b.putStringArray("" + contador, asdf);

                    contador++;

                } while (estudiantes.moveToNext());
            }
        }
        if (profesores != null) {
            if (profesores.moveToFirst()) {
                //Recorro el cursor hasta que no haya más registros
                do {
                    //Guardo los valores en un array que guardare en un arraylist
                    String[] asdf = {profesores.getString(0) + ":", profesores.getString(1), profesores.getString(2), profesores.getString(3), profesores.getString(4), "" + profesores.getInt(5)};

                    datos.add(asdf);
                    b.putStringArray("" + contador, asdf);

                    contador++;

                } while (profesores.moveToNext());
                i = new Intent(getApplicationContext(), VerEstudiantesOProfesores.class);
                b.putInt("contador", contador);

                i.putExtras(b);
                startActivity(i);
            }
        }

    }

    private void vaciarEstudiantes(View view) {
        dbAdapter.vaciarTabla("Estudiantes");
    }

    private void vaciarProfesores(View view) {
        dbAdapter.vaciarTabla("Profesores");
    }

    private void vaciarBD() {
        //metodo que vacia la base de datos por completo
        dbAdapter.vaciarTabla("Armaggedon");
    }
}
