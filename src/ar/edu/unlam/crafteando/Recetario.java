package ar.edu.unlam.crafteando;

import java.util.*;
import java.util.function.Function;
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
    
    public void eliminarReceta(Receta receta) {
        recetas.remove(receta);
    }

    // ==== MÉTODOS PRIVADOS PARA REUTILIZAR ====

    public List<Receta> buscarRecetasPorNombre(String nombreObjetoCompuesto) {
        return recetas.stream()
            .filter(r -> r.getNombre().equalsIgnoreCase(nombreObjetoCompuesto))
            .collect(Collectors.toList());
    }
    
    
    public List<Receta> buscarRecetasPorNombre(String nombreObjetoCompuesto, List<String> tiposDeMesasDisponibles) {
        Set<String> tipos = tiposDeMesasDisponibles.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        return recetas.stream()
            .filter(r -> r.getNombre().equalsIgnoreCase(nombreObjetoCompuesto))
            .filter(r -> r.getTipo().equalsIgnoreCase("Básica") 
                       || tipos.contains(r.getTipo().toLowerCase()))
            .collect(Collectors.toList());
    }
    
    private  ObjetoComponente construirObjetoDesdeReceta(Receta receta) {
        String nombre = receta.getNombre();
        ObjetoCompuesto compuesto = new ObjetoCompuesto(nombre);

        receta.getIngredientes().forEach((ingrediente, cantidad) -> {
            ObjetoComponente subobjeto;

            List<Receta> subRecetas = buscarRecetasPorNombre(ingrediente.getNombre());
            if (!subRecetas.isEmpty()) {
                // Usar recursión con la primera receta disponible
                subobjeto = construirObjetoDesdeReceta(subRecetas.get(0));
            } else {
                // No tiene receta → básico
                subobjeto = new ObjetoBasico(ingrediente.getNombre());
            }

            for (int i = 0; i < cantidad; i++) {
                compuesto.agregar(subobjeto);
            }
        });

        return compuesto;
    }

    // ==== RECETAS DE PRIMER NIVEL ====

    public List<Map<ObjetoComponente, Integer>> obtenerReceta(String nombreObjetoCompuesto) {
        return buscarRecetasPorNombre(nombreObjetoCompuesto).stream()
                .map(Receta::getIngredientes)
                .collect(Collectors.toList());
    }

    public void mostrarReceta(String nombreObjetoCompuesto, List<String> tiposDeMesaDisponibles) {
        List<Receta> recetasFiltradas = buscarRecetasPorNombre(nombreObjetoCompuesto, tiposDeMesaDisponibles);

        System.out.println("\n---------------------------------------------------");
        System.out.println("Recetas disponibles para: " + nombreObjetoCompuesto);

        for (int i = 0; i < recetasFiltradas.size(); i++) {
            Receta receta = recetasFiltradas.get(i);
            System.out.println("\n=== Opción " + (i + 1) + " ===");

            ObjetoComponente construido = construirObjetoDesdeReceta(receta);
            construido.mostrarConstruccion(true);
            System.out.println("Tiempo: " + receta.getTiempoEnSegundos() + " segundos");
        }
    }
    
    public void mostrarReceta(String nombreObjetoCompuesto) {
        List<Receta> recetasFiltradas = buscarRecetasPorNombre(nombreObjetoCompuesto);

        System.out.println("\n---------------------------------------------------");
        System.out.println("Recetas disponibles para: " + nombreObjetoCompuesto);
        
        for (int i = 0; i < recetasFiltradas.size(); i++) {
            Receta receta = recetasFiltradas.get(i);
            System.out.println("\n=== Opción " + (i + 1) + " ===");

            ObjetoComponente construido = construirObjetoDesdeReceta(receta);
            
            construido.mostrarConstruccion(true);
            System.out.println("Tiempo: " + receta.getTiempoEnSegundos() + " segundos");
        }
    }
    
    public void mostrarUnaReceta(String nombreObjetoCompuesto) {
        List<Receta> recetasFiltradas = buscarRecetasPorNombre(nombreObjetoCompuesto);

        System.out.println("\n---------------------------------------------------");
        System.out.println("Receta para: " + nombreObjetoCompuesto);
        
        Receta receta = recetasFiltradas.getFirst();
        ObjetoComponente construido = construirObjetoDesdeReceta(receta);
        
        construido.mostrarConstruccion(true);
        System.out.println("Tiempo: " + receta.getTiempoEnSegundos() + " segundos");
    }


    // ==== RECETA DESDE CERO ====
   
    public List<Map<ObjetoBasico, Integer>> obtenerRecetaDesdeCero(String nombreObjetoCompuesto) {
        List<Receta> variantes = buscarRecetasPorNombre(nombreObjetoCompuesto);

        if (variantes.isEmpty()) {
            throw new IllegalArgumentException("No hay recetas para: " + nombreObjetoCompuesto);
        }

        List<Map<ObjetoBasico, Integer>> resultados = new ArrayList<>();

        for (Receta receta : variantes) {
            ObjetoComponente objetoConstruido = construirObjetoDesdeReceta(receta);
            resultados.add(objetoConstruido.descomponerEnBasicos());
        }

        return resultados;
    }

    public void mostrarRecetaDesdeCero(String nombreObjetoCompuesto, List<String> tiposDeMesaDisponibles) {
        List<Receta> variantes = buscarRecetasPorNombre(nombreObjetoCompuesto, tiposDeMesaDisponibles);

        if (variantes.isEmpty()) {
            System.out.println("No hay recetas disponibles para: " + nombreObjetoCompuesto);
            return;
        }

        // Mapa para cálculo de tiempos y posible recursividad
        Map<String, Receta> recetasPorNombre = recetas.stream()
            .collect(Collectors.toMap(Receta::getNombre, Function.identity(), (r1, r2) -> r1)); // (r1, _)

        for (int i = 0; i < variantes.size(); i++) {
            Receta receta = variantes.get(i);
            System.out.println("\n=== Opción " + (i + 1) + " ===");

            ObjetoComponente objetoConstruido = construirObjetoDesdeReceta(receta);

            objetoConstruido.mostrarConstruccion(false); // false = mostrar todos los niveles

            int tiempo = receta.calcularTiempoTotal(recetasPorNombre);
            System.out.println("Tiempo total de crafteo: " + tiempo + " segundos");
        }
    }
    
    public void mostrarRecetaDesdeCero(String nombreObjetoCompuesto) {
        List<Receta> variantes = buscarRecetasPorNombre(nombreObjetoCompuesto);

        if (variantes.isEmpty()) {
            System.out.println("No hay recetas para: " + nombreObjetoCompuesto);
            return;
        }

        Map<String, Receta> recetasPorNombre = recetas.stream()
            .collect(Collectors.toMap(Receta::getNombre, Function.identity(), (r1,r2) -> r1));

        for (int i = 0; i < variantes.size(); i++) {
            Receta receta = variantes.get(i);
            System.out.println("\n=== Opción " + (i + 1) + " ===");

            ObjetoComponente objetoConstruido = construirObjetoDesdeReceta(receta);

            objetoConstruido.mostrarConstruccion(false); // false = mostrar todos los niveles

            int tiempo = receta.calcularTiempoTotal(recetasPorNombre);
            System.out.println("Tiempo total de crafteo: " + tiempo + " segundos");
        }
    }


    // ==== GETTERS ====
    
    public List<Receta> getRecetas() {
        return Collections.unmodifiableList(recetas);
    }
    
    // ==== VALIDACIÓN ====
    
    public void validarRecetas() {
        recetas.forEach(Receta::validar);
    }
    
    public boolean contieneReceta(String nombreObjetoCompuesto) {
        return recetas.stream()
            .anyMatch(r -> r.getNombre().equalsIgnoreCase(nombreObjetoCompuesto));
    }

}