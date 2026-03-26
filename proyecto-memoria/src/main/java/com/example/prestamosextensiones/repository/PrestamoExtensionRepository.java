package com.example.prestamosextensiones.repository;

import com.example.prestamosextensiones.model.PrestamoExtension;

import java.util.ArrayList;
import java.util.List;

public class PrestamoExtensionRepository {

    private final List<PrestamoExtension> registros = new ArrayList<>();

    public List<PrestamoExtension> findAll() {
        return registros;
    }

    public void save(PrestamoExtension registro) {
        registros.add(registro);
        System.out.println(registro.toString());
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

    public boolean deleteByNombreSolicitante(int index) {
        if(index>=0){
            registros.remove(index);
            return true;
        }
        return false;
    }
}
