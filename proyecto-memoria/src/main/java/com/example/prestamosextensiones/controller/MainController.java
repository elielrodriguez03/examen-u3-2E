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
        // TODO:
        // 1. Leer txtNombreSolicitante, txtArea y cbBloque.
        // 2. Mandar esos datos al service.
        // 3. Si el service regresa un mensaje, mostrar error.
        // 4. Si regresa null, refrescar la lista y limpiar.
        String nombre = txtNombreSolicitante.getText();
        String area = txtArea.getText();
        String bloque = cbBloque.getValue();

        if (nombre == null || nombre.isBlank() || area == null || area.isBlank() || bloque == null) {
            mostrarMensaje("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        String error = service.agregar(nombre, area, bloque);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
        mostrarMensaje("Éxito", "Registro agregado correctamente.", Alert.AlertType.INFORMATION);
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
        // TODO:
        // UPDATE reutiliza los mismos controles.
        //
        // Flujo esperado:
        // 1. Primero buscar por nombre o seleccionar desde el ListView.
        // 2. Eso debe cargar los datos en pantalla y guardar nombreOriginal.
        // 3. Luego el usuario modifica txtNombreSolicitante, txtArea y cbBloque.
        // 4. Al presionar Actualizar, mandar al service:
        //      - nombreOriginal
        //      - txtNombreSolicitante.getText()
        //      - txtArea.getText()
        //      - cbBloque.getValue()
        // 5. El service debe buscar el registro original usando nombreOriginal.
        // 6. Si lo encuentra, debe cambiar sus datos.
        // 7. Luego refrescar el ListView y limpiar los controles.
        //
        // Importante:
        // Si nombreOriginal es null, entonces no se ha buscado ni seleccionado nada.
        if (nombreOriginal == null) {
            mostrarMensaje("Aviso", "Primero busca o selecciona un registro.", Alert.AlertType.WARNING);
            return;
        }

        String nuevoNombre = txtNombreSolicitante.getText();
        String nuevaArea = txtArea.getText();
        String nuevoBloque = cbBloque.getValue();

        if (nuevoNombre == null || nuevoNombre.isBlank() || nuevaArea == null || nuevaArea.isBlank() || nuevoBloque == null) {
            mostrarMensaje("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        String error = service.actualizar(nombreOriginal, nuevoNombre, nuevaArea, nuevoBloque);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
        mostrarMensaje("Éxito", "Registro actualizado correctamente.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void eliminar() {
        // TODO:
        // DELETE sí borra el objeto de la lista.
        //
        // Flujo esperado:
        // 1. Tomar el nombre desde txtNombreSolicitante.
        // 2. Mandarlo al service.
        // 3. El service debe buscarlo y eliminarlo de la lista.
        // 4. Refrescar el ListView.
        // 5. Limpiar controles.
        //
        // También se puede seleccionar un elemento del ListView
        // y luego presionar Eliminar.
        String nombre = txtNombreSolicitante.getText();

        if (nombre == null || nombre.isBlank()) {
            mostrarMensaje("Aviso", "Escribe o selecciona un registro para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        String error = service.eliminar(nombre);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
        mostrarMensaje("Éxito", "Registro eliminado correctamente.", Alert.AlertType.INFORMATION);
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