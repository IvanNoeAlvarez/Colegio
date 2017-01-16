package com.example.ivalion.colegio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VerEstudiantesOProfesores extends AppCompatActivity {

    ListView lista;
    ArrayList<String> datos; //arraylist que contendra la informacion de una tabla en formato de un String por linea (Ej. "1: a_a_a_1_1" , "2: b_b_b_2_2")
    String[] datosSeparados; //String que contendra la informacion fragmentada del elemento de la lista seleccionado (Ej. {"1: " , "a" , "a" , "a" , "1" , "1"}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        lista = (ListView) findViewById(R.id.listView);
        Intent i = getIntent();

        int contador = i.getExtras().getInt("contador");
        Toast.makeText(VerEstudiantesOProfesores.this, "Total: " + contador, Toast.LENGTH_SHORT).show();


        datos = new ArrayList<>();
        if(i.getExtras().getInt("separador")!=0)
            datos.add("Estudiantes");

        for (int n = 0; n < i.getIntExtra("contador", 0); n++) {
            if ((i.getExtras().getInt("separador")==n)&&(i.getExtras().getInt("separador")!=0)){
                datos.add("Profesores");
            }
            //Estructura del array: id nombre curso ciclo nota/despacho edad
            String[] contenido = i.getExtras().getStringArray("" + n);
            if (contenido != null) {
                String str = "";
                for (int z = 0;z<contenido.length;z++){
                    str = str.concat(contenido[z]+"_");
                }
                str = str.substring(0,str.length()-1);
                datos.add(str);
                //datos.add(contenido[0] + "_" + contenido[1] + "_" + contenido[2] + "_" + contenido[3] + "_" + contenido[4] + "_" + contenido[5]);
            }

        }

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);

        lista.setAdapter(arrayAdapter);
        lista.setTextFilterEnabled(true);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                datosSeparados = separaStrings(posicion);
                dialeg(view, posicion);
                return true;
            }
        });
    }

    //metodo que transforma un elemento del Arraylist en un String[] con los datos separados
    public String[] separaStrings(int pos) {

        String[] datosS;
        String linea = datos.get(pos);

        //desconpongo el string
        Log.d("asdf", linea);
        linea.replace(' ', '_');

        datosS = linea.split("_");

        return datosS;
    }

    //metodo que hace aparecer un menu de elecciones
    public void dialeg(View v, final int posicio) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());


        builder.setMessage("¿Qué quieres hacer con " + datosSeparados[1] + "?");
        builder.setTitle(datosSeparados[1]);

        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.setNeutralButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = Integer.parseInt(datosSeparados[0].substring(0, datosSeparados[0].length() - 1));

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
