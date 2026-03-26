package com.example.prestamosextensiones.model;

public class PrestamoExtension {

    private String nombreSolicitante;
    private String area;
    private String bloque;

    public PrestamoExtension(String nombreSolicitante, String area, String bloque) {
        this.nombreSolicitante = nombreSolicitante;
        this.area = area;
        this.bloque = bloque;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
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
        return nombreSolicitante + " | " + area + " | " + bloque;
    }
}
