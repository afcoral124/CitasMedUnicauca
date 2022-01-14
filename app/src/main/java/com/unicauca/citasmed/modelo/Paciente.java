package com.unicauca.citasmed.modelo;

public class Paciente {
    private String correo;
    private int id_paciente;
    private String nombre;
    private String password;
    private String usuario;

    public Paciente() {
        correo="";
        id_paciente=0;
        nombre="";
        password="";
        usuario="";
    }

    public Paciente(String correo, int id_paciente, String nombre, String password, String usuario) {
        this.correo = correo;
        this.id_paciente = id_paciente;
        this.nombre = nombre;
        this.password = password;
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
