package com.example.prestamosextensiones.controller;

import com.example.prestamosextensiones.model.PrestamoExtension;
import com.example.prestamosextensiones.service.PrestamoExtensionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MainController {

    @FXML
    private TextField txtNombreSolicitante;

    @FXML
    private TextField txtArea;

    @FXML
    private ComboBox<String> cbBloque;

    @FXML
    private ListView<String> lvRegistros;

    private final PrestamoExtensionService service = new PrestamoExtensionService();

    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarBloques();
        actualizarLista();

        lvRegistros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarSeleccion(newValue);
            }
        });
    }

    private void cargarBloques() {
        String[] bloques = service.obtenerBloques();
        for (int i = 0; i < bloques.length; i++) {
            cbBloque.getItems().add(bloques[i]);
        }
    }

    @FXML
    public void agregar() {
        String nombre = txtNombreSolicitante.getText();
        String area = txtArea.getText();
        String bloque = cbBloque.getValue();

        String error = service.agregar(nombre, area, bloque);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiarControles();
    }

    @FXML
    public void buscar() {
        PrestamoExtension registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtArea.setText(registro.getArea());
        cbBloque.setValue(registro.getBloque());

        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        if (nombreOriginal == null) {
            mostrarMensaje("Aviso", "Primero busca o selecciona un registro", Alert.AlertType.WARNING);
            return;
        }

        String nuevoNombre = txtNombreSolicitante.getText();
        String nuevaArea = txtArea.getText();
        String nuevoBloque = cbBloque.getValue();

        String error = service.actualizar(nombreOriginal, nuevoNombre, nuevaArea, nuevoBloque);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiarControles();
        nombreOriginal = null;
    }

    @FXML
    public void eliminar() {
        String nombre = txtNombreSolicitante.getText();

        String error = service.eliminar(nombre);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiarControles();
        nombreOriginal = null;
    }

    @FXML
    public void limpiar() {
        txtNombreSolicitante.clear();
        txtArea.clear();
        cbBloque.setValue(null);
        lvRegistros.getSelectionModel().clearSelection();
        nombreOriginal = null;
    }

    private void actualizarLista() {
        lvRegistros.getItems().clear();
        List<PrestamoExtension> registros = service.obtenerTodos();

        for (int i = 0; i < registros.size(); i++) {
            lvRegistros.getItems().add(registros.get(i).toString());
        }
    }

    private void cargarSeleccion(String textoSeleccionado) {
        List<PrestamoExtension> registros = service.obtenerTodos();

        for (int i = 0; i < registros.size(); i++) {
            PrestamoExtension actual = registros.get(i);

            if (actual.toString().equals(textoSeleccionado)) {
                txtNombreSolicitante.setText(actual.getNombreSolicitante());
                txtArea.setText(actual.getArea());
                cbBloque.setValue(actual.getBloque());
                nombreOriginal = actual.getNombreSolicitante();
                break;
            }
        }
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarControles() {
        txtNombreSolicitante.clear();
        txtArea.clear();
        cbBloque.setValue(null);
    }


}
