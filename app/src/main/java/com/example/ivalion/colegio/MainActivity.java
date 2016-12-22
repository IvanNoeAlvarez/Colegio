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
    FloatingActionButton ver;
    Intent i;
    static final int INS_ESTUDIANTE = 1;
    static final int DEL_ESTUDIANTE = 3;
    static final int INS_PROFESOR = 2;
    static final int DEL_PROFESOR = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new myDBAdapter(this);
        dbAdapter.open();

        //los botones estan inicializados en orden de aparicion en el activity; arriba->abajo y  izquierda->derecha
        ver = (FloatingActionButton) findViewById(R.id.btn_ver);

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
                b.putBoolean("eleccion",true);
                i.putExtras(b);
                startActivityForResult(i, INS_ESTUDIANTE);
            }
        });
        estu_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), DelEstudiante.class);
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
                b.putBoolean("eleccion",false);
                i.putExtras(b);
                startActivityForResult(i, INS_PROFESOR);
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

        armageddon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    if (dbAdapter.deleteEstudiante(data.getExtras().getInt("id")))
                        Toast.makeText(MainActivity.this, "Estudiante eliminado", Toast.LENGTH_SHORT).show();
                }
                break;
            case DEL_PROFESOR:
                if (requestCode == RESULT_OK) {

                }
                break;
            default:
                Toast.makeText(MainActivity.this, "Algo has hecho mal", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo que carga el activity con los datos una tabla en funcion del booleano
    private void verEstudiantesOProfesores(boolean eleccion) {

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
                    String[] asdf = {c.getString(0) + ":", c.getString(1), c.getString(2), c.getString(3), "" + c.getInt(4), "" + c.getInt(5)};

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

    }

    private void vaciarEstudiantes(View view) {
        dbAdapter.vaciarTabla("Estudiantes", view);
    }
}
