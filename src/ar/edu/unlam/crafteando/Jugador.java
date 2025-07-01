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
	//private Historial historial;

	private MotorLogicoProlog motor;

	public Jugador (String nombre, Recetario recetario, String rutaBaseProlog) {
		this.nombre = nombre;
		this.inventario = new Inventario();
		this.recetario = recetario;
		//this.historial = new Historial();

		this.motor = new MotorLogicoProlog(rutaBaseProlog);
		if (!motor.cargarBase()) {
			throw new RuntimeException("No se pudo cargar la base Prolog en: " + rutaBaseProlog);
		}
	}

	public void recolectar(ObjetoComponente o, int cantidad) {
		inventario.agregar(o.getNombre(), cantidad);
	}

	public void soltar(ObjetoComponente o, int cantidad) {
		inventario.quitar(o.getNombre(), cantidad);
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
	

	
	private Map<ObjetoComponente, Integer> obtenerFaltantesPorReceta(ObjetoCompuesto o, Receta receta) throws Exception{
		
		Map<ObjetoComponente, Integer> faltantes = new HashMap<>();
		
		for (Map.Entry<ObjetoComponente, Integer> entry : receta.getIngredientes().entrySet()) {

			ObjetoComponente ingrediente = entry.getKey();

			int cantRequerida = entry.getValue();
			int cantDisponible = inventario.contiene(ingrediente.getNombre()) ? inventario.obtenerCantidad(ingrediente.getNombre()) : 0;
			if (cantDisponible < cantRequerida) {
				faltantes.put(ingrediente, cantRequerida - cantDisponible);
			}
		}
		
		return faltantes;
	}

	public List<Map<ObjetoComponente, Integer>> consultarFaltantesPrimerNivel (ObjetoCompuesto o) throws Exception{

		List<Receta> recetas;
		Map<ObjetoComponente, Integer> faltantesReceta;// = new HashMap<ObjetoComponente, Integer>()
		List<Map<ObjetoComponente, Integer>> faltantes = new LinkedList<Map<ObjetoComponente, Integer>>();
		
		recetas = recetario.buscarRecetasPorNombre(o.getNombre());

		for (Receta receta : recetas) {
			
			faltantesReceta = obtenerFaltantesPorReceta(o, receta);
			
			faltantes.add(faltantesReceta);						
		}
				
		return faltantes;
	}

	
	public List<Map<ObjetoComponente, Integer>> consultarFaltantesBasicos(ObjetoCompuesto o) throws Exception{
		
	    List<Map<ObjetoBasico, Integer>> recetasDesdeCero = recetario.obtenerRecetaDesdeCero(o.getNombre());
	    List<Map<ObjetoComponente, Integer>> faltantesBasicos = new ArrayList<>();

	    for (Map<ObjetoBasico, Integer> recetaBasica : recetasDesdeCero) {
	    	
	        Map<ObjetoComponente, Integer> faltantesReceta = obtenerFaltantesBasicosPorReceta(recetaBasica);
	        faltantesBasicos.add(faltantesReceta);
	    }

	    return faltantesBasicos;
	}

	private Map<ObjetoComponente, Integer> obtenerFaltantesBasicosPorReceta(Map<ObjetoBasico, Integer> recetaBasica) {
		
	    Map<ObjetoComponente, Integer> faltantes = new HashMap<>();

	    for (Map.Entry<ObjetoBasico, Integer> entry : recetaBasica.entrySet()) {
	        
	    	ObjetoBasico ingrediente = entry.getKey();
	        int cantidadRequerida = entry.getValue();
	        
	        int cantidadDisponible = inventario.contiene(ingrediente.getNombre())
	                ? inventario.obtenerCantidad(ingrediente.getNombre())
	                : 0;

	        if (cantidadDisponible < cantidadRequerida) {
	            faltantes.put(ingrediente, cantidadRequerida - cantidadDisponible);
	        }
	    }

	    return faltantes;
	}
	
	
	public Integer cuantoPuedoCraftear(ObjetoCompuesto o, Receta receta) throws Exception {
	    
		// me fijo si la receta existe en el recetario
	    if (!recetario.getRecetas().contains(receta)) {
	        throw new Exception("La receta proporcionada no pertenece al recetario del jugador.");
	    }

	    // obtengo  los ingredientes básicos de forma total (descompuestos)
	    Map<ObjetoBasico, Integer> ingredientesBasicos = receta.listarIngredientesDesdeCero(o);

	    int cantidadMinima = Integer.MAX_VALUE;

	    for (Map.Entry<ObjetoBasico, Integer> entry : ingredientesBasicos.entrySet()) {
	        String nombreIngrediente = entry.getKey().getNombre();
	        int cantidadRequeridaPorUnidad = entry.getValue();

	        int cantidadDisponible = inventario.contiene(nombreIngrediente)
	                ? inventario.obtenerCantidad(nombreIngrediente)
	                : 0;

	        int unidadesPosibles = cantidadDisponible / cantidadRequeridaPorUnidad;

	        cantidadMinima = Math.min(cantidadMinima, unidadesPosibles);

	        if (cantidadMinima == 0) return 0;
	    }

	    return cantidadMinima == Integer.MAX_VALUE ? 0 : cantidadMinima;
	}

	
	public void craftear(ObjetoCompuesto o, Receta receta) throws Exception{
		
		// me fijo si la receta existe en el recetario
	    if (!recetario.getRecetas().contains(receta)) {
	        throw new Exception("No sabes la receta de " + o.getNombre());
	    }
	    
	    // me fijo si lo puedo craftear
	    if (cuantoPuedoCraftear(o, receta) < 1) {
	        System.out.println("No tienes suficientes ingredientes para craftear " + o.getNombre() + ".");
	        return;
	    }

	    // calculo y simulo el tiempo
	    int tiempo = receta.getTiempoEnSegundos();
	    System.out.println("Crafteando " + o.getNombre() +
	                       "... (tiempo de crafteo: " + tiempo + "segundos)");

	    try {
	    	// agrego el objeto crafteado al inventario
	        ObjetoCompuesto crafteado = new ObjetoCompuesto(o.getNombre());
	        inventario.agregar(crafteado.getNombre(), 1);
	        System.out.println("¡Listo! Crafteaste " + o.getNombre() + "!");
	        
	        // comsumo los ingredientes
	        Map<ObjetoBasico, Integer> ingredientesBasicos = receta.listarIngredientesDesdeCero(o);

		    for (Map.Entry<ObjetoBasico, Integer> entry : ingredientesBasicos.entrySet()) {
		        String nombre = entry.getKey().getNombre();
		        int cantidad = entry.getValue();

		        inventario.quitar(nombre, cantidad);
		    }

	    } catch (Exception ex) {
	    	
	        throw new RuntimeException("Error al creaftear el objeto.", ex);
	    }
	    
	    //historial.registrar(crafteado, 1, ingredientesBasicos);
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
