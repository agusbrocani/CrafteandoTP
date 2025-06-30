package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;

public class RecetarioTest {
    
    private Recetario recetario;
    private ObjetoBasico carbonMineral;
    private ObjetoBasico carbonVegetal;
    private Receta recetaHacha;
    private Receta recetaAntorcha1;
    private Receta recetaAntorcha2;
    private Receta recetaBaston;
    private Receta recetaPuerta;
    private List<String> mesasDisponibles;

    @BeforeEach
    void setUp() {
        recetario = new Recetario();
        mesasDisponibles = new ArrayList<String>();
        mesasDisponibles.add("Carpintería");

        // Crear objetos básicos
        ObjetoBasico madera = new ObjetoBasico("Madera");
        ObjetoBasico hierro = new ObjetoBasico("Hierro");
        carbonMineral = new ObjetoBasico("Carbon Mineral");
        carbonVegetal = new ObjetoBasico("Carbon Vegetal");

        // Crear recetas
        recetaBaston = new Receta("Bastón", "Básica", 12);
        recetaBaston.agregarIngrediente(madera, 2);

        recetaHacha = new Receta("Hacha", "Básica", 30);
        recetaHacha.agregarIngrediente(new ObjetoCompuesto("Bastón"), 1);
        recetaHacha.agregarIngrediente(hierro, 3);

        recetaAntorcha1 = new Receta("Antorcha", "Básica", 5);
        recetaAntorcha1.agregarIngrediente(new ObjetoCompuesto("Bastón"), 1);
        recetaAntorcha1.agregarIngrediente(carbonMineral, 1);

        recetaAntorcha2 = new Receta("Antorcha", "Básica", 5);
        recetaAntorcha2.agregarIngrediente(new ObjetoCompuesto("Bastón"), 1);
        recetaAntorcha2.agregarIngrediente(carbonVegetal, 1);
        
        recetaPuerta = new Receta("Puerta", "Carpintería", 10);
        recetaPuerta.agregarIngrediente(madera, 6);
        
        // Agregar recetas al recetario
        recetario.agregarReceta(recetaBaston);
        recetario.agregarReceta(recetaHacha);
        recetario.agregarReceta(recetaAntorcha1);
        recetario.agregarReceta(recetaAntorcha2);
        recetario.agregarReceta(recetaPuerta);
    }
    
    @Nested
    @DisplayName("Pruebas de Construcción y Validación")
    class ConstruccionYValidacion {
        
        @Test
        @DisplayName("Debe crear un recetario vacío")
        void debeCrearRecetarioVacio() {
            Recetario nuevoRecetario = new Recetario();
            assertTrue(nuevoRecetario.getRecetas().isEmpty());
        }
        
        @Test
        @DisplayName("Debe agregar recetas correctamente")
        void debeAgregarRecetasCorrectamente() {
            assertEquals(5, recetario.getRecetas().size());
            assertTrue(recetario.getRecetas().contains(recetaHacha));
            assertTrue(recetario.getRecetas().contains(recetaAntorcha1));
            assertTrue(recetario.getRecetas().contains(recetaAntorcha2));
            assertTrue(recetario.getRecetas().contains(recetaBaston));
            assertTrue(recetario.getRecetas().contains(recetaPuerta));
        }
        
        @Test
        @DisplayName("Debe lanzar excepción al agregar receta nula")
        void debeLanzarExcepcionAlAgregarRecetaNula() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> recetario.agregarReceta(null)
            );
            assertEquals("Receta nula", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe lanzar excepción al validar receta inválida")
        void debeLanzarExcepcionAlValidarRecetaInvalida() {
            Receta recetaInvalida = new Receta();
            recetaInvalida.setTipo("Objeto Inválido");
            recetaInvalida.setTiempoEnSegundos(10);
            
            recetario.agregarReceta(recetaInvalida);
            
            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> recetario.validarRecetas()
            );
            assertEquals(Constant.EXCEPCION_RECETA_SIN_INGREDIENTES, exception.getMessage());
        }
    }


    @Nested
    @DisplayName("Obtener y mostrar Recetas")
    class RecetasObtenerYMostrar {
        
        @Test
        @DisplayName("Debe obtener receta desde cero para Hacha")
        void debeObtenerRecetaDesdeCero() {
        	List<Map<ObjetoBasico, Integer>> recetasDesdeCero = recetario.obtenerRecetaDesdeCero("Hacha");

        	Map<ObjetoBasico, Integer> primera = recetasDesdeCero.get(0);

        	// Se verifica que la receta tenga exactamente 2 ingredientes
        	assertEquals(2, primera.size());
        }
        
        @Test
        @DisplayName("Mostrar todas las recetas 1er Nivel para una Antorcha")
        void mostrarTodasLasRecetasParaUnaAntorcha() {
            recetario.mostrarReceta("Antorcha", mesasDisponibles);
        }
        
        @Test
        @DisplayName("Mostrar todas las recetas 1er Nivel para una Puerta")
        void mostrarTodasLasRecetasParaUnaPuerta() {
            recetario.mostrarReceta("Puerta", mesasDisponibles);
        }
        
        @Test
        @DisplayName("Mostrar receta desde cero para Antorcha")
        void mostrarRecetaDesdeCero() {
            recetario.mostrarRecetaDesdeCero("Antorcha", mesasDisponibles);
        }
   
   }
    
    @Nested
    @DisplayName("Pruebas de casos borde")
    class CasosBorde {
        
        @Test
        @DisplayName("Debe manejar nombres con diferentes casos")
        void debeManjarNombresConDiferentesCasos() {
            List<Map<ObjetoComponente, Integer>> ingredientes1 = recetario.obtenerReceta("hacha");
            List<Map<ObjetoComponente, Integer>> ingredientes2 = recetario.obtenerReceta("HACHA");
            List<Map<ObjetoComponente, Integer>> ingredientes3 = recetario.obtenerReceta("Hacha");
            
            assertEquals(ingredientes1, ingredientes2);
            assertEquals(ingredientes2, ingredientes3);
        }
    }
 }

