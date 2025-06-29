package ar.edu.unlam.crafteando;

import java.util.HashMap;
import java.util.Map;

public class MesaCrafteo extends ObjetoCompuesto{
	private String nombre;
    private final List<Receta> recetas;

    public MesaCrafteo(String nombre) {
        this.nombre = nombre;
        this.recetas = new ArrayList<>();
    }

    public List<Receta> obtenerRecetasDisponibles() {
        return new ArrayList<>(recetas); // copia defensiva
    }

    public void agregarReceta(Receta receta) {
        this.recetas.add(receta);
    }

    public String getNombre() {
        return nombre;
    }

    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected List<Receta> getRecetas() {
        return recetas;
    }
}
