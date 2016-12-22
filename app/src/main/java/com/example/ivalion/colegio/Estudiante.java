package com.example.ivalion.colegio;

/**
 * Created by Ivalion on 13/12/2016.
 */
public class Estudiante {
    String nombre, ciclo, curso;
    int edad;
    double nota;

    public Estudiante(String nom, String cic, String cur, float not, int ed) {
        nombre = nom;
        ciclo = cic;
        curso = cur;

        edad = ed;
        nota = not;
    }


    //getters
    public Object getTodo() {
        Object[] todo = new Object[5];
        todo[0] = getNombre();
        todo[1] = getCiclo();
        todo[2] = getCurso();
        todo[3] = getNota();
        todo[4] = getEdad();
        return todo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public int getEdad() {
        return edad;
    }

    public double getNota() {
        return nota;
    }
}
