package ar.edu.unlam.crafteando;

import java.util.ArrayList;
import java.util.List;

public class MesaCrafteo extends ObjetoCompuesto{
	private String nombre;
    private final List<Receta> recetas;

    public MesaCrafteo(String nombre) throws Exception {
        this.nombre = nombre;
        this.recetas = new ArrayList<>();
    }

    public List<Receta> obtenerRecetasDisponibles() {
        return new ArrayList<>(recetas);
    }
    
    /*
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
    }*/
}
