package ar.edu.unlam.crafteando;

/*
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.ASTList;
*/
import org.jpl7.*;

import java.util.*;
import java.util.stream.Collectors;

public class Jugador {
    
    private String nombre;
    private Inventario inventario;
    private Recetario recetario; // CARGAR EN PROLOG COMO BASE DE CONOCIMIENTOS EN ingrediente(Objeto, Ing, CantReq);
    //private Historial historial;

    private MotorLogicoProlog motor;

    public Jugador(String nombre, Inventario inventario, Recetario recetario, /*Historial historial,*/ String rutaBaseProlog) {
        this.nombre     = nombre;
        this.inventario = inventario;
        this.recetario  = recetario;
        //this.historial  = historial;

        this.motor = new MotorLogicoProlog(rutaBaseProlog);
        if (!motor.cargarBase()) {
            throw new RuntimeException("No se pudo cargar la base Prolog en: " + rutaBaseProlog);
        }
    }

    public List<ObjetoCompuesto> consultarObjetosCrafteables() {
    	
        // 1 - limpiar todos los hechos antiguos tengo(_,_)
        motor.eliminarTodos("tengo(_,_)");

        // 2 - cargar el inventario actual en la base en los tengo(_,_)
        for (Map.Entry<ObjetoComponente, Integer> entry : inventario.getItems().entrySet()) {
            String atomo = entry.getKey().getNombre();
            int cantidad = entry.getValue();
            motor.agregarHecho(
                String.format("tengo(%s,%d)", atomo, cantidad)
            );
        }

        // 3 - mandar la consulta a Prolog
        Query q = new Query("objetos_crafteables(L)");
        if (!q.hasSolution()) {
            return Collections.emptyList();
        }
        
        // CHEQUEAR ESTOS DOS:
        // 4 - obtener y parseo la lista que me devuelve Prolog
        Term lista = q.oneSolution().get("L");
        Term[] elems = ((ASTList) lista).toTermArray();
        List<String> nombres = Arrays.stream(elems)
                                     .map(Term::name)
                                     .collect(Collectors.toList());

        // 5 - convertir cada átomo Prolog a ObjetoCompuesto Java
        return nombres.stream()
                      .map(ObjetoCompuesto::new)
                      .collect(Collectors.toList());
    }
}
