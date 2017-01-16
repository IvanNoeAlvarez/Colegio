package com.example.ivalion.colegio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Eliminar extends AppCompatActivity {
    EditText id;
    Button borrar;
    Bundle b;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        i = getIntent();

        id = (EditText)findViewById(R.id.et_id);
        borrar = (Button)findViewById(R.id.btn_borrar);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().isEmpty())
                    Toast.makeText(Eliminar.this, "Introduce un ID", Toast.LENGTH_SHORT).show();
                else{
                    Bundle b = new Bundle();
                    Intent i = new Intent();
                    b.putInt("id", Integer.parseInt(id.getText().toString()));
                    i.putExtras(b);
                    setResult(RESULT_OK, i);
                    finish();
                }

            }
        });


        if (i.getExtras().getBoolean("eleccion")==false)
            id.setHint("Id Profesor");
        else
            id.setHint("Id Estudiante");


    }
}
