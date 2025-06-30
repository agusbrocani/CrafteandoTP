package ar.edu.unlam.crafteando;

import java.util.*;
//import org.jpl7.*;

import org.jpl7.Query;
import org.jpl7.Term;

import java.util.stream.Collectors;

public class Jugador {

	private String nombre;
	private Inventario inventario;
	private Recetario recetario;
	// private Historial historial;

	private MotorLogicoProlog motor;

	public Jugador(String nombre, Inventario inventario, Recetario recetario,
			/* Historial historial, */ String rutaBaseProlog) {
		this.nombre = nombre;
		this.inventario = inventario;
		this.recetario = recetario;
		// this.historial = historial;

		this.motor = new MotorLogicoProlog(rutaBaseProlog);
		if (!motor.cargarBase()) {
			throw new RuntimeException("No se pudo cargar la base Prolog en: " + rutaBaseProlog);
		}
	}

	public void recolectar(ObjetoComponente o) {
		inventario.agregar(o);
	}

	public void soltar(ObjetoComponente o) {
		inventario.quitar(o);
	}

	public void consultarInventario() {
		inventario.ver();
	}

	public void consultarRecetaDesdeCero(ObjetoCompuesto o) {
		recetario.mostrarRecetaDesdeCero(o.getNombre());
	}

	public void consultarReceta(ObjetoCompuesto o) {
		recetario.mostrarRecetaDesdeCero(o.getNombre());
	}

	public Map<ObjetoComponente, Integer> consultarFaltantes(ObjetoCompuesto o) {

		Map<ObjetoComponente, Integer> receta;
		Map<ObjetoComponente, Integer> faltantes = new HashMap<>();

		receta = recetario.obtenerRecetaPrimerNivel(o.getNombre());
		
		if (receta.isEmpty()) {
	        return null;
	    }

		for (Map.Entry<ObjetoComponente, Integer> entry : receta.entrySet()) {

			ObjetoComponente ingrediente = entry.getKey();

			int cantRequerida = entry.getValue();

			int cantDisponible = inventario.contiene(ingrediente) ? inventario.obtenerCantidad(ingrediente) : 0;

			if (cantDisponible < cantRequerida) {
				faltantes.put(ingrediente, cantRequerida - cantDisponible);
			}
		}
		return faltantes;
	}

	public Map<ObjetoBasico, Integer> consultarFaltantesBasicos(ObjetoCompuesto o) {

		Map<ObjetoBasico, Integer> recetaBasicos;
		Map<ObjetoBasico, Integer> faltantesBasicos = new HashMap<>();

		recetaBasicos = recetario.obtenerRecetaDesdeCero(o.getNombre());
		
		if (recetaBasicos.isEmpty()) {
	        return null;
	    }

		for (Map.Entry<ObjetoBasico, Integer> entry : recetaBasicos.entrySet()) {

			ObjetoBasico ingredienteBasico = entry.getKey();

			int cantRequerida = entry.getValue();

			int cantDisponible = inventario.contiene(ingredienteBasico) ? inventario.obtenerCantidad(ingredienteBasico) : 0;

			if (cantDisponible < cantRequerida) {
				faltantesBasicos.put(ingredienteBasico, cantRequerida - cantDisponible);
			}
		}
		return faltantesBasicos;
	}

	
	public Integer cuantoPuedoCraftear(ObjetoCompuesto o) {
		
		int cantCrafteables = 0;
		
	    Map<ObjetoBasico, Integer> recetaBasicos = recetario.obtenerRecetaDesdeCero(o.getNombre());

	    if (recetaBasicos.isEmpty()) {
	        return 0;
	    }


	    for (Map.Entry<ObjetoBasico, Integer> entry : recetaBasicos.entrySet()) {
	    	
	        ObjetoBasico basico = entry.getKey();
	        
	        int cantRequeridaPorUnidad = entry.getValue();
	        
	        int cantDisponible = inventario.contiene(basico) ? inventario.obtenerCantidad(basico) : 0;
	        
	        cantCrafteables = cantDisponible / cantRequeridaPorUnidad;
	    }

	    return cantCrafteables;
	}
	
	public void craftear(ObjetoCompuesto o) {
		
	    // me fijo si tengo la receta en el inventario
	    Receta receta = null;
	    for (Receta r : recetario.getRecetas()) {
	        if (r.getNombre().equalsIgnoreCase(o.getNombre())) {
	            receta = r;
	            break;
	        }
	    }
	    
	    if (receta == null) {
	        System.out.println("No sabes la receta de " + o.getNombre());
	        return;
	    }

	    // me fijo si lo puedo craftear
	    if (cuantoPuedoCraftear(o) < 1) {
	        System.out.println("No tienes suficientes ingredientes para craftear " + o.getNombre() + ".");
	        return;
	    }

	    // calculo y simulo el tiempo
	    int tiempo = receta.getTiempoEnSegundos();
	    System.out.println("Crafteando " + o.getNombre() +
	                       "... (tiempo de crafteo: " + tiempo + "segundos)");

	    try {
	    	// agrego el objeto crafteado al inventario
	        ObjetoCompuesto crafteado = new ObjetoCompuesto(o.getNombre(), 1);
	        inventario.agregar(crafteado);
	        System.out.println("¡Listo! Crafteaste " + o.getNombre() + "!");
	        
	        // comsumo los ingredientes de primer nivel
		    for (Map.Entry<ObjetoComponente,Integer> entry : receta.getIngredientes().entrySet()) {
		    	
		        ObjetoComponente ingrediente = entry.getKey().clonarConCantidad(entry.getValue());
		        inventario.quitar(ingrediente);
		    }
	    } catch (Exception ex) {
	    	
	        throw new RuntimeException("Error al creaftear el objeto.", ex);
	    }
	}

	

	
	public List<String> /* List<ObjetoCompuesto> */ consultarObjetosCrafteables() {

		// ACTUALIZAR INVENTARIO EN PROLOG
		// limpiar todos los hechos antiguos tengo(_,_)
		motor.eliminarTodos("tengo(_,_)"); // probar con el punto "tengo(_,_)."

		// cargar el inventario actual en la base en los tengo(_,_)
		for (Map.Entry<ObjetoComponente, Integer> entry : inventario.getObjetos().entrySet()) {
			String atomo = entry.getKey().getNombre();
			int cantidad = entry.getValue();
			motor.agregarHecho(String.format("tengo(%s,%d)", atomo, cantidad));
		}

		// ACTUALIZAR RECETARIO EN PROLOG
		// limpiar todos los hechos antiguos ingrediente(_,_,_)
		motor.eliminarTodos("ingrediente(_,_,_)");

		// para cada receta de mi recetario la desmenuzo en sus ingredientes
		for (Receta receta : recetario.getRecetas()) {

			String objeto = receta.getNombre();

			for (Map.Entry<ObjetoComponente, Integer> e : receta.getIngredientes().entrySet()) {
				String ing = e.getKey().getNombre();
				int cantReq = e.getValue();

				motor.agregarHecho(String.format("ingrediente(%s,%s,%d)", ing, objeto, cantReq));
			}
		}

		// mandar la consulta a Prolog
		Query q = new Query("objetos_crafteables(L)");
		if (!q.hasSolution()) {
			return Collections.emptyList();
		}

		// CHEQUEAR ESTOS DOS:
		// obtener y parseo la lista que me devuelve Prolog
		Term lista = q.oneSolution().get("L");
		Term[] elems = lista.listToTermArray();

		List<String> nombres = Arrays.stream(elems).map(Term::name).collect(Collectors.toList());

		return nombres;
		// convertir cada átomo Prolog a ObjetoCompuesto Java
		// o sea, por cada atom hace un new ObjetoCompuesto
		// devuelve un List<ObjetoCompuesto>
		// requiere agregar un contructor a ObjetoCompuesto que solo setee el nombre.
		/*
		 * return nombres.stream() .map(ObjetoCompuesto::new)
		 * .collect(Collectors.toList());
		 */
	}
}
