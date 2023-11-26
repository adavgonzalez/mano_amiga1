package com.example.manoamiga.pogo;

public class chats {
    String id;
    String nombre;


    public chats(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public chats() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
