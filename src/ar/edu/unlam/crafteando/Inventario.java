package ar.edu.unlam.crafteando;

import java.util.*;
import java.util.stream.Collectors;

public class Inventario {
    private final Map<String, Integer> objetos;
    private final Recetario recetario;
    //private final Map<String, MesaCrafteo> mesasPorTipo = new HashMap<>();

    public Inventario() {
        this.objetos = new HashMap<>();
        this.recetario = new Recetario();
    }
    
    public Inventario(Recetario recetario) { // Podriamos obligar a que manden un recetario si o si
    	this.objetos = new HashMap<>();
    	this.recetario = recetario;
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
        
        /*
        if (objeto instanceof MesaCrafteo mesa) { // puede ser un flag directamente en el objeto, si es que es mesa de crafteo
            recetario.notificarNuevaMesa(mesa); // llamada directa
        } 
        */
    }

    public Integer obtenerCantidad(String nombre) {
        if (!objetos.containsKey(nombre)) {
            throw new IllegalArgumentException("El objeto no está en el inventario.");
        }
        else
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

