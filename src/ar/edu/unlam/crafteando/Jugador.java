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

    public Jugador(String nombre, Inventario inventario, Recetario recetario, /*Historial historial,*/ String rutaBaseProlog) {
        this.nombre = nombre;
        this.inventario = inventario;
        this.recetario  = recetario;
        //this.historial  = historial;

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
    	//FALTA METODO PARA MOSTRAR
    }

    public List<String> /*List<ObjetoCompuesto>*/ consultarObjetosCrafteables() {
    	
    	// ACTUALIZAR INVENTARIO EN PROLOG
        // limpiar todos los hechos antiguos tengo(_,_)
        motor.eliminarTodos("tengo(_,_)"); // probar con el punto "tengo(_,_)."

        // cargar el inventario actual en la base en los tengo(_,_)
        for (Map.Entry<ObjetoComponente, Integer> entry : inventario.getObjetos().entrySet()) {
            String atomo = entry.getKey().getNombre();
            int cantidad = entry.getValue();
            motor.agregarHecho(
                String.format("tengo(%s,%d)", atomo, cantidad)
            );
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

	             motor.agregarHecho(
	                 String.format("ingrediente(%s,%s,%d)", ing, objeto, cantReq)
	             );
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
        
        List<String> nombres = Arrays.stream(elems)
                                     .map(Term::name)
                                     .collect(Collectors.toList());

        return nombres;
        // convertir cada átomo Prolog a ObjetoCompuesto Java
        // o sea, por cada atom hace un new ObjetoCompuesto
        // devuelve un List<ObjetoCompuesto>
        // requiere agregar un contructor a ObjetoCompuesto que solo setee el nombre.
        /* return nombres.stream()
                      .map(ObjetoCompuesto::new)
                      .collect(Collectors.toList());
        */
    }
}
