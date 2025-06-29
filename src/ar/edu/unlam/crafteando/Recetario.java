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
    
    /// PRIMER NIVEL //////////////////////////////////////

    public Map<ObjetoComponente, Integer> verIngredientesPrimerNivel(String nombreObjetoCompuesto) {
        Receta receta = recetas.stream()
            .filter(r -> r.getTipo().equalsIgnoreCase(nombreObjetoCompuesto))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No se encontró receta para: " + nombreObjetoCompuesto));

        // Suponemos que el tipo representa el nombre del objeto compuesto
        return receta.getIngredientes();
    }
    
    public void mostrarReceta(String nombreObjetoCompuesto) {
        Map<ObjetoComponente, Integer> ingredientes = verIngredientesPrimerNivel(nombreObjetoCompuesto);

        System.out.println("Receta para: " + nombreObjetoCompuesto);
        ingredientes.forEach((componente, cantidad) ->
            System.out.println("- " + componente.getNombre() + " x" + cantidad)
        );
    }
    
    // DESDE CERO ////////////////////////////////////////
    public Map<ObjetoBasico, Integer> verIngredientesDesdeCero(String nombreObjetoCompuesto) {
        Receta receta = recetas.stream()
            .filter(r -> r.getTipo().equalsIgnoreCase(nombreObjetoCompuesto))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No se encontró receta para: " + nombreObjetoCompuesto));

        Map<ObjetoBasico, Integer> resultado = new HashMap<>();

        for (Map.Entry<ObjetoComponente, Integer> entry : receta.getIngredientes().entrySet()) {
            Map<ObjetoBasico, Integer> basicos = entry.getKey().descomponerEnBasicos();
            int cantidad = entry.getValue();

            for (Map.Entry<ObjetoBasico, Integer> basico : basicos.entrySet()) {
                resultado.merge(basico.getKey(), basico.getValue() * cantidad, Integer::sum);
            }
        }

        return resultado;
    }
    
    
    public void mostrarRecetaDesdeCero(String nombreObjetoCompuesto) {
        Map<ObjetoBasico, Integer> basicos = verIngredientesDesdeCero(nombreObjetoCompuesto);

        System.out.println("Receta completa (desde cero) para: " + nombreObjetoCompuesto);
        basicos.forEach((objeto, cantidad) ->
            System.out.println("- " + objeto.getNombre() + " x" + cantidad)
        );
    }
    
    // OTROS /////////////////////////////////////////////
    
    public List<Receta> getRecetas() {
        return Collections.unmodifiableList(recetas);
    }
	
	
}
