package ar.edu.unlam.crafteando;

import org.jpl7.*;

public class MotorLogicoProlog {
    private final String archivo;

    public MotorLogicoProlog(String rutaArchivo) {
        this.archivo = rutaArchivo;
    }

    public boolean cargarBase() {
        Query cargar = new Query("consult", new Term[]{new Atom(archivo)});
        return cargar.hasSolution();
    }

    public boolean agregarHecho(String hecho) {
        return new Query("assertz(" + hecho + ").").hasSolution();
    }

    public boolean eliminarHecho(String hecho) {
        return new Query("retract(" + hecho + ").").hasSolution();
    }

    public boolean eliminarTodos(String patron) {
        return new Query("retractall(" + patron + ").").hasSolution();
    }
    
    public void listarHechos(String predicado) {
    	  // para imprimir por consola los hechos
    	  new Query("listing(" + predicado + ").").hasSolution();
    	}

    public void consultar(String consulta, String variable) {
        Query q = new Query(consulta);
        while (q.hasMoreSolutions()) {
            java.util.Map<String, Term> sol = q.nextSolution();
            System.out.println(variable + " = " + sol.get(variable));
        }
    }
}
