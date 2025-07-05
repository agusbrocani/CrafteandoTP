package ar.edu.unlam.crafteando.Jugador;

import java.util.*;
import java.util.stream.Collectors;
import ar.edu.unlam.crafteando.Clases.*;

public class Inventario {
    private final Map<ObjetoComponente, Integer> objetos;
    //private final Map<String, MesaCrafteo> mesasPorTipo = new HashMap<>();

    public Inventario() {
        objetos = new HashMap<>();
    }

    
    // hace la copia
    public Inventario(Inventario otro) {
        this.objetos = new HashMap<>();
        for (Map.Entry<ObjetoComponente, Integer> entry : otro.objetos.entrySet()) {
            ObjetoComponente claveOriginal = entry.getKey();
            Integer cantidad = entry.getValue();

            // Si tus objetos son inmutables, no hace falta clonar, podés usar la misma instancia
            this.objetos.put(claveOriginal, cantidad);
        }
    }
    
    public void ver() {
        objetos.forEach((obj, cantidad) ->
            System.out.println("[" + obj.getNombre() + " - " + cantidad + "]")
        );
    }

    public Map<ObjetoComponente, Integer> getObjetos() {
        return objetos;
    }

    public void quitar(ObjetoComponente objeto, int cantidad) {
        if (!objetos.containsKey(objeto)) {
            throw new IllegalArgumentException("El objeto no está en el inventario.");
        }

        int cantidadActual = objetos.get(objeto);
        if (cantidad > cantidadActual) {
            throw new IllegalArgumentException("Cantidad insuficiente. Actual: " + cantidadActual + ", Solicitada: " + cantidad);
        }

        int nuevaCantidad = cantidadActual - cantidad;
        if (nuevaCantidad == 0) {
            objetos.remove(objeto);
        } else {
            objetos.put(objeto, nuevaCantidad);
        }
    }

    public void agregar(ObjetoComponente objeto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a cero.");
        }

        objetos.merge(objeto, cantidad, Integer::sum);
    }

    public Integer obtenerCantidad(ObjetoComponente objeto) {
        if (!objetos.containsKey(objeto)) {
            throw new IllegalArgumentException("El objeto no está en el inventario.");
        }
        return objetos.get(objeto);
    }

    public boolean contiene(ObjetoComponente objeto) {
        return objetos.containsKey(objeto);
    }

    public void guardarComoJson(String rutaArchivo) {
        List<EntradaInventario> lista = objetos.entrySet().stream()
                .map(entry -> new EntradaInventario(entry.getKey().getNombre(), entry.getValue()))
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

