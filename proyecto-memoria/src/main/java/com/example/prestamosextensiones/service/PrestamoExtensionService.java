package com.example.prestamosextensiones.service;

import com.example.prestamosextensiones.model.PrestamoExtension;
import com.example.prestamosextensiones.repository.PrestamoExtensionRepository;

import java.util.List;

public class PrestamoExtensionService {

    private PrestamoExtensionRepository repo = new PrestamoExtensionRepository();

    public List<PrestamoExtension> obtenerTodos() {
        return repo.getLista();
    }

    public boolean agregar(PrestamoExtension p) {
        for (PrestamoExtension e : repo.getLista()) {
            if (e.getNombre().equalsIgnoreCase(p.getNombre())) {
                return false;
            }
        }
        repo.getLista().add(p);
        return true;
    }

    public boolean actualizar(String nombreOriginal, PrestamoExtension nuevo) {
        for (PrestamoExtension e : repo.getLista()) {

            if (e.getNombre().equalsIgnoreCase(nombreOriginal)) {

                // validar duplicado
                for (PrestamoExtension otro : repo.getLista()) {
                    if (!otro.getNombre().equalsIgnoreCase(nombreOriginal) &&
                            otro.getNombre().equalsIgnoreCase(nuevo.getNombre())) {
                        return false;
                    }
                }

                e.setNombre(nuevo.getNombre());
                e.setArea(nuevo.getArea());
                e.setBloque(nuevo.getBloque());

                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String nombre) {
        return repo.getLista().removeIf(e ->
                e.getNombre().equalsIgnoreCase(nombre));
    }
}