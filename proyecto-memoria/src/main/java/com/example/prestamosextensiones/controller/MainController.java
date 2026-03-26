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

        // También se puede cargar un registro seleccionándolo en el ListView.
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

        String nombreSolicitante = txtNombreSolicitante.getText();
        String area = txtArea.getText();
        String bloque = cbBloque.getValue();

        String msg =  service.agregar(nombreSolicitante, area, bloque);
        if (msg != null){
            mostrarMensaje("Error", msg, Alert.AlertType.ERROR);
            return;
        }
        actualizarLista();
        limpiar();
    }

    @FXML
    public void buscar() {
        // Método de ejemplo resuelto.
        PrestamoExtension registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtArea.setText(registro.getArea());
        cbBloque.setValue(registro.getBloque());

        // Este valor es clave para UPDATE.
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {

        String msg = service.actualizar(nombreOriginal, txtNombreSolicitante.getText(), txtArea.getText(), cbBloque.getValue());
        if (msg != null){
            mostrarMensaje("Error", msg, Alert.AlertType.ERROR);
        }
        actualizarLista();
        limpiar();


    }

    @FXML
    public void eliminar() {

        String nombreSolicitante = txtNombreSolicitante.getText();
        String msg = service.eliminar(nombreSolicitante);
        if (msg != null){
            mostrarMensaje("Error", msg, Alert.AlertType.ERROR);
            return;
        }
        actualizarLista();
        limpiar();
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
}
