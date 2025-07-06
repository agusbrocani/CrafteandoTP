package ar.edu.unlam.crafteando.Clases;

import java.util.List;
import java.util.Map;
import ar.edu.unlam.crafteando.Jugador.*;
import ar.edu.unlam.crafteando.lectura.*;

public class CrafteandoTP {
	
	public static ObjetoComponente construirObjeto(String nombre, String tipo) throws Exception {
		if ("Basico".equals(tipo)) {
			return new ObjetoBasico(nombre);
		}
		
		return new ObjetoCompuesto(nombre);
	}
	
	public static void main(String[] args) throws Exception {
		RecetaLectura recetaLectura = GestorJson.leer("archivos/recetas2.json", RecetaLectura.class);
		ObjetoLectura listaDeObjetosLectura = GestorJson.leer("archivos/inventario2.json", ObjetoLectura.class);

		Recetario recetario = new Recetario();
		for (RecetaLecturaItem recetaLeida : recetaLectura.getRecetas()) {
		    // Crear la receta real
		    Receta receta = new Receta(
		        recetaLeida.getNombre(),
		        recetaLeida.getTipoDeMesaCrafteo(),
		        recetaLeida.getTiempoConstruccion()
		    );

		    // Recorrer ingredientes
		    for (ObjetoLecturaItem ingredienteLeido : recetaLeida.getIngredientes()) {
		        ObjetoComponente ingrediente = construirObjeto(ingredienteLeido.getNombre(), ingredienteLeido.getTipo());
		        int cantidad = ingredienteLeido.getCantidad();

		        if (!ingrediente.esBasico()) {
		            ObjetoCompuesto acumulador = (ObjetoCompuesto) ingrediente;
		            for (int i = 1; i < cantidad; i++) {
		                ObjetoComponente nuevo = construirObjeto(ingredienteLeido.getNombre(), ingredienteLeido.getTipo());
		                acumulador.agregar(nuevo);
		            }
		            receta.agregarIngrediente(acumulador, cantidad);
		        } else {
		            receta.agregarIngrediente(ingrediente, cantidad);
		        }
		    }

		    for (Map.Entry<ObjetoComponente, Integer> entry : receta.getIngredientes().entrySet()) {
		        ObjetoComponente obj = entry.getKey();
		        int cantidad = entry.getValue();
		        System.out.println(obj.getNombre() + " x" + cantidad);
		    }
		    System.out.println("------------------");
		    recetario.agregarReceta(receta);
		}
		
		Jugador jugador = new Jugador("Jugadorcito", recetario, "prolog/integracion.pl");
		
		 // Agregar objetos al inventario
        for (ObjetoLecturaItem objLeido : listaDeObjetosLectura.getObjetos()) {
            ObjetoComponente objeto = construirObjeto(objLeido.getNombre(), objLeido.getTipo());
            int cantidad = objLeido.getCantidad();

            if (!objeto.esBasico()) {
                ObjetoCompuesto acumulador = (ObjetoCompuesto) objeto;
                for (int i = 1; i < cantidad; i++) {
                    ObjetoComponente nuevo = construirObjeto(objLeido.getNombre(), objLeido.getTipo());
                    acumulador.agregar(nuevo);
                }
                jugador.recolectar(acumulador, 1);
            } else {
                jugador.recolectar(objeto, cantidad);
            }
        }

        // Jugar con una receta existente del JSON
        String recetaElegida = "Espada de Hierro";

        try {
            System.out.println("\n=== Pruebas de Jugador ===");

            System.out.println("\nInventario Inicial:");
            jugador.consultarInventario();

            // Recolectar más para asegurar poder craftear
            jugador.recolectar(new ObjetoBasico("Hierro"), 20);
            jugador.recolectar(new ObjetoBasico("Madera"), 10);
            jugador.recolectar(new ObjetoBasico("Carbon"), 5);
            jugador.recolectar(new ObjetoBasico("Trigo"), 5);

            System.out.println("\nInventario despues de recolectar:");
            jugador.consultarInventario();

            // Soltar
            jugador.soltar(new ObjetoBasico("Hierro"), 5);
            System.out.println("\nInventario despues de soltar:");
            jugador.consultarInventario();

            // Consultas de recetas
            System.out.println("\nRECETA DESDE CERO");
            jugador.verRecetasDesdeCero(recetaElegida);

            System.out.println("\nRECETA PRIMER NIVEL");
            jugador.verRecetas(recetaElegida);

            // Faltantes
            System.out.println("\nFaltantes primer nivel:");
            List<Map<ObjetoComponente, Integer>> falt1 = jugador.consultarFaltantesPrimerNivel(recetaElegida);
            System.out.println(falt1);

            System.out.println("\nFaltantes básicos:");
            List<Map<ObjetoComponente, Integer>> faltB = jugador.consultarFaltantesBasicos(recetaElegida);
            System.out.println(faltB);

            // Cuánto puedo craftear
            int max = jugador.cuantoPuedoCraftear(recetaElegida);
            System.out.println("\nPuedo craftear " + recetaElegida + " " + max + " veces");

            // Objetos crafteables
            System.out.println("\nObjetos actualmente crafteables:");
            List<String> crafteables = jugador.consultarObjetosCrafteables();
            System.out.println(crafteables);

            // Craftear
            jugador.craftear(recetaElegida);
            System.out.println("INVENTARIO DESPUES DE CRAFTEAR 1 " + recetaElegida + ":");
            jugador.consultarInventario();

            // Historial
            jugador.getHistorial();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		
//		for (ObjetoLecturaItem objLeido : listaDeObjetosLectura.getObjetos()) {
//		    ObjetoComponente objeto = construirObjeto(objLeido.getNombre(), objLeido.getTipo());
//		    int cantidad = objLeido.getCantidad();
//
//		    if (!objeto.esBasico()) {
//		        ObjetoCompuesto acumulador = (ObjetoCompuesto) objeto;
//		        for (int i = 1; i < cantidad; i++) {
//		            ObjetoComponente nuevo = construirObjeto(objLeido.getNombre(), objLeido.getTipo());
//		            acumulador.agregar(nuevo);
//		        }
//		        jugador.recolectar(objeto, cantidad)
//		    } else {
//		        for (int i = 0; i < cantidad; i++) {
//		            jugador.agregarAlInventario(objeto);
//		        }
//		    }
//		}
		
		
		
//		for (RecetaLecturaItem receta : recetaLectura.getRecetas()) {
//		    System.out.println(receta);
//		    for (ObjetoLecturaItem ingrediente : receta.getIngredientes()) {
//		        System.out.println("  " + ingrediente);
//		    }
//		}
		
//		for (ObjetoLecturaItem obj : listaDeObjetosLectura.getObjetos()) {
//			int ce = obj.getCantidad();
//			for (int i = 0; i < ce; i++) {
//				ObjetoComponente o = construirObjeto(obj.getNombre(), obj.getTipo());
//				if ("Compuesto".equals(obj.getTipo())) {
//					// consultar recetario para cargarle los objetos con lo que se hace.
//				}
//				
////				jugador.agregarAlInventario(o);
//				o.mostrarConstruccion(false);
//			}
//		    System.out.println("------");
//		}
		
		
		
		
		
//		construirObjeto("Espada", "Compuesto").mostrarConstruccion(false);
		
		
//		int i;

//		// Objetos compuestos
//		ObjetoCompuesto oEspada1 = new ObjetoCompuesto("Espada");
//		ObjetoCompuesto oEspada2 = new ObjetoCompuesto("Espada");
//		ObjetoCompuesto oHojaDeHierro = new ObjetoCompuesto("Hoja de hierro");
//		ObjetoCompuesto oMangoDeMadera = new ObjetoCompuesto("Mango de madera");
//		ObjetoCompuesto oPegamento = new ObjetoCompuesto("Pegamento");
//		ObjetoCompuesto oHojaDeHierroDelTitanic = new ObjetoCompuesto("Hoja de hierro del Titanic");
//		ObjetoCompuesto oSubmarino = new ObjetoCompuesto("Submarino");
//
//		// Objetos básicos
//		ObjetoBasico oMadera = new ObjetoBasico("Madera");
//		ObjetoBasico oCuerda = new ObjetoBasico("Cuerda");
//		ObjetoBasico oHierro = new ObjetoBasico("Hierro");
//		ObjetoBasico oBaston = new ObjetoBasico("Bastón");
//		ObjetoBasico oSustanciaQuePegaSacadaDelArbol = new ObjetoBasico("Sustancia que pega sacada del arbol");
//
//		oEspada1.agregar(oHierro);
//		oEspada1.agregar(oHierro);
//		oEspada1.agregar(oMadera);
//		oEspada1.agregar(oCuerda);
//
//		oEspada1.agregar(oHierro);
//		oEspada1.agregar(oHierro);
//		oEspada1.agregar(oMadera);
//		oEspada1.agregar(oCuerda);
//
//		oEspada2.agregar(oHojaDeHierroDelTitanic);
//		for (i = 0; i < 5; i++) {
//			oEspada2.agregar(oMangoDeMadera);
//		}
//		oEspada2.agregar(oCuerda);
//
//		for (i = 0; i < 10; i++) {
//			oHojaDeHierro.agregar(oHierro);
//		}
//
//		for (i = 0; i < 7; i++) {
//			oMangoDeMadera.agregar(oMadera);
//		}
//
//		for (i = 0; i < 2; i++) {
//			oPegamento.agregar(oSustanciaQuePegaSacadaDelArbol);
//		}
//
//		for (i = 0; i < 50; i++) {
//			oSubmarino.agregar(oHierro);
//		}
//
//		oHojaDeHierroDelTitanic.agregar(oSubmarino);
//		oHojaDeHierroDelTitanic.agregar(oHierro);
//
//		// Recetario inicial (se carga del archivo)
//		Recetario recetasBasicasCargadasDeArchivo = new Recetario();
//		Receta espada1 = new Receta("Espada", "Básico", 60);
//		Receta espada2 = new Receta("Espada", "Básico", 59);
//
//		Receta hojaDeHierro = new Receta("Hoja de hierro", "Básico", 50);
//
//		Receta mangoDeMadera = new Receta("Mango de madera", "Básico", 11);
//
//		Receta pegamento = new Receta("Pegamento", "Básico", 25);
//
//		Receta hojaDeHierroDelTitanic = new Receta("Hoja de hierro del Titanic", "Básico", 250);
//
//		Receta submarino = new Receta("Submarino", "Básico", 1599);
//
//		// Completar las recetas con sus ingredientes
//		espada1.agregarIngrediente(oHojaDeHierro, 2);
//		espada1.agregarIngrediente(oMangoDeMadera, 1);
//		espada1.agregarIngrediente(oCuerda, 1);
//
//		espada2.agregarIngrediente(oHojaDeHierroDelTitanic, 1);
//		espada2.agregarIngrediente(oMangoDeMadera, 5);
//		espada2.agregarIngrediente(oCuerda, 1);
//
//		hojaDeHierro.agregarIngrediente(oHierro, 10);
//		mangoDeMadera.agregarIngrediente(oMadera, 7);
//		pegamento.agregarIngrediente(oSustanciaQuePegaSacadaDelArbol, 2);
//
//		hojaDeHierroDelTitanic.agregarIngrediente(oHierro, 1);
//		hojaDeHierroDelTitanic.agregarIngrediente(oSubmarino, 1);
//
//		submarino.agregarIngrediente(oHierro, 50);
//
//		// Agrego todas las recetas al Recetario Básico
//				
//		recetasBasicasCargadasDeArchivo.agregarReceta(espada1); //(!) CON ESTA RECETA PUEDO CRAFTEAR 0 EN PROLOG
//		recetasBasicasCargadasDeArchivo.agregarReceta(espada2);
//		recetasBasicasCargadasDeArchivo.agregarReceta(hojaDeHierro);
//		recetasBasicasCargadasDeArchivo.agregarReceta(mangoDeMadera);
//		recetasBasicasCargadasDeArchivo.agregarReceta(pegamento);
//		recetasBasicasCargadasDeArchivo.agregarReceta(hojaDeHierroDelTitanic);
//		recetasBasicasCargadasDeArchivo.agregarReceta(submarino);
//
//		// Creo jugador
//		Jugador jugador = new Jugador("Jugadorcito", recetasBasicasCargadasDeArchivo, "prolog/integracion.pl");
//
//		// Inicializo inventario
//		jugador.recolectar(oHojaDeHierroDelTitanic, 1);
//		for (i = 0; i < 5; i++) {
//			jugador.recolectar(oMangoDeMadera, 1);
//		}
//		jugador.recolectar(oCuerda, 1);
//
//		for (i = 0; i < 10; i++) {
//			jugador.recolectar(oHierro, 1);
//		}
//
//		for (i = 0; i < 7; i++) {
//			jugador.recolectar(oMadera, 1);
//		}
//
//		for (i = 0; i < 2; i++) {
//			jugador.recolectar(oSustanciaQuePegaSacadaDelArbol, 1);
//		}
//
//		for (i = 0; i < 50; i++) {
//			jugador.recolectar(oHierro, 1);
//		}
//
//		jugador.recolectar(oSubmarino, 1);
//		jugador.recolectar(oHierro, 1);
//
//		// A JUGAR!
//		try {
//			System.out.println("\n=== Pruebas de Jugador ===");
//
//			System.out.println("\nInventario Inicial");
//			jugador.consultarInventario();
//
//			// 1. recolectar
//			// FABRICAR 2 ESPADAS EXACTAMENTE (una con basicos, otra con primer nivel)
//			jugador.recolectar(oHierro, 20);
//			jugador.recolectar(oMadera, 7);
//			jugador.recolectar(oCuerda, 2);
//			jugador.recolectar(oMangoDeMadera, 1);
//			jugador.recolectar(oHojaDeHierro, 2);
//
//			System.out.println("\nInventario despues de recolectar:");
//			jugador.consultarInventario();
//
//			// 2. soltar
//			jugador.soltar(oHierro, 6);
//			System.out.println("\nInventario despues de soltar:");
//			jugador.consultarInventario();
//
//			System.out.println("INVENTARIO:");
//			jugador.consultarInventario();
//			System.out.println("\n");
//
//			// 3. consultarRecetaDesdeCero y consultarReceta
//			System.out.println("\nRECETA DESDE CERO");
//			jugador.verRecetasDesdeCero("Espada");
//			System.out.println("\n");
//
//			System.out.println("\nRECETA PRIMER NIVEL");
//			jugador.verRecetas("Espada");
//			System.out.println("\n");
//			System.out.println("\n");
//
//			// 4. consultarFaltantesPrimerNivel
//			System.out.println("\nFaltantes primer nivel para Espada:");
//			List<Map<ObjetoComponente, Integer>> falt1 = jugador.consultarFaltantesPrimerNivel("Espada");
//			System.out.println(falt1);
//
//			// 5. consultarFaltantesBasicos
//			System.out.println("\nFaltantes básicos para Espada:");
//			List<Map<ObjetoComponente, Integer>> faltB = jugador.consultarFaltantesBasicos("Espada");
//			System.out.println(faltB);
//			
//
//			jugador.recolectar(oBaston, 20);
//			// 6. cuantoPuedoCraftear
//			int maxCraftear = jugador.cuantoPuedoCraftear("Espada");
//			System.out.println("\nPuedo craftear Espada " + maxCraftear + " veces");
//
//			// 8. consultarObjetosCrafteables (vía Prolog)
//			System.out.println("\nObjetos actualmente crafteables:");
//			List<String> crafteables = jugador.consultarObjetosCrafteables();
//			System.out.println(crafteables);
//
//			// 7. craftear
//			jugador.craftear("Espada");
//			System.out.println("INVENTARIO DESPUES DE CRAFTEAR 1 ESPADA: ");
//			jugador.consultarInventario();
//
//			jugador.getHistorial();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
