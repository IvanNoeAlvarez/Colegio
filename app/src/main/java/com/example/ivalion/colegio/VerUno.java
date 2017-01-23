package com.example.ivalion.colegio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class VerUno extends AppCompatActivity {

    EditText nombre, curso, ciclo, nota, despacho, edad;
    LinearLayout ll_despacho,ll_nota;
    Intent i;
    String[] datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_uno);

        i = getIntent();

        //nombre curso ciclo nota edad
        nombre = (EditText) findViewById(R.id.et_nombre);
        curso = (EditText) findViewById(R.id.et_curso);
        ciclo = (EditText) findViewById(R.id.et_ciclo);
        nota = (EditText) findViewById(R.id.et_nota);
        despacho = (EditText) findViewById(R.id.et_despacho);
        edad = (EditText) findViewById(R.id.et_edad);


        ll_despacho = (LinearLayout)findViewById(R.id.ll_profesor);
        ll_nota = (LinearLayout)findViewById(R.id.ll_estudiante);

        datos = i.getStringArrayExtra("datos");
        if(averiguaSiEsAlumno(datos[4])){
            ll_despacho.setVisibility(View.GONE);
            nombre.setText(datos[1]);
            curso.setText(datos[2]);
            ciclo.setText(datos[3]);
            nota.setText(datos[4]);
            edad.setText(datos[5]);
        }else{
            ll_nota.setVisibility(View.GONE);
            nombre.setText(datos[1]);
            curso.setText(datos[2]);
            ciclo.setText(datos[3]);
            despacho.setText(datos[4]);
            edad.setText(datos[5]);
        }

    }




    /*Como los datos son solo String, intento hacer un Integer del dato dado (el 5º del array).
     Si puedo, es por que es una nota y por tanto es un estudiante.
     Si no puedo, es por que es un despacho (que contiene chars), y por tanto es un profesor.
      */
    private boolean averiguaSiEsAlumno(String d) {
        boolean esAlumno = true;
        try {
            Integer.parseInt(d);
        } catch (NumberFormatException nfe) {
            esAlumno = false;
        } finally {
            return esAlumno;
        }

    }
}
