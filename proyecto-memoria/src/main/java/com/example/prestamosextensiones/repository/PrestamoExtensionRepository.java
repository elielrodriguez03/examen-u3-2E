package com.example.prestamosextensiones.repository;

import com.example.prestamosextensiones.model.PrestamoExtension;
import java.util.ArrayList;
import java.util.List;

public class PrestamoExtensionRepository {

    private List<PrestamoExtension> lista = new ArrayList<>();

    public List<PrestamoExtension> getLista() {
        return lista;
    }
}