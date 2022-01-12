package com.unicauca.citasmed.modelo;

public class Cita {
    private int id_cita;
    private String fecha;
    private String hora;
    private int estado;
    private int id_profesional;
    private int id_paciente;

    public Cita() {
        id_cita=0;
        fecha="";
        hora="";
        estado=0;
        id_profesional=0;
        id_paciente=0;
    }

    public Cita(int id_cita, String fecha, String hora, int estado, int id_profesional, int id_paciente) {
        this.id_cita = id_cita;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.id_profesional = id_profesional;
        this.id_paciente = id_paciente;
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_cita) {
        this.id_cita = id_cita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_profesional() {
        return id_profesional;
    }

    public void setId_profesional(int id_profesional) {
        this.id_profesional = id_profesional;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }
}
