package com.example.ivalion.colegio;

/**
 * Created by Ivalion on 13/12/2016.
 */
public class Profesor {
    String nombre, ciclo, curso, despacho;
    int edad;

    public Profesor(String nom, String cic, String cur, String des, int ed){
        nombre = nom;
        ciclo = cic;
        curso = cur;
        despacho = des;
        edad = ed;
    }


    //getters
    public Object getTodo(){
        Object[]todo = new Object[5];
        todo[0]=getNombre();
        todo[1]=getCiclo();
        todo[2]=getCurso();
        todo[3]=getDespacho();
        todo[4]=getEdad();
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

    public String getDespacho() {
        return despacho;
    }

    public int getEdad() {
        return edad;
    }
}
