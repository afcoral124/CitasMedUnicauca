package com.unicauca.citasmed.modelo;

import java.util.ArrayList;

public class Profesional {
    private int id_profesional;
    private String nombre;
    private String ciudad;
    private int id_profesion;
    private ArrayList<Cita> citas;
    public Profesional(){
        id_profesional=0;
        nombre="";
        ciudad="";
        id_profesion=0;
        citas=null;
    }


    public Profesional(int id_profesional, String nombre, String ciudad, int profesion, ArrayList<Cita> citas) {
        this.id_profesional = id_profesional;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.id_profesion = profesion;
        this.citas = citas;
    }

    public int getId_profesional() {
        return id_profesional;
    }

    public void setId_profesional(int id_profesional) {
        this.id_profesional = id_profesional;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getId_profesion() {
        return id_profesion;
    }

    public void setId_profesion(int id_profesion) {
        this.id_profesion = id_profesion;
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }

    public void setCitas(ArrayList<Cita> citas) {
        this.citas = citas;
    }
}