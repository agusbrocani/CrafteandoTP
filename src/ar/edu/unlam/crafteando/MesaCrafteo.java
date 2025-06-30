package ar.edu.unlam.crafteando;
import java.util.List;
import java.util.LinkedList;

public class MesaCrafteo extends ObjetoCompuesto{
	private String nombre;
	private final List <Receta> recetas;
	
	public MesaCrafteo(String nombre, String nombreObjetoACraftear, Integer cantidad) {
		super(nombreObjetoACraftear);
		this.nombre = nombre;
		recetas = new LinkedList<Receta>();
	}

    public String getNombre() {
        return nombre;
    }
    
    public List<Receta> obtenerRecetasDisponibles() {
        return new LinkedList<>(recetas);
    }   
}
