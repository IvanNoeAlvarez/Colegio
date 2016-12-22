package com.example.ivalion.colegio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VerEstudiantesOProfesores extends AppCompatActivity {

    ListView lista;
    ArrayList<String> datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_estudiantes);

        lista = (ListView) findViewById(R.id.listView);
        Intent i = getIntent();

        int contador = i.getExtras().getInt("contador");
        Toast.makeText(VerEstudiantesOProfesores.this, contador + "", Toast.LENGTH_SHORT).show();




        datos = new ArrayList<String>();
        for (int n = 0; n < i.getIntExtra("contador", 0); n++) {

            //Estructura del array: id nombre curso ciclo nota/despacho edad
            String[] contenido = i.getExtras().getStringArray("" + n);
            if (contenido != null) {
                //Toast.makeText(VerEstudiantesOProfesores.this, contenido[0]+" "+contenido[1]+" "+contenido[2]+" "+contenido[3]+" "+contenido[4]+" "+contenido[5], Toast.LENGTH_SHORT).show();
                datos.add(contenido[0] + " " + contenido[1] + " " + contenido[2] + " " + contenido[3] + " " + contenido[4] + " " + contenido[5]);
            }

        }

        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        // Set The Adapter
        lista.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                String dato = datos.get(posicion);
                Toast.makeText(getApplicationContext(), "asdf : " + dato, Toast.LENGTH_LONG).show();
                return true;
            }
        });


    }

    public void dialeg(View v, final int posicio) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        String nomComplet = "<b>" + datos.get(posicio) + ", " + datos.get(posicio) + "</b>";
        String msg = "Estas segur que vols eliminar el client " + nomComplet + "?";

        builder.setMessage("Estas  segur que vols eliminar el client " + nomComplet + "?");
        builder.setTitle("Eliminar Client");

        // 3. Li afegim els botons Si i No, amb les accions a realitzar
        builder.setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setNegativeButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        }




}
