package com.example.prestamosextensiones.service;

import com.example.prestamosextensiones.model.PrestamoExtension;
import com.example.prestamosextensiones.repository.PrestamoExtensionRepository;

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
        // 2. Validar que area no esté vacía.
        // 3. Validar que bloque no sea null ni vacío.
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        // 5. Crear un objeto PrestamoExtension.
        // 6. Guardarlo usando repository.save(...).
        // 7. Si todo sale bien, regresar null.
        // 8. Si algo falla, regresar un mensaje de error.
        if (nombreSolicitante == null || nombreSolicitante.isBlank()) {
            return "El nombre del solicitante es obligatorio.";
        }
        if (area == null || area.isBlank()) {
            return "El área es obligatoria.";
        }
        if (bloque == null || bloque.isBlank()) {
            return "El bloque es obligatorio.";
        }

        PrestamoExtension existente = repository.findByNombreSolicitante(nombreSolicitante.trim());
        if (existente != null) {
            return "Ya existe un registro con el nombre: " + nombreSolicitante;
        }

        PrestamoExtension nuevo = new PrestamoExtension(
                nombreSolicitante.trim(),
                area.trim(),
                bloque
        );
        repository.save(nuevo);

        return null;
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
        if (nombreOriginal == null || nombreOriginal.isBlank()) {
            return "No se ha seleccionado ningún registro para actualizar.";
        }

        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            return "El nuevo nombre del solicitante es obligatorio.";
        }
        if (nuevaArea == null || nuevaArea.isBlank()) {
            return "La nueva área es obligatoria.";
        }
        if (nuevoBloque == null || nuevoBloque.isBlank()) {
            return "El nuevo bloque es obligatorio.";
        }

        PrestamoExtension registro = repository.findByNombreSolicitante(nombreOriginal.trim());


        if (registro == null) {
            return "No se encontró el registro original: " + nombreOriginal;
        }

        boolean nombreCambio = !nombreOriginal.trim().equalsIgnoreCase(nuevoNombre.trim());
        if (nombreCambio) {
            PrestamoExtension repetido = repository.findByNombreSolicitante(nuevoNombre.trim());
            if (repetido != null) {
                return "Ya existe un registro con el nombre: " + nuevoNombre;
            }
        }


        registro.setNombreSolicitante(nuevoNombre.trim());
        registro.setArea(nuevaArea.trim());
        registro.setBloque(nuevoBloque);


        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que el nombre no esté vacío.
        // 2. Usar repository.deleteByNombreSolicitante(...).
        // 3. Si elimina correctamente, regresar null.
        // 4. Si no existe, regresar un mensaje de error.
        if (nombreSolicitante == null || nombreSolicitante.isBlank()) {
            return "El nombre del solicitante es obligatorio para eliminar.";
        }

        boolean eliminado = repository.deleteByNombreSolicitante(nombreSolicitante.trim());

        if (!eliminado) {
            return "No se encontró el registro con el nombre: " + nombreSolicitante;
        }
        return null;
    }
}