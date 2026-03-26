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
        String mensaje = null;
        // 1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante.isBlank()){mensaje="Nombre vacio";}

        // 2. Validar que area no esté vacía.
        if (area.isBlank()) {mensaje="Area vacia";}

        // 3. Validar que bloque no sea null ni vacío.
        if (bloque==null || bloque.isBlank()){mensaje="Bloque vacio";}

        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        if(buscarPorNombreSolicitante(nombreSolicitante)!=null){mensaje="Este nombre ya esta registrado";}

        // 5. Crear un objeto PrestamoExtension.
        PrestamoExtension registro=new PrestamoExtension(nombreSolicitante,area,bloque);

        // 6. Guardarlo usando repository.save(...).
        repository.save(registro);

        // 7. Si todo sale bien, regresar null.
        if (mensaje == null) {
            return null;
        } else {
            // 8. Si algo falla, regresar un mensaje de error.
            return mensaje;
        }
    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String nuevaArea, String nuevoBloque) {
        String mensaje=null;
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if (nombreOriginal==null || nombreOriginal.isBlank()){mensaje="Nombre vacio";}

        // 2. Validar los nuevos datos: nuevoNombre, nuevaArea y nuevoBloque.
        if (nuevoNombre==null || nuevoNombre.isBlank()){mensaje="Nombre vacio";}
        if (nuevaArea==null || nuevaArea.isBlank()) {mensaje="Area vacia";}
        if (nuevoBloque==null || nuevoBloque.isBlank()){mensaje="Bloque vacio";}
        PrestamoExtension registro=new PrestamoExtension(nuevoNombre,nuevaArea,nuevoBloque);

        // 3. Buscar el registro original con repository.findByNombreSolicitante(nombreOriginal).
        registro = (repository.findByNombreSolicitante(nombreOriginal));

        // 4. Si no existe, regresar mensaje de error.
        if (registro==null){mensaje="No existe este nombre";}

        // 5. Si el nombre cambió, validar que el nuevoNombre no esté repetido.
        if (registro!=null){
            if(buscarPorNombreSolicitante(nuevoNombre)!=null){mensaje="Este nombre ya esta registrado";}
            else {
                mensaje=null;
            }
        }
        // 6. Si todo está bien, modificar el mismo objeto encontrado:
        //      registro.setNombreSolicitante(...);
        //      registro.setArea(...);
        //      registro.setBloque(...);
        if (mensaje ==null){
            registro.setNombreSolicitante(nuevoNombre);
            registro.setArea(nuevaArea);
            registro.setBloque(nuevoBloque);
            // 7. Regresar null si la actualización fue correcta.
            return mensaje;
        }else {
            return mensaje;
        }

    }

    public String eliminar(String nombreSolicitante) {
        String mensaje;
        // TODO:
        // 1. Validar que el nombre no esté vacío.
        if ((nombreSolicitante !=null) || !nombreSolicitante.isBlank() ){
            // 2. Usar repository.deleteByNombreSolicitante(...).
            repository.deleteByNombreSolicitante(nombreSolicitante);
            // 3. Si elimina correctamente, regresar null.
            mensaje=null;
        }else {
            mensaje="Nombre vacio";
        }

        // 4. Si no existe, regresar un mensaje de error.
        return mensaje;
    }
}
