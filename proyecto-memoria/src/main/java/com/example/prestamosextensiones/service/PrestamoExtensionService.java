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
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre del solicitante es obligatorio.";
        }
        if (area == null || area.trim().isEmpty()) {
            return "El área es obligatoria.";
        }
        if (bloque == null || bloque.trim().isEmpty()) {
            return "Debe seleccionar un bloque.";
        }

        if (repository.findByNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro con ese nombre.";
        }

        PrestamoExtension nuevo = new PrestamoExtension(nombreSolicitante.trim(), area.trim(), bloque.trim());
        repository.save(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String nuevaArea, String nuevoBloque) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "Debe seleccionar o buscar un registro primero.";
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return "El nuevo nombre es obligatorio.";
        }
        if (nuevaArea == null || nuevaArea.trim().isEmpty()) {
            return "El área es obligatoria.";
        }
        if (nuevoBloque == null || nuevoBloque.trim().isEmpty()) {
            return "Debe seleccionar un bloque.";
        }

        PrestamoExtension registro = repository.findByNombreSolicitante(nombreOriginal.trim());
        if (registro == null) {
            return "No se encontró el registro original.";
        }

        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre.trim()) &&
                repository.findByNombreSolicitante(nuevoNombre.trim()) != null) {
            return "Ya existe un registro con el nuevo nombre.";
        }

        registro.setNombreSolicitante(nuevoNombre.trim());
        registro.setArea(nuevaArea.trim());
        registro.setBloque(nuevoBloque.trim());

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Debe indicar el nombre del solicitante.";
        }

        boolean eliminado = repository.deleteByNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) {
            return "No se encontró el registro a eliminar.";
        }

        return null;
    }

    public List<String> obtenerNombresSolicitantes() {
        return repository.findAll()
                .stream()
                .map(PrestamoExtension::getNombreSolicitante)
                .toList();
    }

}
