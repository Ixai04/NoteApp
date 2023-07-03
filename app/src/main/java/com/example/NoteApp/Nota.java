package com.example.NoteApp;


public class Nota {
    private long id;
    private String titulo;
    private String contenido;
    private String fecha;
    private String hora;

    Nota(String titulo, String contenido, String fecha, String hora){
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.hora = hora;
    }

    Nota(long id, String titulo, String contenido, String fecha, String hora){
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.hora = hora;
    }
    Nota(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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

}
