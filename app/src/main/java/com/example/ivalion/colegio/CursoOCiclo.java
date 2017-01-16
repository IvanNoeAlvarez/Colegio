package com.example.ivalion.colegio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CursoOCiclo extends AppCompatActivity {

    EditText ciclo, curso;
    Button aceptar;
    Intent i;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso0ciclo);

        ciclo = (EditText) findViewById(R.id.et_ciclo);
        curso = (EditText) findViewById(R.id.et_curso);

        aceptar = (Button) findViewById(R.id.btn_ok);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent();
                b = new Bundle();

                if (ciclo.getText().toString().isEmpty() && curso.getText().toString().isEmpty())
                    Toast.makeText(CursoOCiclo.this, "Introduce algún dato para aceptar", Toast.LENGTH_SHORT).show();
                else {

                    if (!curso.getText().toString().isEmpty())
                    b.putString("curso", curso.getText().toString());
                    if (!ciclo.getText().toString().isEmpty())
                    b.putString("ciclo", ciclo.getText().toString());
                    i.putExtras(b);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }


}
