package com.example.manoamiga.pogo;

public class mensaje {

    String id;
    String nombre;
    String mensaje;
    Long timestamp;

    public mensaje(String id, String nombre, String mensaje, Long timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

    public mensaje() {
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setHora(Long timestamp){
        this.timestamp = timestamp;
    }
}
