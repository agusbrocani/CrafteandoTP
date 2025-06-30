package ar.edu.unlam.crafteando;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MesaCrafteo extends ObjetoCompuesto {
	private String nombre;
    private final List<Receta> recetas;

    public MesaCrafteo(String nombre) {
        super(nombre);
        this.nombre = nombre;
        this.recetas = new ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MesaCrafteo)) return false;
        MesaCrafteo that = (MesaCrafteo) o;
        return Objects.equals(getNombre(), that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }

    public void agregarReceta(Receta receta) {
        recetas.add(receta);
    }
    
    public void agregarVariasRecetas(List<Receta> nuevasRecetas) {
        if (nuevasRecetas == null) {
            throw new IllegalArgumentException("La lista de recetas no puede ser nula.");
        }

        for (Receta receta : nuevasRecetas) {
            if (receta == null) {
                throw new IllegalArgumentException("No se puede agregar una receta nula.");
            }
            recetas.add(receta);
        }
    }

    public List<Receta> obtenerRecetas() {
        return Collections.unmodifiableList(recetas);
    }

    
    public void desbloquearRecetasEn(Recetario recetario) {
        for (Receta receta : recetas) {
            recetario.agregarReceta(receta);
        }
    }
}
