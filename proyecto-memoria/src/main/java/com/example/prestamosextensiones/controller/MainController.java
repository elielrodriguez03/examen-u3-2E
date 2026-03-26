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

    // Aquí se guarda el nombre original del registro encontrado o seleccionado.
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarBloques();
        actualizarLista();

        // Si el alumno selecciona un elemento del ListView,
        // también puede cargar sus datos en los mismos controles.
        lvRegistros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarSeleccion(newValue);
            }
        });
    }

    private void cargarBloques() {
        String[] turnos = service.obtenerBloques();
        for (int i = 0; i < turnos.length; i++) {
            cbBloque.getItems().add(turnos[i]);
        }
    }

    @FXML
    public void agregar() {

        String nombre = txtNombreSolicitante.getText();
        String area = txtArea.getText();
        String bloque = cbBloque.getValue();


        if (nombre == null || nombre.isEmpty() ||
                area == null || area.isEmpty() ||
                bloque == null || bloque.isEmpty()) {

            mostrarMensaje("Error", "Todos los campos son obligatorios.", Alert.AlertType.INFORMATION);
            return;
        }

        PrestamoExtension nuevo = new PrestamoExtension(nombre, area, bloque);

    }

    @FXML
    public void buscar() {

        String nombre = txtNombreSolicitante.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre del solicitante para buscar.", Alert.AlertType.INFORMATION);
            return;
        }

        PrestamoExtension registro = service.buscarPorNombreSolicitante(nombre.trim());

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
            mostrarMensaje("Error", "Primero busca o selecciona un registro para actualizar", Alert.AlertType.INFORMATION);
            return;
        }

        String nuevoNombre = txtNombreSolicitante.getText();
        String nuevaArea = txtArea.getText();
        String nuevoBloque = cbBloque.getValue();

        PrestamoExtension actualizado = new PrestamoExtension(nuevoNombre, nuevaArea, nuevoBloque);

        if (Boolean.parseBoolean(service.actualizar(nombreOriginal, actualizado))) {
            actualizarLista();
            limpiar();
            nombreOriginal = null;
        }
    }

    @FXML
    public void eliminar() {

        String nombre = txtNombreSolicitante.getText();
        if (Boolean.parseBoolean(service.eliminar(nombre))) {
            actualizarLista();
            limpiar();
        } else {
            mostrarMensaje("Error", "No existeel registro a eliminar.", Alert.AlertType.INFORMATION   );
        }

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
            ListView<PrestamoExtension> lvRegistros;
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
}
