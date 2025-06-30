package ar.edu.unlam.crafteando;

import java.util.List;

public class MesaHerreria extends MesaCrafteo {

	public MesaHerreria(List<Receta> recetasDisponibles) {
        super("Mesa de carpinteria");
        for (Receta receta : recetasDisponibles) {
            agregarReceta(receta);
        }
    }
}