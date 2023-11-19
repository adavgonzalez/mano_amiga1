package com.example.manoamiga.modelos;

public class Proveedor {

    int id;
    String nombre,telefono,correo,contraseña,direccion,nm_service;

    public Proveedor() {
    }

    public Proveedor(int id, String nombre, String telefono, String correo, String contraseña, String direccion, String nm_service) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.nm_service = nm_service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNm_service() {
        return nm_service;
    }

    public void setNm_service(String nm_service) {
        this.nm_service = nm_service;
    }
}
