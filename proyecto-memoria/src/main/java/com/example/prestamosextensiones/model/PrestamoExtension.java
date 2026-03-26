package com.example.prestamosextensiones.model;

public class PrestamoExtension {

    private String nombre;
    private String area;
    private String bloque;

    public PrestamoExtension(String nombre, String area, String bloque) {
        this.nombre = nombre;
        this.area = area;
        this.bloque = bloque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    @Override
    public String toString() {
        return nombre + " | " + area + " | " + bloque;
    }
}