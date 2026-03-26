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

        PrestamoExtension solicitante = buscarPorNombreSolicitante(nombreSolicitante);
        if (solicitante != null) {
            return "Ya existe un registro de préstamo para ese solicitante";
        }

        String msg = validarDatos(nombreSolicitante, area, bloque);
        if (msg != null) { return msg;}

        PrestamoExtension prestamo = new PrestamoExtension(nombreSolicitante, area, bloque);
        repository.save(prestamo);

        return null;
    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String nuevaArea, String nuevoBloque) {

        PrestamoExtension solicitante = buscarPorNombreSolicitante(nombreOriginal);
        if (solicitante == null) {
            return "Solicitante válido no encontrado.";
        }

        String msg = validarDatos(nuevoNombre, nuevaArea, nuevoBloque);
        if (msg != null) { return msg;}

        if (!nuevoNombre.equals(nombreOriginal)){
            PrestamoExtension prestamo = buscarPorNombreSolicitante(nuevoNombre);
            if (prestamo != null){
                return "Ya existe un registro de préstamo a ese solicitante";
            }
        }

        solicitante.setNombreSolicitante(nuevoNombre);
        solicitante.setArea(nuevaArea);
        solicitante.setBloque(nuevoBloque);
        return null;
    }

    public String eliminar(String nombreSolicitante) {

        PrestamoExtension solicitante = buscarPorNombreSolicitante(nombreSolicitante);
        if (solicitante == null){
            return "Solicitante válido no seleccionado";
        }
        repository.deleteByNombreSolicitante(nombreSolicitante);
        return null;
    }

    private String validarDatos(String nombreSolicitante, String area, String bloque){
        if (nombreSolicitante.isBlank()) {
            return "El nombre no puede estar vacío.";
        }

        if (area.isBlank()) {
            return "El área no puede estar vacía";
        }

        if (bloque == null) {
            return "El bloque no puede estar vacío.";
        }

        return null;
    }

}
