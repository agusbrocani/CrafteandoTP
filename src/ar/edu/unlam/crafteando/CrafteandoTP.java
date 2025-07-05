package ar.edu.unlam.crafteando;

import java.util.List;
import java.util.Map;

public class CrafteandoTP {


    public static void main(String[] args) {
        // Rutas
		GestorJson jsonMng;
        String pathInventario = "archivo/Inventario.json";
        String pathRecetas = "archivo/Recetas.json";
        String pathProlog = "prolog/datos.pl"; 

        // Leer Inventario
	
        Map<ObjetoComponente, Integer> objetos = jsonMng.leerInventarioComoMapa(pathInventario);
        Inventario inventario = new Inventario(objetos);

        // Leer Recetas
        List<Receta> recetas = jsonMng.leerRecetasComoLista(pathRecetas);
        Recetario recetario = new Recetario(recetas);

        // Crear jugador
        Jugador jugador = new Jugador("beta", inventario, recetario, pathProlog);

        // Inventario inicial
        System.out.println("===== INVENTARIO INICIAL =====");
        jugador.consultarInventario();

        // Objetos a craftear
        List<String> objetosACraftear = List.of("Antorcha", "Bastón", "Espada de Hierro");

        System.out.println("\n===== CRAFTEANDO OBJETOS =====");
        for (String nombre : objetosACraftear) {
            boolean exito = jugador.craftear(nombre);
			if(exito)
				System.out.println("Error al craftear "+nombre);
        }

        // Inventario final
        System.out.println("\n===== INVENTARIO FINAL =====");
        jugador.consultarInventario();

		int i;

		// Objetos compuestos
		ObjetoCompuesto oEspada1 = new ObjetoCompuesto("Espada");
		ObjetoCompuesto oEspada2 = new ObjetoCompuesto("Espada");
		ObjetoCompuesto oHojaDeHierro = new ObjetoCompuesto("Hoja de hierro");
		ObjetoCompuesto oMangoDeMadera = new ObjetoCompuesto("Mango de madera");
		ObjetoCompuesto oPegamento = new ObjetoCompuesto("Pegamento");
		ObjetoCompuesto oHojaDeHierroDelTitanic = new ObjetoCompuesto("Hoja de hierro del Titanic");
		ObjetoCompuesto oSubmarino = new ObjetoCompuesto("Submarino");

		// Objetos básicos
		ObjetoBasico oMadera = new ObjetoBasico("Madera");
		ObjetoBasico oCuerda = new ObjetoBasico("Cuerda");
		ObjetoBasico oHierro = new ObjetoBasico("Hierro");
		ObjetoBasico oSustanciaQuePegaSacadaDelArbol = new ObjetoBasico("Sustancia que pega sacada del arbol");

		oEspada1.agregar(oHierro);
		oEspada1.agregar(oHierro);
		oEspada1.agregar(oMadera);
		oEspada1.agregar(oCuerda);

		oEspada1.agregar(oHierro);
		oEspada1.agregar(oHierro);
		oEspada1.agregar(oMadera);
		oEspada1.agregar(oCuerda);

		oEspada2.agregar(oHojaDeHierroDelTitanic);
		for (i = 0; i < 5; i++) {
			oEspada2.agregar(oMangoDeMadera);
		}
		oEspada2.agregar(oCuerda);

		for (i = 0; i < 10; i++) {
			oHojaDeHierro.agregar(oHierro);
		}

		for (i = 0; i < 7; i++) {
			oMangoDeMadera.agregar(oMadera);
		}

		for (i = 0; i < 2; i++) {
			oPegamento.agregar(oSustanciaQuePegaSacadaDelArbol);
		}

		for (i = 0; i < 50; i++) {
			oSubmarino.agregar(oHierro);
		}

		oHojaDeHierroDelTitanic.agregar(oSubmarino);
		oHojaDeHierroDelTitanic.agregar(oHierro);

		// Recetario inicial (se carga del archivo)
		Recetario recetasBasicasCargadasDeArchivo = new Recetario();
		Receta espada1 = new Receta("Espada", "Básico", 60);
		Receta espada2 = new Receta("Espada", "Básico", 59);

		Receta hojaDeHierro = new Receta("Hoja de hierro", "Básico", 50);

		Receta mangoDeMadera = new Receta("Mango de madera", "Básico", 11);

		Receta pegamento = new Receta("Pegamento", "Básico", 25);

		Receta hojaDeHierroDelTitanic = new Receta("Hoja de hierro del Titanic", "Básico", 250);

		Receta submarino = new Receta("Submarino", "Básico", 1599);

		// Completar las recetas con sus ingredientes
		espada1.agregarIngrediente(oHojaDeHierro, 2);
		espada1.agregarIngrediente(oMangoDeMadera, 1);
		espada1.agregarIngrediente(oCuerda, 1);

		espada2.agregarIngrediente(oHojaDeHierroDelTitanic, 1);
		espada2.agregarIngrediente(oMangoDeMadera, 5);
		espada2.agregarIngrediente(oCuerda, 1);

		hojaDeHierro.agregarIngrediente(oHierro, 10);
		mangoDeMadera.agregarIngrediente(oMadera, 7);
		pegamento.agregarIngrediente(oSustanciaQuePegaSacadaDelArbol, 2);

		hojaDeHierroDelTitanic.agregarIngrediente(oHierro, 1);
		hojaDeHierroDelTitanic.agregarIngrediente(oSubmarino, 1);

		submarino.agregarIngrediente(oHierro, 50);

		// Agrego todas las recetas al Recetario Básico
				
		recetasBasicasCargadasDeArchivo.agregarReceta(espada1); //(!) CON ESTA RECETA PUEDO CRAFTEAR 0 EN PROLOG
		recetasBasicasCargadasDeArchivo.agregarReceta(espada2);
		recetasBasicasCargadasDeArchivo.agregarReceta(hojaDeHierro);
		recetasBasicasCargadasDeArchivo.agregarReceta(mangoDeMadera);
		recetasBasicasCargadasDeArchivo.agregarReceta(pegamento);
		recetasBasicasCargadasDeArchivo.agregarReceta(hojaDeHierroDelTitanic);
		recetasBasicasCargadasDeArchivo.agregarReceta(submarino);

		// Creo jugador
		Jugador jugador = new Jugador("Jugadorcito", recetasBasicasCargadasDeArchivo, "prolog/integracion.pl");

		// Inicializo inventario
		jugador.recolectar("Hoja de hierro del Titanic", 1);
		for (i = 0; i < 5; i++) {
			jugador.recolectar("Mango de madera", 1);
		}
		jugador.recolectar("Cuerda", 1);

		for (i = 0; i < 10; i++) {
			jugador.recolectar("Hierro", 1);
		}

		for (i = 0; i < 7; i++) {
			jugador.recolectar("Madera", 1);
		}

		for (i = 0; i < 2; i++) {
			jugador.recolectar("Sustancia que pega sacada del arbol", 1);
		}

		for (i = 0; i < 50; i++) {
			jugador.recolectar("Hierro", 1);
		}

		jugador.recolectar("Submarino", 1);
		jugador.recolectar("Hierro", 1);

		// A JUGAR!
		try {
			System.out.println("\n=== Pruebas de Jugador ===");

			System.out.println("\nInventario Inicial");
			jugador.consultarInventario();

			// 1. recolectar
			// FABRICAR 2 ESPADAS EXACTAMENTE (una con basicos, otra con primer nivel)
			jugador.recolectar("Hierro", 20);
			jugador.recolectar("Madera", 7);
			jugador.recolectar("Cuerda", 2);
			jugador.recolectar("Mango de madera", 1);
			jugador.recolectar("Hoja de hierro", 2);

			System.out.println("\nInventario despues de recolectar:");
			jugador.consultarInventario();

			// 2. soltar
			jugador.soltar("Hierro", 6);
			System.out.println("\nInventario despues de soltar:");
			jugador.consultarInventario();

			System.out.println("INVENTARIO:");
			jugador.consultarInventario();
			System.out.println("\n");

			// 3. consultarRecetaDesdeCero y consultarReceta
			System.out.println("\nRECETA DESDE CERO");
			jugador.verRecetasDesdeCero("Espada");
			System.out.println("\n");

			System.out.println("\nRECETA PRIMER NIVEL");
			jugador.verRecetas("Espada");
			System.out.println("\n");
			System.out.println("\n");

			// 4. consultarFaltantesPrimerNivel
			System.out.println("\nFaltantes primer nivel para Espada:");
			List<Map<ObjetoComponente, Integer>> falt1 = jugador.consultarFaltantesPrimerNivel("Espada");
			System.out.println(falt1);

			// 5. consultarFaltantesBasicos
			System.out.println("\nFaltantes básicos para Espada:");
			List<Map<ObjetoComponente, Integer>> faltB = jugador.consultarFaltantesBasicos("Espada");
			System.out.println(faltB);
			

			jugador.recolectar("Baston", 20);
			// 6. cuantoPuedoCraftear
			int maxCraftear = jugador.cuantoPuedoCraftear("Espada");
			System.out.println("\nPuedo craftear Espada " + maxCraftear + " veces");

			// 8. consultarObjetosCrafteables (vía Prolog)
			System.out.println("\nObjetos actualmente crafteables:");
			List<String> crafteables = jugador.consultarObjetosCrafteables();
			System.out.println(crafteables);

			// 7. craftear
			jugador.craftear("Espada");
			System.out.println("INVENTARIO DESPUES DE CRAFTEAR 1 ESPADA: ");
			jugador.consultarInventario();

			jugador.getHistorial();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
