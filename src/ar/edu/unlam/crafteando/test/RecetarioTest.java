package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;

public class RecetarioTest {
    
    private Recetario recetario;
    private ObjetoBasico madera;
    private ObjetoBasico hierro;
    private ObjetoBasico carbonMineral;
    private ObjetoBasico carbonVegetal;
    private ObjetoCompuesto hacha;
    private ObjetoCompuesto antorcha;
    private ObjetoCompuesto baston;
    private Receta recetaHacha;
    private Receta recetaAntorcha1;
    private Receta recetaAntorcha2;
    private Receta recetaBaston;

    @BeforeEach
    void setUp() {
        recetario = new Recetario();
        
        // Crear objetos básicos
        madera = new ObjetoBasico("Madera");
        hierro = new ObjetoBasico("Hierro");
        carbonMineral = new ObjetoBasico("Carbon Mineral");
        carbonVegetal = new ObjetoBasico("Carbon Vegetal");
        
        // Crear objetos compuestos
        hacha = new ObjetoCompuesto("Hacha");
        antorcha = new ObjetoCompuesto("Antorcha");
        baston = new ObjetoCompuesto("Bastón");
        
        // Crear receta para Bastón
        recetaBaston = new Receta("RecetaBaston","Bastón", 12);
        recetaBaston.agregarIngrediente(madera, 2);
        
        // Crear receta para Hacha 
        recetaHacha = new Receta("RecetaHacha","Hacha", 30);
        recetaHacha.agregarIngrediente(baston, 1);
        recetaHacha.agregarIngrediente(hierro, 3);
        
        // Crear recetas para Antorcha (múltiples)
        recetaAntorcha1 = new Receta("RecetaAntorcha","Antorcha", 5);
        recetaAntorcha1.agregarIngrediente(baston, 1);
        recetaAntorcha1.agregarIngrediente(carbonMineral, 1);
        
        recetaAntorcha2 = new Receta("RecetaAntorcha","Antorcha", 5);
        recetaAntorcha2.agregarIngrediente(baston, 1);
        recetaAntorcha2.agregarIngrediente(carbonVegetal, 1);
        
        // Agregar recetas al recetario
        recetario.agregarReceta(recetaHacha);
        recetario.agregarReceta(recetaAntorcha1);
        recetario.agregarReceta(recetaAntorcha2);
        recetario.agregarReceta(recetaBaston);
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
            assertEquals(4, recetario.getRecetas().size());
            assertTrue(recetario.getRecetas().contains(recetaHacha));
            assertTrue(recetario.getRecetas().contains(recetaAntorcha1));
            assertTrue(recetario.getRecetas().contains(recetaAntorcha2));
            assertTrue(recetario.getRecetas().contains(recetaBaston));
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
    }

    @Nested
    @DisplayName("Pruebas de Búsqueda de Recetas")
    class BusquedaDeRecetas {
        
        @Test
        @DisplayName("Debe obtener receta de primer nivel correctamente")
        void debeObtenerRecetaPrimerNivel() {
            Map<ObjetoComponente, Integer> ingredientes = recetario.obtenerRecetaPrimerNivel("Hacha");
            
            assertEquals(2, ingredientes.size());
        }
        
        @Test
        @DisplayName("Debe obtener todas las recetas para un objeto")
        void debeObtenerTodasLasRecetas() {
            List<Map<ObjetoComponente, Integer>> todasLasRecetas = recetario.obtenerTodasLasRecetas("Antorcha");
            
            assertEquals(2, todasLasRecetas.size());
            
            // Primera receta
            Map<ObjetoComponente, Integer> receta1 = todasLasRecetas.get(0);
            assertEquals(2, receta1.size());
            
            // Segunda receta
            Map<ObjetoComponente, Integer> receta2 = todasLasRecetas.get(1);
            assertEquals(2, receta2.size());
        }
        
        
        @Test
        @DisplayName("Debe lanzar excepción para receta inexistente")
        void debeLanzarExcepcionParaRecetaInexistente() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> recetario.obtenerRecetaPrimerNivel("Espada")
            );
            assertEquals("No se encontró receta para: Espada", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas de obtener y mostrar Recetas")
    class RecetasObtenerYMostrar {
        
        @Test
        @DisplayName("Debe obtener receta desde cero - objeto simple")
        void debeObtenerRecetaDesdeCero() {
            Map<ObjetoBasico, Integer> ingredientesBasicos = recetario.obtenerRecetaDesdeCero("Hacha");
            
            assertEquals(2, ingredientesBasicos.size());

        }
        
        @Test
        @DisplayName("Mostrar receta simple")
        void mostrarRecetaSimple() {
            recetario.mostrarReceta("Hacha");

        }
        
        @Test
        @DisplayName("Mostrar todas las recetas")
        void mostrarTodasLasRecetas() {
            recetario.mostrarTodasLasRecetas("Antorcha");
        }
        
        @Test
        @DisplayName("Mostrar receta desde cero")
        void mostrarRecetaDesdeCero() {
            recetario.mostrarRecetaDesdeCero("Hacha");
        }
        

    @Nested
    @DisplayName("Pruebas de Utilidades")
    class Utilidades {
 
        @Test
        @DisplayName("Debe lanzar excepción al validar receta inválida")
        void debeLanzarExcepcionAlValidarRecetaInvalida() {
            Receta recetaInvalida = new Receta();
            recetaInvalida.setTipo("Objeto Inválido");
            recetaInvalida.setTiempoEnSegundos(10);
            // No agregamos ingredientes - receta inválida
            
            recetario.agregarReceta(recetaInvalida);
            
            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> recetario.validarRecetas()
            );
            assertEquals(Constant.EXCEPCION_RECETA_SIN_INGREDIENTES, exception.getMessage());
        }
    }


    @Nested
    @DisplayName("Pruebas de casos borde")
    class CasosBorde {
        
        @Test
        @DisplayName("Debe manejar nombres con diferentes casos")
        void debeManjarNombresConDiferentesCasos() {
            Map<ObjetoComponente, Integer> ingredientes1 = recetario.obtenerRecetaPrimerNivel("hacha");
            Map<ObjetoComponente, Integer> ingredientes2 = recetario.obtenerRecetaPrimerNivel("HACHA");
            Map<ObjetoComponente, Integer> ingredientes3 = recetario.obtenerRecetaPrimerNivel("Hacha");
            
            assertEquals(ingredientes1, ingredientes2);
            assertEquals(ingredientes2, ingredientes3);
        }
        

        @Test
        @DisplayName("Debe manejar objetos sin ingredientes")
        void debeManjarObjetosSinIngredientes() {
            // Los objetos básicos no tienen recetas en el recetario
            // pero pueden ser usados como ingredientes
            assertThrows(IllegalArgumentException.class, () -> {
                recetario.obtenerRecetaPrimerNivel("Madera");
            });
        }
    }
   }
 }

