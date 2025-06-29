package ar.edu.unlam.crafteando;

import java.util.ArrayList;
import java.util.List;

public class MesaCrafteo extends ObjetoCompuesto{
	private String nombre;
    private final List<Receta> recetas;

    public MesaCrafteo(String nombre) throws Exception {
    	super(nombre, 1);
        this.nombre = nombre;
        this.recetas = new ArrayList<>();
    }

    public void agregarRecetasDisponibles(Recetario recetario) {
    	
    }
}
