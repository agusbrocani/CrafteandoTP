package ar.edu.unlam.crafteando;
import org.jpl7.*;

public class CrafteandoTP {
    public static void main(String[] args) {
    	
    	//System.out.println("hola mundo");
    	String ruta = "prolog/datos.pl";
    	Query cargar = new Query("consult", new Term[]{new Atom(ruta)});
    	// 1- Internamente se hace un -> consult('datos.pl'). => CARGA EL ARCHIVO
    	// Lo tiene guardado en el motor de Prolog embebido que maneja JPL
    	
    	// 2- Lo cargo efectivamente?
    	if (!cargar.hasSolution()) {
    	    System.err.println("NO se pudo cargar el archivo '" + ruta + "'");
    	    return;
    	}
    	
    	// Agregar nuevo hecho
    	Query agregar = new Query("assertz(es_padre(juan, lucas))");
    	agregar.hasSolution(); // Devuelve true si fue exitoso

    	// Eliminar un hecho específico
    	Query eliminar = new Query("retract(es_padre(juan, pedro))");
    	eliminar.hasSolution(); // Elimina si existe

    	// Eliminar todos los es_padre
    	Query limpiar = new Query("retractall(es_padre(_, _))");
    	limpiar.hasSolution();
    }
}
