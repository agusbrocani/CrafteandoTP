package ar.edu.unlam.crafteando.Clases;

import java.util.List;
import java.util.Map;

import ar.edu.unlam.crafteando.Jugador.*;
import ar.edu.unlam.crafteando.lectura.*;
import ar.edu.unlam.crafteando.loader.*;

public class CrafteandoTP {

    public static void main(String[] args) throws Exception {
        // 1. Leer los archivos JSON
        RecetaLectura recetasLeidas = GestorJson.leer("archivos/recetas.json", RecetaLectura.class);
        ObjetoLectura inventarioLeido = GestorJson.leer("archivos/inventario.json", ObjetoLectura.class);

        // 2. Cargar recetas e inventario
        Recetario recetario = new CargadorDeRecetas().cargar(recetasLeidas);
        Jugador jugador = new Jugador("Jugadorcito", recetario, "prolog/integracion.pl");
        new CargadorDeInventario().cargar(jugador, inventarioLeido);

        // 3. Estado inicial
        System.out.println("\n=== Pruebas de Jugador ===");
        System.out.println("\nInventario Inicial:");
        jugador.consultarInventario();

        // 4. Recolectar manualmente para simular parte del caso original
        jugador.recolectar(new ObjetoBasico("Hierro"), 20);
        jugador.recolectar(new ObjetoBasico("Madera"), 7);
        jugador.recolectar(new ObjetoBasico("Cuerda"), 2);
        jugador.recolectar(new ObjetoCompuesto("Mango de Madera"), 1);
        jugador.recolectar(new ObjetoCompuesto("Hoja de hierro"), 2);

        System.out.println("\nInventario despues de recolectar:");
        jugador.consultarInventario();

        // 5. Soltar algunos objetos
        jugador.soltar(new ObjetoBasico("Hierro"), 6);
        System.out.println("\nInventario despues de soltar:");
        jugador.consultarInventario();

        // 6. Mostrar recetas
        System.out.println("\nRECETA DESDE CERO");
        jugador.verRecetasDesdeCero("Espada de Hierro");

        System.out.println("\nRECETA PRIMER NIVEL");
        jugador.verRecetas("Espada de Hierro");

        // 7. Consultar faltantes
        System.out.println("\nFaltantes primer nivel para Espada de Hierro:");
        List<Map<ObjetoComponente, Integer>> faltantes1 = jugador.consultarFaltantesPrimerNivel("Espada de Hierro");
        System.out.println(faltantes1);

        System.out.println("\nFaltantes basicos para Espada de Hierro:");
        List<Map<ObjetoComponente, Integer>> faltantesB = jugador.consultarFaltantesBasicos("Espada de Hierro");
        System.out.println(faltantesB);

        // 8. Cuánto puedo craftear
        jugador.recolectar(new ObjetoBasico("Baston"), 20);
        int max = jugador.cuantoPuedoCraftear("Espada de Hierro");
        System.out.println("\nPuedo craftear Espada de Hierro " + max + " veces");

        // 9. Objetos actualmente crafteables
        System.out.println("\nObjetos actualmente crafteables:");
        List<String> disponibles = jugador.consultarObjetosCrafteables();
        System.out.println(disponibles);

        // 10. Craftear
        jugador.craftear("Espada de Hierro");
        System.out.println("\nINVENTARIO DESPUES DE CRAFTEAR 1 ESPADA DE HIERRO:");
        jugador.consultarInventario();
        
        System.out.println("\n=== Chequeo directo ===");
        ObjetoCompuesto espada = new ObjetoCompuesto("Espada de Hierro");

        if (jugador.cuantoHayDe(espada) > 0) {
            System.out.println("✅ La espada está en el inventario con cantidad: " + jugador.cuantoHayDe(espada));
        } else {
            System.out.println("❌ No se encontró la espada de hierro en el inventario");
        }

        // 11. Historial
        jugador.getHistorial();
        
        //12. Guardar Inventario
        jugador.guardarInventario("archivos/Inventario-out-test.json");
    }
}
