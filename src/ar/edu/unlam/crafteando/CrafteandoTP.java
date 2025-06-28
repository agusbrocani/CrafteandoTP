package ar.edu.unlam.crafteando;

public class CrafteandoTP {
    public static void main(String[] args) {
        // Ruta relativa al archivo datos.pl
    	String pathDatosProlog = "prolog/datos.pl";
        MotorLogicoProlog motor = new MotorLogicoProlog(pathDatosProlog);

        // 1. Cargar la base de conocimiento
        if (!motor.cargarBase()) {
            System.err.println("NO se pudo cargar el archivo '" + pathDatosProlog + "'");
            return;
        }

        // 2. Agregar nuevo hecho
        motor.agregarHecho("es_padre(juan, lucas)");

        // 3. Eliminar hecho específico
        motor.eliminarHecho("es_padre(juan, pedro)");

        // 4. Eliminar todos los hechos que coincidan con el patrón
        motor.eliminarTodos("es_padre(_, _)");

        // 5. Agregar hechos de prueba
        motor.agregarHecho("es_padre(juan, ana)");
        motor.agregarHecho("es_padre(juan, jose)");

        // 6. Consultar los hijos de juan
        motor.consultar("es_padre(juan, X)", "X");
    }
}

//public class CrafteandoTP {
//    public static void main(String[] args) {
//        // Crear objeto
//        Persona persona = new Persona("Agustin", 24);
//
//        // Definir ruta donde se va a guardar
//        String rutaArchivo = "archivos/persona.json";
//
//        // Guardar JSON
//        if (GestorJson.guardar(persona, rutaArchivo)) {
//            System.out.println("✔ Persona guardada exitosamente");
//        }
//
//        // Leer JSON
//        Persona leida = GestorJson.leer(rutaArchivo, Persona.class);
//        if (leida != null) {
//            System.out.println("📂 Persona leída:");
//            System.out.println("Nombre: " + leida.getNombre());
//            System.out.println("Edad: " + leida.getEdad());
//        } else {
//            System.err.println("✘ No se pudo leer el JSON");
//        }
//    }
//}
