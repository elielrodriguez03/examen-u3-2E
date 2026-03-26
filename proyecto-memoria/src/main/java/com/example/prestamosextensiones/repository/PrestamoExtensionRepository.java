package com.example.prestamosextensiones.repository;

import com.example.prestamosextensiones.model.PrestamoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrestamoExtensionRepository {

    private final List<PrestamoExtension> registros = new ArrayList<>();

    public List<PrestamoExtension> findAll() {
        return registros;
    }

    public void save(Objects registro) {
        registros.add(registro);
    }

    public PrestamoExtension findByNombreSolicitante(String nombreSolicitante) {
        for (int i = 0; i < registros.size(); i++) {
            PrestamoExtension actual = registros.get(i);

            if (actual.getNombreSolicitante().equalsIgnoreCase(nombreSolicitante)) {
                return actual;
            }
        }
        return null;
    }

    public boolean deleteByNombreSolicitante(String nombreSolicitante) {
        for (int i = 0; i < registros.size(); i++) {
            PrestamoExtension actual = registros.get(i);

            if (actual.getNombreSolicitante().equalsIgnoreCase(nombreSolicitante)) {
                registros.remove(i);
                return true;
            }
        }
        return false;
    }
}
