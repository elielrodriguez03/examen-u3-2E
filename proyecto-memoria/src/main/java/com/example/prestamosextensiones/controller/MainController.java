package com.example.prestamosextensiones.controller;

import com.example.prestamosextensiones.model.PrestamoExtension;
import com.example.prestamosextensiones.service.PrestamoExtensionService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML
    private TextField txtNombreSolicitante;

    @FXML
    private TextField txtArea;

    @FXML
    private ComboBox<String> cbBloque;

    @FXML
    private ListView<PrestamoExtension> listView;

    private PrestamoExtensionService service = new PrestamoExtensionService();

    private String nombreOriginal = null;

    @FXML
    public void initialize() {
        cbBloque.getItems().addAll("Bloque A", "Bloque B", "Laboratorio");
    }

    // ------------------ AGREGAR ------------------
    @FXML
    public void agregar() {

        String nombre = txtNombreSolicitante.getText();
        String area = txtArea.getText();
        String bloque = cbBloque.getValue();

        if (nombre.isEmpty() || area.isEmpty() || bloque == null) {
            System.out.println("Campos vacíos");
            return;
        }

        PrestamoExtension p = new PrestamoExtension(nombre, area, bloque);

        if (service.agregar(p)) {
            actualizarLista();
            limpiar();
        } else {
            System.out.println("Nombre repetido");
        }
    }

    // ------------------ ACTUALIZAR ------------------
    @FXML
    public void actualizar() {

        if (nombreOriginal == null) {
            System.out.println("Primero busca o selecciona");
            return;
        }

        String nombre = txtNombreSolicitante.getText();
        String area = txtArea.getText();
        String bloque = cbBloque.getValue();

        PrestamoExtension nuevo = new PrestamoExtension(nombre, area, bloque);

        if (service.actualizar(nombreOriginal, nuevo)) {
            actualizarLista();
            limpiar();
            nombreOriginal = null;
        } else {
            System.out.println("Error al actualizar");
        }
    }

    // ------------------ ELIMINAR ------------------
    @FXML
    public void eliminar() {

        String nombre = txtNombreSolicitante.getText();

        if (service.eliminar(nombre)) {
            actualizarLista();
            limpiar();
        } else {
            System.out.println("No encontrado");
        }
    }

    // ------------------ SELECCIONAR ------------------
    @FXML
    public void seleccionar() {

        PrestamoExtension p = listView.getSelectionModel().getSelectedItem();

        if (p != null) {
            txtNombreSolicitante.setText(p.getNombre());
            txtArea.setText(p.getArea());
            cbBloque.setValue(p.getBloque());

            nombreOriginal = p.getNombre();
        }
    }

    // ------------------ LISTA ------------------
    private void actualizarLista() {
        listView.getItems().setAll(service.obtenerTodos());
    }

    // ------------------ LIMPIAR ------------------
    private void limpiar() {
        txtNombreSolicitante.clear();
        txtArea.clear();
        cbBloque.setValue(null);
    }
}