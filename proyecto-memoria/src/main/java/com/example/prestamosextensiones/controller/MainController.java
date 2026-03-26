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
     public String nombreOriginal;

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
        int i = 0;
        while (i < bloques.length) {
            cbBloque.getItems().add(bloques[i]);
            i++;
        }
    }

    @FXML
    public void agregar() {
        // TODO:
        // 1. Leer txtNombreSolicitante, txtArea y cbBloque.
        String nombre = this.txtNombreSolicitante.getText();
        String Area = this.txtArea.getText();
        String bloque = (String)this.cbBloque.getValue();

        // 2. Mandar esos datos al service.
        this.service.agregar(nombre, Area, bloque);
        // 3. Si el service regresa un mensaje, mostrar error.
        this.txtNombreSolicitante.setText(this.txtNombreSolicitante.getText());
        this.txtArea.setText(this.txtArea.getText());
        this.cbBloque.setValue((String)this.cbBloque.getValue());
        this.mostrarMensaje("error", "Hay un error",Alert.AlertType.ERROR);
        this.txtNombreSolicitante.clear();
        this.txtArea.clear();
        this.cbBloque.setValue(null);
        this.lvRegistros.getSelectionModel().clearSelection();
        this.nombreOriginal = null;
        // 4. Si regresa null, refrescar la lista y limpiar.
        mostrarMensaje("Pendiente", "Completa la lógica de Agregar", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void buscar() {
        // Método de ejemplo resuelto.
        PrestamoExtension registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        } else {
        this.txtNombreSolicitante.setText(registro.getNombreSolicitante());
        this.txtArea.setText(registro.getArea());
        this.cbBloque.setValue(registro.getBloque());
        this.nombreOriginal = registro.getNombreSolicitante();
    }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtArea.setText(registro.getArea());
        cbBloque.setValue(registro.getBloque());

        // Este valor es clave para UPDATE.
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        String nuevoNombre = this.txtNombreSolicitante.getText();
        String nuevoLibro = this.txtArea.getText();
        String nuevoTurno = (String)this.cbBloque.getValue();
        String mensaje = this.service.actualizar(this.nombreOriginal, nuevoNombre, nuevoLibro, nuevoTurno);
        if (mensaje == null) {
            this.actualizarLista();
            this.txtArea.clear();
        }

        this.mostrarMensaje("Pendiente", "Completa la lógica de Actualizar", Alert.AlertType.INFORMATION);

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
        mostrarMensaje("Pendiente", "Completa la lógica de Actualizar", Alert.AlertType.INFORMATION);
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
        mostrarMensaje("Pendiente", "Completa la lógica de Eliminar", Alert.AlertType.INFORMATION);
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

        int i = 0;
        while (i < registros.size()) {
            lvRegistros.getItems().add(registros.get(i).toString());
            i++;
        }
    }

    private void cargarSeleccion(String textoSeleccionado) {
        List<PrestamoExtension> registros = service.obtenerTodos();

        int i = 0;
        while (i < registros.size()) {
            PrestamoExtension actual = registros.get(i);

            if (actual.toString().equals(textoSeleccionado)) {
                txtNombreSolicitante.setText(actual.getNombreSolicitante());
                txtArea.setText(actual.getArea());
                cbBloque.setValue(actual.getBloque());
                nombreOriginal = actual.getNombreSolicitante();
                break;
            }
            i++;
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





