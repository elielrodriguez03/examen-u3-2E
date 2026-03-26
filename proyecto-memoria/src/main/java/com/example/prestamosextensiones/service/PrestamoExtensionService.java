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
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()){
            return null;
        }
        // 2. Validar que area no esté vacía.
        if (area == null || area.trim().isEmpty()){
            return null;
        }
        // 3. Validar que bloque no sea null ni vacío.
        if (bloque == null){
            return null;
        }
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        if (repository.findByNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro con ese nombre.";
        }
        // 5. Crear un objeto PrestamoExtension.
        PrestamoExtension prestamoExtension = new PrestamoExtension(nombreSolicitante.trim(), area.trim(), bloque.trim());
        // 6. Guardarlo usando repository.save(...).
        repository.save(prestamoExtension);
        // 7. Si todo sale bien, regresar null.
        return null;
        // 8. Si algo falla, regresar un mensaje de error.

    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String nuevaArea, String nuevoBloque) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "No se ha seleccionado ningún registro para actualizar.";
        }
        // 2. Validar los nuevos datos: nuevoNombre, nuevaArea y nuevoBloque.
        // 3. Buscar el registro original con repository.findByNombreSolicitante(nombreOriginal).
        PrestamoExtension registroExistente = repository.findByNombreSolicitante(nombreOriginal.trim());
        // 4. Si no existe, regresar mensaje de error.
        if (registroExistente == null) {
            return "El registro original no se esta en la lista";
        }

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return "El nuevo nombre del alumno no puede estar vacío.";
        }
        if (nuevaArea == null || nuevaArea.trim().isEmpty()) {
            return "El area no puede estar vacia";
        }
        if (nuevoBloque == null || nuevoBloque.trim().isEmpty()) {
            return "Seleccione un nuevo bloque";
        }
        // 5. Si el nombre cambió, validar que el nuevoNombre no esté repetido.
        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre.trim())) {
            if (repository.findByNombreSolicitante(nuevoBloque.trim()) != null) {
                return "El nuevo nombre que intentas asignar ya está registrado para otro alumno.";
            }
        }
        // 6. Si todo está bien, modificar el mismo objeto encontrado:
        //      registro.setNombreSolicitante(...);
        //      registro.setArea(...);
        //      registro.setBloque(...);

        registroExistente.setNombreSolicitante(nuevoNombre.trim());
        registroExistente.setArea(nuevaArea.trim());
        registroExistente.setBloque(nuevoBloque);
        // 7. Regresar null si la actualización fue correcta.
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que el nombre no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre no puede estar vacio";
        }
        // 2. Usar repository.deleteByNombreSolicitante(...).
        boolean eliminado = repository.deleteByNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) {
            return "No se encontró ningún registro con ese nombre para eliminar";
        }
        // 3. Si elimina correctamente, regresar null.
        return null;
        // 4. Si no existe, regresar un mensaje de error.
    }
}
