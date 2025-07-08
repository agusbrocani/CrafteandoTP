package ar.edu.unlam.crafteando.Jugador;

import java.util.*;
import java.util.stream.Collectors;
import ar.edu.unlam.crafteando.Clases.*;

public class Inventario {
    private final Map<ObjetoComponente, Integer> objetos;

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
        	System.out.println("El objeto no está en el inventario.");
        	return;
        }

        int cantidadActual = objetos.get(objeto);
        if (cantidad > cantidadActual) {
            System.out.println("Cantidad insuficiente. Actual: " + cantidadActual + ", Solicitada: " + cantidad);
            return;
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
        	System.out.println("Cantidad debe ser mayor a cero.");
        	return;
        }

        objetos.merge(objeto, cantidad, Integer::sum);
    }
    
    public void agregar(MesaCrafteo mesa) {
        objetos.merge(mesa, 1, Integer::sum);
    }

    public Integer obtenerCantidad(ObjetoComponente objeto) {
        if (!objetos.containsKey(objeto)) {
        	System.out.println("El objeto no está en el inventario.");
        	return 0;
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
    
}

