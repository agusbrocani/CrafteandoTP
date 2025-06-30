package ar.edu.unlam.crafteando;

import java.util.*;
import java.util.stream.Collectors;

public class Recetario {
    private final List<Receta> recetas;

    public Recetario() {
        this.recetas = new ArrayList<>();
    }
    
    public void agregarReceta(Receta receta) {
        if (receta == null) {
            throw new IllegalArgumentException("Receta nula");
        }
        recetas.add(receta);
    }

    // ==== MÉTODOS PRIVADOS PARA REUTILIZAR ====

    private List<Receta> buscarRecetasPorNombre(String nombreObjetoCompuesto) {
        List<Receta> recetasEncontradas = recetas.stream()
                .filter(r -> r.getTipo().equalsIgnoreCase(nombreObjetoCompuesto))
                .collect(Collectors.toList());
        
        if (recetasEncontradas.isEmpty()) {
            throw new IllegalArgumentException("No se encontró receta para: " + nombreObjetoCompuesto);
        }
        return recetasEncontradas;
    }

    private Receta seleccionarPrimeraReceta(String nombreObjetoCompuesto) {
        return buscarRecetasPorNombre(nombreObjetoCompuesto).get(0);
    }
    
    private ObjetoComponente construirObjetoDesdeReceta(String nombre) {
        Receta receta = seleccionarPrimeraReceta(nombre);
        ObjetoCompuesto compuesto = new ObjetoCompuesto(nombre);

        receta.getIngredientes().forEach((componente, cantidad) -> {
            ObjetoComponente ingrediente;
            if (componente instanceof ObjetoBasico) {
                ingrediente = componente;
            } else {
                ingrediente = construirObjetoDesdeReceta(componente.getNombre()); // 👈 recursivo
            }

            for (int i = 0; i < cantidad; i++) {
                compuesto.agregar(ingrediente);
            }
        });

        return compuesto;
    }
   
    // ==== RECETAS DE PRIMER NIVEL ====

    public List<Map<ObjetoComponente, Integer>> obtenerTodasLasRecetas(String nombreObjetoCompuesto) {
        return buscarRecetasPorNombre(nombreObjetoCompuesto).stream()
                .map(Receta::getIngredientes)
                .collect(Collectors.toList());
    }

    public Map<ObjetoComponente, Integer> obtenerRecetaPrimerNivel(String nombreObjetoCompuesto) {
        return seleccionarPrimeraReceta(nombreObjetoCompuesto).getIngredientes();
    }

    public void mostrarReceta(String nombreObjetoCompuesto) {
        Map<ObjetoComponente, Integer> ingredientes = obtenerRecetaPrimerNivel(nombreObjetoCompuesto);
        System.out.println("Receta para: " + nombreObjetoCompuesto);
        ingredientes.forEach((objeto, cantidad) ->
            System.out.println("- " + objeto.getNombre() + " x" + cantidad)
        );
    }

    public void mostrarTodasLasRecetas(String nombreObjetoCompuesto) {
        List<Receta> todasLasRecetas = buscarRecetasPorNombre(nombreObjetoCompuesto);
        System.out.println("Todas las recetas para: " + nombreObjetoCompuesto);
        
        for (int i = 0; i < todasLasRecetas.size(); i++) {
            System.out.println("\n=== Opción " + (i + 1) + " ===");
            Receta receta = todasLasRecetas.get(i);
            receta.getIngredientes().forEach((objeto, cantidad) ->
                System.out.println("- " + objeto.getNombre() + " x" + cantidad)
            );
            System.out.println("Tiempo: " + receta.getTiempoEnSegundos() + " segundos");
        }
    }

    // ==== RECETA DESDE CERO ====


    public Map<ObjetoBasico, Integer> obtenerRecetaDesdeCero(String nombreObjetoCompuesto) {
        ObjetoComponente objetoConstruido = construirObjetoDesdeReceta(nombreObjetoCompuesto);
        return objetoConstruido.descomponerEnBasicos();
    }

    public void mostrarRecetaDesdeCero(String nombreObjetoCompuesto) {
        Map<ObjetoBasico, Integer> ingredientes = obtenerRecetaDesdeCero(nombreObjetoCompuesto);
        System.out.println("Receta completa (desde cero) para: " + nombreObjetoCompuesto);
        ingredientes.forEach((objeto, cantidad) ->
                System.out.println("- " + objeto.getNombre() + " x" + cantidad)
        );
    }

    // ==== MOSTRAR CONSTRUCCIÓN JERÁRQUICA ====

    public void mostrarConstruccion(String nombreObjetoCompuesto, boolean soloPrimerNivel) {
        List<Receta> recetasDisponibles = buscarRecetasPorNombre(nombreObjetoCompuesto);
        
        Receta recetaSeleccionada = recetasDisponibles.getFirst();
        
        // Creamos un objeto temporal con los ingredientes de la receta
        ObjetoCompuesto objetoParaMostrar = new ObjetoCompuesto(nombreObjetoCompuesto);
        
        recetaSeleccionada.getIngredientes().forEach((componente, cantidad) -> {
            for (int i = 0; i < cantidad; i++) {
                objetoParaMostrar.agregar(componente);
            }
        });
        
        System.out.println("Construcción para " + nombreObjetoCompuesto + ":");
        // Aprovechamos el polimorfismo: mostrarConstruccion() funciona recursivamente
        objetoParaMostrar.mostrarConstruccion(soloPrimerNivel);
    }

    // ==== UTILIDADES ====
    
    public int contarRecetasPara(String nombreObjetoCompuesto) {
        return buscarRecetasPorNombre(nombreObjetoCompuesto).size();
    }

    public List<String> getNombresObjetosConRecetas() {
        return recetas.stream()
                .map(Receta::getTipo)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // ==== GETTERS ====
    
    public List<Receta> getRecetas() {
        return Collections.unmodifiableList(recetas);
    }
    
    // ==== VALIDACIÓN ====
    
    public void validarRecetas() {
        recetas.forEach(Receta::validar);
    }
}