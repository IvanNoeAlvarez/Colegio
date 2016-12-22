package com.example.ivalion.colegio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Insertar extends AppCompatActivity {

    EditText nombre, curso, ciclo, nota, despacho, edad;
    Button insertar;
    LinearLayout ll_estudiante, ll_profesor;
    Bundle b;
    Intent i;
    boolean eleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_estudiante);

        i = getIntent();

        //nombre curso ciclo nota edad
        nombre = (EditText) findViewById(R.id.et_nombre);
        curso = (EditText) findViewById(R.id.et_curso);
        ciclo = (EditText) findViewById(R.id.et_ciclo);
        nota = (EditText) findViewById(R.id.et_nota);
        despacho = (EditText) findViewById(R.id.et_despacho);
        edad = (EditText) findViewById(R.id.et_edad);

        ll_estudiante = (LinearLayout) findViewById(R.id.ll_estudiante);
        ll_profesor = (LinearLayout) findViewById(R.id.ll_profesor);

        //si es true, se inserta un estudiante; si no, un profesor.
        eleccion = i.getExtras().getBoolean("eleccion");

        if (eleccion)
            ll_profesor.setVisibility(View.GONE);
        else
            ll_estudiante.setVisibility(View.GONE);


        insertar = (Button) findViewById(R.id.btn_ins);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b = new Bundle();
                //si los campos no estan vacios y esta visible despacho/nota
                if (compruebaVacios()) {

                    b.putString("nombre", nombre.getText().toString());
                    b.putString("curso", curso.getText().toString());
                    b.putString("ciclo", ciclo.getText().toString());

                    //verifico qué layout esta visible
                    if (ll_estudiante.getVisibility() == View.VISIBLE)
                        b.putInt("nota", Integer.parseInt(nota.getText().toString()));
                    else
                        b.putString("despacho", despacho.getText().toString());

                    b.putInt("edad", Integer.parseInt(edad.getText().toString()));
                    i.putExtras(b);
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    Toast.makeText(Insertar.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //metodo que comprueba los EditTexts y devuelve true si estan todos llenos
    private boolean compruebaVacios() {
        if (eleccion)
            if (!(nombre.getText().toString().isEmpty() ||
                    curso.getText().toString().isEmpty() ||
                    ciclo.getText().toString().isEmpty() ||
                    edad.getText().toString().isEmpty() ||
                    nota.getText().toString().isEmpty()
            ))
                return true;
            else
                return false;
        else if (!(nombre.getText().toString().isEmpty() ||
                curso.getText().toString().isEmpty() ||
                ciclo.getText().toString().isEmpty() ||
                edad.getText().toString().isEmpty() ||
                despacho.getText().toString().isEmpty()
        ))
            return true;
        else
            return false;

    }
}
