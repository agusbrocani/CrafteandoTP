package ar.edu.unlam.crafteando;

import java.util.*;

public class Recetario {
	private final List<Receta> recetas;

	public Recetario() {
		this.recetas = new ArrayList<>();
	}

    public void agregarReceta(Receta receta) {
        if (receta == null) throw new IllegalArgumentException("Receta nula");
        recetas.add(receta);
    }

    // ==== MÉTODO PRIVADO PARA REUTILIZAR ====

    private Receta buscarRecetaPorNombre(String nombreObjetoCompuesto) {
        return recetas.stream()
                .filter(r -> r.getTipo().equalsIgnoreCase(nombreObjetoCompuesto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró receta para: " + nombreObjetoCompuesto));
    }

    // ==== RECETA DE PRIMER NIVEL ====

    private Map<ObjetoComponente, Integer> obtenerRecetaPrimerNivel(String nombreObjetoCompuesto) {
        return buscarRecetaPorNombre(nombreObjetoCompuesto).getIngredientes();
    }

    public void mostrarReceta(String nombreObjetoCompuesto) {
        Map<ObjetoComponente, Integer> ingredientes = obtenerRecetaPrimerNivel(nombreObjetoCompuesto);
        System.out.println("Receta para: " + nombreObjetoCompuesto);
        ingredientes.forEach((objeto, cantidad) ->
            System.out.println("- " + objeto.getNombre() + " x" + cantidad)
        );
    }

    // ==== RECETA DESDE CERO ====

//    public Map<ObjetoBasico, Integer> obtenerRecetaDesdeCero(String nombreObjetoCompuesto) {
//        Receta receta = buscarRecetaPorNombre(nombreObjetoCompuesto);
//
//        ObjetoCompuesto objeto = new ObjetoCompuesto(nombreObjetoCompuesto);
//
//        receta.getIngredientes().forEach((componente, cantidad) -> {
//            ObjetoComponente nuevo = componente.crearConCantidad(cantidad);
//            objeto.agregar(nuevo);
//        });
//
//        return objeto.descomponerEnBasicos();
//    }

//    public void mostrarRecetaDesdeCero(String nombreObjetoCompuesto) {
//        Map<ObjetoBasico, Integer> ingredientes = obtenerRecetaDesdeCero(nombreObjetoCompuesto);
//        System.out.println("Receta completa (desde cero) para: " + nombreObjetoCompuesto);
//        ingredientes.forEach((objeto, cantidad) ->
//                System.out.println("- " + objeto.getNombre() + " x" + cantidad)
//        );
//    }

    public List<Receta> getRecetas() {
        return Collections.unmodifiableList(recetas);
    }

}