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
        // TODO:
        // 1. Leer txtNombreSolicitante, txtArea y cbBloque.
        String nombreSolicitante = txtNombreSolicitante.getText();
        String area=txtArea.getText();
        String bloque= cbBloque.getValue();

        // 2. Mandar esos datos al service.
        String mensaje= service.agregar(nombreSolicitante , area , bloque);

        // 3. Si el service regresa un mensaje, mostrar error.
        if (mensaje!=null){
            mostrarMensaje("Error:  ", mensaje, Alert.AlertType.INFORMATION);
        }

        // 4. Si regresa null, refrescar la lista y limpiar.
        if (mensaje==null){
            mostrarMensaje("Estado: ", "Agregado correctamente", Alert.AlertType.INFORMATION);
            actualizarLista();
            limpiar();
        }
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
        // TODO:
        // UPDATE reutiliza los mismos controles.
        PrestamoExtension registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());
        //
        // Flujo esperado:
        // 1. Primero buscar por nombre o seleccionar desde el ListView.
        nombreOriginal=registro.getNombreSolicitante();

        // 2. Eso debe cargar los datos en pantalla y guardar nombreOriginal.
        // 3. Luego el usuario modifica txtNombreSolicitante, txtArea y cbBloque.
        // 4. Al presionar Actualizar, mandar al service:
        //      - nombreOriginal
        //      - txtNombreSolicitante.getText()
        //      - txtArea.getText()
        //      - cbBloque.getValue()
        String nuevoNombre= txtNombreSolicitante.getText();
        String nuevaArea=txtArea.getText();
        String nuevoBloque=cbBloque.getValue();
        String mensaje=service.actualizar(nombreOriginal,nuevoNombre,nuevaArea,nuevoBloque);
        // 5. El service debe buscar el registro original usando nombreOriginal.
        // 6. Si lo encuentra, debe cambiar sus datos.
        // 7. Luego refrescar el ListView y limpiar los controles.
        //
        // Importante:
        // Si nombreOriginal es null, entonces no se ha buscado ni seleccionado nada.
        if (mensaje==null){
            mostrarMensaje("Estado: ", "Actualizado correctamente", Alert.AlertType.INFORMATION);
        }else {
            mostrarMensaje("Error", mensaje, Alert.AlertType.INFORMATION);
        }

    }

    @FXML
    public void eliminar() {
        // TODO:
        // DELETE sí borra el objeto de la lista.
        //
        // Flujo esperado:
        // 1. Tomar el nombre desde txtNombreSolicitante.
        String nombreSoliciante=txtNombreSolicitante.getText();
        // 2. Mandarlo al service.
        String mensaje=service.eliminar(nombreSoliciante);
        // 3. El service debe buscarlo y eliminarlo de la lista.
        // 4. Refrescar el ListView.
        actualizarLista();
        // 5. Limpiar controles.
        limpiar();
        // También se puede seleccionar un elemento del ListView
        // y luego presionar Eliminar.
        if (mensaje==null){
            mostrarMensaje("Estado", "Eliminado", Alert.AlertType.INFORMATION);
        }else{
            mostrarMensaje("Error:", mensaje, Alert.AlertType.INFORMATION);
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
