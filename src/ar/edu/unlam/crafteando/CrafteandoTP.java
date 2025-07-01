package ar.edu.unlam.crafteando;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

public class CrafteandoTP {
    public static void main(String[] args) {
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

        // Cargar datos desde archivos
        Persona persona = cargarPersona("archivos/persona.json");
        Inventario inventario = cargarInventario("archivos/Inventario.json");
        Recetario recetario = cargarRecetario("archivos/Recetas.json");

        // Crear jugador
        Jugador jugador = new Jugador(persona.getNombre(), inventario, recetario, "prolog/datos.pl");

        // Mostrar recetas disponibles
        System.out.println("\n Recetas disponibles:");
        for (Receta receta : recetario.getRecetas()) {
            System.out.println(" " + receta.getNombreObjeto());
        }

        // Mostrar inventario final
        System.out.println("\n Inventario final:");
        mostrarInventario(jugador.getInventario());

        // Guardar inventario actualizado
        guardarInventario(jugador.getInventario(), "archivos/Inventario.json");
    }

    private static Persona cargarPersona(String path) {
        Persona persona = GestorJson.leer(path, Persona.class);
        System.out.println(" Bienvenido, " + persona.getNombre());
        return persona;
    }

    private static Inventario cargarInventario(String path) {
        List<EntradaInventario> entradas = Arrays.asList(
            GestorJson.leer(path, EntradaInventario[].class)
        );
        Inventario inventario = new Inventario();
        for (EntradaInventario entrada : entradas) {
            ObjetoBasico objeto = new ObjetoBasico(entrada.getNombre());
            inventario.agregar(objeto, entrada.getCantidad());
        }
        return inventario;
    }

    private static Recetario cargarRecetario(String path) {
    List<Receta> recetasJson = Arrays.asList(
        GestorJson.leer(path, Receta[].class)
    );
    Recetario recetario = new Recetario();
    for (Receta rj : recetasJson) {
        for (Map.Entry<String, Integer> ing : rj.ingredientes.entrySet()) {
            ObjetoComponente comp = crearComponente(ing.getKey());
            rj.agregarIngrediente(comp, ing.getValue());
        }
        recetario.agregarReceta(rj);
    }
    return recetario;
}

    private static void mostrarInventario(Inventario inventario) {
        for (Map.Entry<ObjetoComponente, Integer> entry : inventario.getContenido().entrySet()) {
            System.out.println(" " + entry.getKey().getNombre() + ": " + entry.getValue());
        }
    }

    private static void guardarInventario(Inventario inventario, String path) {
        List<EntradaInventario> inventarioJson = inventario.getContenido().entrySet().stream()
            .map(entry -> new EntradaInventario(entry.getKey().getNombre(), entry.getValue()))
            .collect(Collectors.toList());
        GestorJson.guardar(inventarioJson, path);
        System.out.println("\n Inventario guardado en: " + path);
    }

    private static ObjetoComponente crearComponente(String nombre) {
        // En este ejemplo asumimos que todo es ObjetoBasico, pero podÃ©s extender esto
        return new ObjetoBasico(nombre);
    }

}
