package ar.edu.unlam.crafteando;

import java.util.List;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

public class CrafteandoTP {
	public static void main(String[] args) {

		int i;

		// Objetos compuestos
		ObjetoCompuesto oEspada1 = new ObjetoCompuesto("Espada");
		ObjetoCompuesto oEspada2 = new ObjetoCompuesto("Espada");
		ObjetoCompuesto oHojaDeHierro = new ObjetoCompuesto("Hierro");
		ObjetoCompuesto oMangoDeMadera = new ObjetoCompuesto("Mango de madera");
		ObjetoCompuesto oPegamento = new ObjetoCompuesto("Pegamento");
		ObjetoCompuesto oHojaDeHierroDelTitanic = new ObjetoCompuesto("Hoja de hierro del Titanic");
		ObjetoCompuesto oSubmarino = new ObjetoCompuesto("Submarino");

		// Objetos básicos
		ObjetoBasico oMadera = new ObjetoBasico("Madera");
		ObjetoBasico oCuerda = new ObjetoBasico("Cuerda");
		ObjetoBasico oHierro = new ObjetoBasico("Hierro");
		ObjetoBasico oSustanciaQuePegaSacadaDelArbol = new ObjetoBasico("Sustancia que pega sacada del árbol");
		
		oEspada1.agregar(oHierro); oEspada1.agregar(oHierro);
		oEspada1.agregar(oMadera); oEspada1.agregar(oCuerda);

		
		 oEspada1.agregar(oHierro); oEspada1.agregar(oHierro);
		 oEspada1.agregar(oMadera); oEspada1.agregar(oCuerda);
		 
		 oEspada2.agregar(oHojaDeHierroDelTitanic); for (i = 0; i < 5; i++) {
		 oEspada2.agregar(oMangoDeMadera); } oEspada2.agregar(oCuerda);
		 
		 for (i = 0; i < 10; i++) { oHojaDeHierro.agregar(oHierro); }
		 
		 for (i = 0; i < 7; i++) { oMangoDeMadera.agregar(oMadera); }
		 
		 for (i = 0; i < 2; i++) {
		 oPegamento.agregar(oSustanciaQuePegaSacadaDelArbol); }
		  
		 for (i = 0; i < 50; i++) { oSubmarino.agregar(oHierro); }
		 
		 oHojaDeHierroDelTitanic.agregar(oSubmarino);
		 oHojaDeHierroDelTitanic.agregar(oHierro);
		 

		// Recetario inicial (se carga del archivo)
		Recetario recetasBasicasCargadasDeArchivo = new Recetario();
		Receta espada1 = new Receta("Espada", "Básico", 60);
		Receta espada2 = new Receta("Espada", "Básico", 59);

		Receta hojaDeHierro = new Receta("HojaDeHierro", "Básico", 50);

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
		recetasBasicasCargadasDeArchivo.agregarReceta(espada1);
		recetasBasicasCargadasDeArchivo.agregarReceta(espada2);
		recetasBasicasCargadasDeArchivo.agregarReceta(hojaDeHierro);
		recetasBasicasCargadasDeArchivo.agregarReceta(mangoDeMadera);
		recetasBasicasCargadasDeArchivo.agregarReceta(pegamento);
		recetasBasicasCargadasDeArchivo.agregarReceta(hojaDeHierroDelTitanic);
		recetasBasicasCargadasDeArchivo.agregarReceta(submarino);

		// Creo jugador
		Jugador jugador = new Jugador("Jugadorcito", recetasBasicasCargadasDeArchivo, "prolog/integracion.pl"); //"prolog/integracion.pl"

		// Inicializo inventario
		jugador.recolectar(oHojaDeHierroDelTitanic, 1);
		for (i = 0; i < 5; i++) {
			jugador.recolectar(oMangoDeMadera, 1);
		}
		jugador.recolectar(oCuerda, 1);

		for (i = 0; i < 10; i++) {
			jugador.recolectar(oHierro, 1);
		}

		for (i = 0; i < 7; i++) {
			jugador.recolectar(oMadera, 1);
		}

		for (i = 0; i < 2; i++) {
			jugador.recolectar(oSustanciaQuePegaSacadaDelArbol, 1);
		}

		for (i = 0; i < 50; i++) {
			jugador.recolectar(oHierro, 1);
		}

		jugador.recolectar(oSubmarino, 1);
		jugador.recolectar(oHierro, 1);

		// A JUGAR!
		try {
			System.out.println("\n=== Pruebas de Jugador ===");

			/*
			System.out.println("\nInventario Inicial");
			jugador.consultarInventario();

			// 1. recolectar
			jugador.recolectar(oHierro, 5);
			jugador.recolectar(oMadera, 3);
			System.out.println("\nInventario despues de recolectar:");
			jugador.consultarInventario();

			// 2. soltar
			jugador.soltar(oHierro,6);
			System.out.println("\nInventario despues de soltar:");
			jugador.consultarInventario();

			// 3. consultarRecetaDesdeCero y consultarReceta
			System.out.println("\nMostrar receta completa desde cero de Espada:");
			jugador.verRecetasDesdeCero(oEspada1);
			System.out.println("\n\nMostrar receta de primer nivel de Espada:");
			jugador.verRecetas(oEspada2);
			
			// 4. consultarFaltantesPrimerNivel
			System.out.println("\nFaltantes primer nivel para Espada:");
			List<Map<ObjetoComponente, Integer>> falt1 = jugador.consultarFaltantesPrimerNivel(oEspada1);
			System.out.println(falt1);

			// 5. consultarFaltantesBasicos
			System.out.println("\nFaltantes básicos para Espada:");
			List<Map<ObjetoComponente, Integer>> faltB = jugador.consultarFaltantesBasicos(oEspada1);
			System.out.println(faltB);
			*/
			

			/*
			// 6. cuantoPuedoCraftear
			int maxCraftear = jugador.cuantoPuedoCraftear(oEspada1, espada1);
			System.out.println("\nPuedo craftear Espada " + maxCraftear + " veces");

			 // 7. craftear jugador.craftear(oEspada1, espada1);
			 System.out.println("Inventario tras craftear 1 Espada:");
			 jugador.consultarInventario();
			 */
			 
			 // 8. consultarObjetosCrafteables (vía Prolog)
			 System.out.println("\nObjetos actualmente crafteables:"); List<String>
			 crafteables = jugador.consultarObjetosCrafteables();
			 System.out.println(crafteables);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
