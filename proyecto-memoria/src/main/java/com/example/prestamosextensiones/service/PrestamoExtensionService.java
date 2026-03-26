package com.example.prestamosextensiones.service;

import com.example.prestamosextensiones.controller.MainController;
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
        // TODO:

        // 1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre es obligatorio.";
        }

        // 2. Validar que area no esté vacía.
        if (area == null || area.trim().isEmpty()) {
            return "Debes especificar el área.";
        }

        // 3. Validar que bloque no sea null ni vacío.
        if (bloque == null || bloque.trim().isEmpty()) {
            return "Selecciona un bloque.";
        }

        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.


        // 5. Crear un objeto PrestamoExtension.
        PrestamoExtension prestamoExtension = new PrestamoExtension(nombreSolicitante, area, bloque);

        // 6. Guardarlo usando repository.save(...).
        repository.save(prestamoExtension);

        // 7. Si todo sale bien, regresar null.
        // 8. Si algo falla, regresar un mensaje de error.
        return null;
    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String nuevaArea, String nuevoBloque) {
        // TODO:

        // 1. Validar que nombreOriginal no sea null ni vacío.
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "El nombre es obligatorio.";
        }

        // 2. Validar los nuevos datos: nuevoNombre, nuevaArea y nuevoBloque.
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return "El nombre es obligatorio.";
        }

        if (nuevaArea == null || nuevaArea.trim().isEmpty()) {
            return "Debes especificar el área.";
        }

        if (nuevoBloque == null || nuevoBloque.trim().isEmpty()) {
            return "Selecciona un bloque.";
        }

        // 3. Buscar el registro original con repository.findByNombreSolicitante(nombreOriginal).

        repository.findByNombreSolicitante(nombreOriginal);

        // 4. Si no existe, regresar mensaje de error.

        if (repository.findByNombreSolicitante(nombreOriginal) == null) {
            return "Error, el registro original no existe";
        }

        // 5. Si el nombre cambió, validar que el nuevoNombre no esté repetido.

        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre)) {
            if (repository.findByNombreSolicitante(nuevoNombre) != null) {
                return "El nombre ya existe.";
            }
        }

        // 6. Si todo está bien, modificar el mismo objeto encontrado:

        PrestamoExtension registro = new PrestamoExtension(nuevoNombre, nuevoBloque, nuevaArea);

        //      registro.setNombreSolicitante(...);
        //      registro.setArea(...);
        //      registro.setBloque(...);
        registro.setNombreSolicitante(nuevoNombre);
        registro.setNombreSolicitante(nuevaArea);
        registro.setNombreSolicitante(nuevoBloque);

        // 7. Regresar null si la actualización fue correcta.
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que el nombre no esté vacío.
        // 2. Usar repository.deleteByNombreSolicitante(...).
        // 3. Si elimina correctamente, regresar null.
        // 4. Si no existe, regresar un mensaje de error.
        return "Falta implementar eliminar en el service";
    }
}
