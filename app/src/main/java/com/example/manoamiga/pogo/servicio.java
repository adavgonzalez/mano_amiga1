package com.example.manoamiga.pogo;

public class servicio
{
    String id;
    String servicio;
    String precio;


    public servicio(String id, String servicio, String precio) {
        this.id = id;
        this.precio = precio;
        this.servicio = servicio;
    }

    public servicio() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
