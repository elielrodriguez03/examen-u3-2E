package com.example.prestamosextensiones.service;

import com.example.prestamosextensiones.model.PrestamoExtension;
import com.example.prestamosextensiones.repository.PrestamoExtensionRepository;
import javafx.scene.control.Alert;

import java.util.List;

public class PrestamoExtensionService {

    private final PrestamoExtensionRepository repository = new PrestamoExtensionRepository();

    private final String[] bloques = {"Bloque A", "Bloque B", "Bloque C", "Laboratorio"};

    public String[] obtenerBloques() {
        return bloques;
    }

    public List<PrestamoExtension> obtenerTodos() {
        return repository.findAll();
    }

    public PrestamoExtension buscarPorNombreSolicitante(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return null;
        }

        return repository.findByNombreSolicitante(nombreSolicitante.trim());
    }

   public String agregar(String nombreSolicitante, String area, String bloque) {
       if (nombreSolicitante.isEmpty() || area.isEmpty() || bloque == null) {
           mostrarMensaje("Pendiente", "Completa la lógica de Agregar", Alert.AlertType.INFORMATION);
       }else{
           return "Datos registrados";
       }

        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        // 2. Validar que area no esté vacía.
        // 3. Validar que bloque no sea null ni vacío.
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        // 5. Crear un objeto PrestamoExtension.
        // 6. Guardarlo usando repository.save(...).
        // 7. Si todo sale bien, regresar null.
        // 8. Si algo falla, regresar un mensaje de error.
       return "";

    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String nuevaArea, String nuevoBloque) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        // 2. Validar los nuevos datos: nuevoNombre, nuevaArea y nuevoBloque.
        // 3. Buscar el registro original con repository.findByNombreSolicitante(nombreOriginal).
        // 4. Si no existe, regresar mensaje de error.
        // 5. Si el nombre cambió, validar que el nuevoNombre no esté repetido.
        // 6. Si todo está bien, modificar el mismo objeto encontrado:
        //      registro.setNombreSolicitante(...);
        //      registro.setArea(...);
        //      registro.setBloque(...);
        // 7. Regresar null si la actualización fue correcta.
        return "Falta implementar actualizar en el service";
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que el nombre no esté vacío.
        // 2. Usar repository.deleteByNombreSolicitante(...).
        // 3. Si elimina correctamente, regresar null.
        // 4. Si no existe, regresar un mensaje de error.
        return "Falta implementar eliminar en el service";
    }
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
