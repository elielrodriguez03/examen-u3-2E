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
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Agregue el nombre del Solicitante";
        }
        if (area == null || area.trim().isEmpty()) {
            return "Agregue el nombre del area";
        }
        if (bloque == null || bloque.trim().isEmpty()) {
            return "Seleccione un bloque";
        }
        if (repository.findByNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un alumno registrado con este nombre";
        }

        PrestamoExtension nuevoRegistro = new PrestamoExtension(nombreSolicitante.trim(), area.trim(), bloque);
        repository.save(nuevoRegistro);
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
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "Ingrese para contionuar";
        }
        PrestamoExtension registroExistente = repository.findByNombreSolicitante(nombreOriginal.trim());
        if (registroExistente == null) {
            return "El registro no se encuentra";
        }

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return "Agregue el nombre nuevo";
        }
        if (nuevaArea == null || nuevaArea.trim().isEmpty()) {
            return "Agregue su area";
        }
        if (nuevoBloque == null || nuevoBloque.trim().isEmpty()) {
            return "Seleccione su bloque";
        }

        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre.trim())) {
            if (repository.findByNombreSolicitante(nuevoNombre.trim()) != null) {
                return "Ya hay un solicitante con este nombre";
            }
        }
        return "Falta implementar actualizar en el service";
    }

    public String eliminar(String nombreSolicitante) {
        // 1. Validar que el nombre no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Ingrese el nombre del solicitante a eliminar";
        }

        // 2. Buscar si existe el registro
        PrestamoExtension registro = repository.findByNombreSolicitante(nombreSolicitante.trim());

        if (registro == null) {
            return "No se encontró el registro";
        }

        // 3. Eliminar usando el repository
        repository.deleteByNombreSolicitante(nombreSolicitante.trim());

        // 4. Si todo sale bien, regresar null
        return null;
    }
}
