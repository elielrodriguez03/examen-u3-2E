package com.example.prestamosextensiones.model;

import javafx.fxml.FXML;

public class PrestamoExtension {

    private String nombreSolicitante;
    private String area;
    private String bloque;

    public PrestamoExtension(String nombreSolicitante, String area, String bloque) {
        this.nombreSolicitante = nombreSolicitante;
        this.area = area;
        this.bloque = bloque;
    }
     @FXML
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }
    @FXML
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }
       @FXML
    public String getArea() {
        return area;
    }
      @FXML
    public void setArea(String area) {
        this.area = area;
    }
    @FXML
    public String getBloque() {
        return bloque;
    }
    @FXML
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    @Override
    public String toString() {
        return nombreSolicitante + " | " + area + " | " + bloque;
    }
}
