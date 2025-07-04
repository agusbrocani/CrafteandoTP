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

	public void recolectar(String nombreObjetoCompuesto, int cantidad) {
		inventario.agregar(nombreObjetoCompuesto, cantidad);
	}

	public void soltar(String nombreObjetoCompuesto, int cantidad) {
		inventario.quitar(nombreObjetoCompuesto, cantidad);
	}

	
	public void consultarInventario() {
		inventario.ver();
	}
	
	public void verRecetasDesdeCero(String nombreObjetoCompuesto) {
		recetario.mostrarRecetaDesdeCero(nombreObjetoCompuesto);
	}

	public void verRecetas(String nombreObjetoCompuesto) {
		recetario.mostrarReceta(nombreObjetoCompuesto);
	}
	
	private Map<ObjetoComponente, Integer> obtenerFaltantesPorReceta(Receta receta) throws Exception{
		
		Map<ObjetoComponente, Integer> faltantes = new HashMap<ObjetoComponente, Integer>();
		
		for (Map.Entry<ObjetoComponente, Integer> entry : receta.getIngredientes().entrySet()) {

			ObjetoComponente ingrediente = entry.getKey();

			int cantRequerida = entry.getValue();
			int cantDisponible = inventario.contiene(ingrediente.getNombre()) ? inventario.obtenerCantidad(ingrediente.getNombre()) : 0;
			
			if (cantDisponible < cantRequerida) {
				faltantes.put(ingrediente, cantRequerida - cantDisponible);
			}
		}
		System.out.println(faltantes);	
		return faltantes;
	}

	
	public List<Map<ObjetoComponente, Integer>> consultarFaltantesPrimerNivel (String nombreObjetoCompuesto) throws Exception{

		List<Receta> recetas;
		Map<ObjetoComponente, Integer> faltantesReceta = new HashMap<ObjetoComponente, Integer>();
		List<Map<ObjetoComponente, Integer>> faltantes = new LinkedList<Map<ObjetoComponente, Integer>>();
		
		recetas = recetario.buscarRecetasPorNombre(nombreObjetoCompuesto);

		for (Receta receta : recetas) {		
			
			faltantesReceta = obtenerFaltantesPorReceta(receta);
			faltantes.add(faltantesReceta);						
		}		
		return faltantes;
	}
	
	public List<Map<ObjetoComponente, Integer>> consultarFaltantesBasicos(String nombreObjetoCompuesto) throws Exception{
		
	    List<Map<ObjetoBasico, Integer>> recetasDesdeCero = recetario.obtenerRecetaDesdeCero(nombreObjetoCompuesto);
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
	
	public int cuantoPuedoCraftear(String nombreObjetoCompuesto) {
		
	    List<Receta> variantes = recetario.buscarRecetasPorNombre(nombreObjetoCompuesto);

	    if (variantes.isEmpty()) {
	        throw new IllegalArgumentException("No hay recetas para: " + nombreObjetoCompuesto);
	    }

	    int maxCantidad = 0;
	    for (Receta receta : variantes) {
	    	
	    	// creo una copia del inventario para considerar la fabricacion de objetos intermedios, de ser necesarios
	        Inventario copia = new Inventario(this.inventario);
	        int cantidad = calcularCuantasVecesPuedoCraftear(receta, copia);
	        maxCantidad = Math.max(maxCantidad, cantidad);
	    }

	    return maxCantidad;
	}

	private int calcularCuantasVecesPuedoCraftear(Receta receta, Inventario inventario) {
		
	    int cantidadMaxima = Integer.MAX_VALUE;

	    for (Map.Entry<ObjetoComponente, Integer> entry : receta.getIngredientes().entrySet()) {
	    	
	        String nombre = entry.getKey().getNombre();
	        int cantidadPorUnidad = entry.getValue();

	        if (entry.getKey().esBasico()) {
	            int disponibles = inventario.contiene(nombre) ? inventario.obtenerCantidad(nombre) : 0;
	            int posibles = disponibles / cantidadPorUnidad;
	            cantidadMaxima = Math.min(cantidadMaxima, posibles);
	            
	        } else {
	            // Es un compuesto > intentar fabricarlo recursivamente
	            List<Receta> subrecetas = recetario.buscarRecetasPorNombre(nombre);
	            if (subrecetas.isEmpty()) {
	            	return 0;
	            }

	            int cantidadSubmaxima = 0;
	            for (Receta subreceta : subrecetas) {
	            	
	                Inventario copiaInventario = new Inventario(inventario);
	                int subCantidad = calcularCuantasVecesPuedoCraftear(subreceta, copiaInventario);
	                cantidadSubmaxima = Math.max(cantidadSubmaxima, subCantidad);
	            }

	            int posibles = cantidadSubmaxima / cantidadPorUnidad;
	            cantidadMaxima = Math.min(cantidadMaxima, posibles);
	        }
	    }

	    return cantidadMaxima == Integer.MAX_VALUE ? 0 : cantidadMaxima;
	}

	
	//public boolean craftear(String nombreObjetoCompuesto, Receta receta)
	// OJO: TIENE SENTIDO PASARLE RECETA??? 
	// SI TOTAL EL JUGADOR NO PUEDE ELEGIR PORQUE SE COMPARAN POR NOMBRE QUE ES EL MISMO QUE EL DEL OBJETO
	
	// EN OTRAS NOTAS, ESTE METODO TOMA LA PRIMERA RECETA DE LAS QUE PUEDE HACER
	public boolean craftear(String nombreObjetoCompuesto) throws Exception {
		
	    // 1) valido que exista al menos una receta para ese nombre
		// CASO EL OBJETO ES DE UNA MESA DE CRAFTEO QUE NO TENGO
	    if (!recetario.contieneReceta(nombreObjetoCompuesto)) {
	        throw new Exception("No sabes la receta de " + nombreObjetoCompuesto);
	    }

	    // 2) obtengo todas las recetas con ese nombre
	    List<Receta> recetas = recetario.buscarRecetasPorNombre(nombreObjetoCompuesto);

	    // 3) me quedo solo con las recetas para las que tengo todos los ingredientes
	    List<Receta> recetasPosibles = new ArrayList<>();
	    
	    for (Receta r : recetas) {
	    	
	        boolean puedoCraftear = true;
	        
	        for (Map.Entry<ObjetoComponente, Integer> entry : r.getIngredientes().entrySet()) {
	            
	        	String ingNombre = entry.getKey().getNombre();
	            int cantNecesaria = entry.getValue();
	            if (inventario.obtenerCantidad(ingNombre) < cantNecesaria) {
	            	puedoCraftear = false;
	                break;
	            }
	        }
	        if (puedoCraftear) {
	        	recetasPosibles.add(r);
	        }
	    }

	    if (recetasPosibles.isEmpty()) {
	        System.out.println("Tienes recetas para " + nombreObjetoCompuesto +
	                           " pero te faltan ingredientes para todas ellas.");
	        return false;
	    }

	    // 4) Me quedo con la primera receta “crafteable”
	    Receta recetaElegida = recetasPosibles.get(0);

	    // 5) Simulo tiempo de crafteo
	    int tiempo = recetaElegida.getTiempoEnSegundos();
	    System.out.println("Crafteando " + nombreObjetoCompuesto + "... \nTiempo de crafteo: " + tiempo + " segundos");

	    try {
	        // 6a) agrego el objeto compuesto crafteado al inventario
	        inventario.agregar(nombreObjetoCompuesto, 1);

	        // 6b) consumo los ingredientes
	        for (Map.Entry<ObjetoComponente, Integer> entry : recetaElegida.getIngredientes().entrySet()) {
	           
	        	String nombreIngredienteConsumido = entry.getKey().getNombre();
		        int cantidadConsumida = entry.getValue();
		        
	        	inventario.quitar(nombreIngredienteConsumido, cantidadConsumida);
	        }
	        
	        //historial.registrar(crafteado, 1, ingredientesBasicos);

	        System.out.println("¡Listo! Crafteaste " + nombreObjetoCompuesto);
	        return true;

	    } catch (Exception ex) {
	    	
	        // por si algo falla al modificar inventario
	        throw new RuntimeException("Error al craftear " + nombreObjetoCompuesto, ex);
	    }
	    
	}

		
	public List<String> consultarObjetosCrafteables() {

		// ACTUALIZAR INVENTARIO EN PROLOG
		// limpiar todos los hechos antiguos tengo(_,_)
		motor.eliminarTodos("tengo(_,_)"); // probar con el punto "tengo(_,_)."

		// cargar el inventario actual en la base en los tengo(_,_)
		for (Map.Entry<ObjetoComponente, Integer> entry : inventario.getObjetos().entrySet()) {
			String atomo = entry.getKey().getNombre().toLowerCase();
			int cantidad = entry.getValue();
			motor.agregarHecho(String.format("tengo('%s',%d)", atomo, cantidad));
		}

		// ACTUALIZAR RECETARIO EN PROLOG
		// limpiar todos los hechos antiguos ingrediente(_,_,_)
		motor.eliminarTodos("ingrediente(_,_,_)");

		// para cada receta de mi recetario la desmenuzo en sus ingredientes
		for (Receta receta : recetario.getRecetas()) {

			String objeto = receta.getNombre().toLowerCase();

			for (Map.Entry<ObjetoComponente, Integer> e : receta.getIngredientes().entrySet()) {
				String ing = e.getKey().getNombre().toLowerCase();
				int cantReq = e.getValue();

				motor.agregarHecho(String.format("ingrediente('%s','%s',%d)", ing, objeto, cantReq));
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
	}
}
