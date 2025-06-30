package ar.edu.unlam.crafteando;

import java.util.List;

public class MesaCarpinteria extends MesaCrafteo {

	public MesaCarpinteria(List<Receta> recetasDisponibles) {
        super("Mesa de carpinteria");
        for (Receta receta : recetasDisponibles) {
            agregarReceta(receta);
        }
    }
}