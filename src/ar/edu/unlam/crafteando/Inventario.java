package ar.edu.unlam.crafteando;

import java.util.*;
import java.util.stream.Collectors;

public class Inventario {
    private final Map<String, Integer> objetos;
    //private final Map<String, MesaCrafteo> mesasPorTipo = new HashMap<>();

    public Inventario() {
        objetos = new HashMap<>();
    }
    
    // Constructor copia
    public Inventario(Inventario otro) {
        this.objetos = new HashMap<>(otro.objetos);
    }

    public void ver() {
        objetos.forEach((nombre, cantidad) ->
            System.out.println("[" + nombre + " - " + cantidad + "]")
        );
    }

    public Map<ObjetoComponente, Integer> getObjetos() {
        // Map simulado
        return objetos.entrySet().stream()
            .collect(Collectors.toMap(
                e -> new ObjetoBasico(e.getKey()), // todos como ObjetoBasico genérico
                Map.Entry::getValue
            ));
    }

    public void quitar(String nombre, int cantidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido.");
        }
        
        if (!objetos.containsKey(nombre)) {
            throw new IllegalArgumentException("El objeto no está en el inventario.");
        }

        int cantidadActual = objetos.get(nombre);
        if (cantidad > cantidadActual) {
            throw new IllegalArgumentException("Cantidad insuficiente. Actual: " + cantidadActual +
                    ", Solicitada: " + cantidad);
        }

        int nuevaCantidad = cantidadActual - cantidad;
        if (nuevaCantidad == 0) {
            objetos.remove(nombre);
        } else {
            objetos.put(nombre, nuevaCantidad);
        }
    }

    public void agregar(String nombre, int cantidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de objeto inválido.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a cero.");
        }

        objetos.merge(nombre, cantidad, Integer::sum);
    }

    /*
    public Integer obtenerCantidad(String nombre) {
        if (!objetos.containsKey(nombre)) {
            throw new IllegalArgumentException("El objeto no está en el inventario.");
        }
        else
        return objetos.get(nombre);
    }
    */
    
    // LA COMENTE PORQUE NECESITO EVALUAR YO LA CANTIDAD SI NO LA TENGO, 
    //SI ME TRA EXCEPCION ME ROMPE EL PROGRAMA
    public Integer obtenerCantidad(String nombre) {

    	// notar que el get ya devuelve null si no se encuentra la clave 
        return objetos.get(nombre);
    }

    public boolean contiene(String nombre) {
        return objetos.containsKey(nombre);
    }

    public void guardarComoJson(String rutaArchivo) {
        List<EntradaInventario> lista = objetos.entrySet().stream()
                .map(entry -> new EntradaInventario(entry.getKey(), entry.getValue()))
                .toList();
        GestorJson.guardar(lista, rutaArchivo);
    }
    
    // ========= LÓGICA DE MESAS ========================0
    /*
    public void agregarMesa(MesaCrafteo mesa) {
        if (mesa == null) throw new IllegalArgumentException("Mesa nula");

        String tipo = mesa.getNombre().toLowerCase();
        if (mesasPorTipo.containsKey(tipo)) {
            throw new IllegalStateException("Ya existe una mesa de tipo: " + tipo);
        }
        mesasPorTipo.put(tipo, mesa);
    }

    public void quitarMesa(String tipo) {
        mesasPorTipo.remove(tipo.toLowerCase());
    }

    public List<String> getTiposDeMesa() {
        return new ArrayList<>(mesasPorTipo.keySet());
    }
    */
    
}

