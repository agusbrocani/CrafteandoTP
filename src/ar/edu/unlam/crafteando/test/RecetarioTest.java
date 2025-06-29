package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;

public class RecetarioTest {
	private Recetario recetario;
    private ObjetoBasico madera;
    private ObjetoBasico hierro;
    private ObjetoCompuesto baston;
    private ObjetoCompuesto espada;

    @BeforeEach
    public void setUp() throws Exception {
        recetario = new Recetario();

        madera = new ObjetoBasico("Madera", 1);
        hierro = new ObjetoBasico("Hierro", 1);

        baston = new ObjetoCompuesto("Bastón", 1);
        baston.agregar(madera);
        baston.agregar(madera); 

        espada = new ObjetoCompuesto("Espada", 1);
        espada.agregar(baston);
        espada.agregar(hierro);

        // Armamos la receta de la espada
        Receta recetaEspada = new Receta("Espada", 1);
        recetaEspada.agregarIngrediente(baston, 1);
        recetaEspada.agregarIngrediente(hierro, 1);

        recetario.agregarReceta(recetaEspada);
    }

    @Test
    public void testAgregarRecetaValida() {
        assertEquals(1, recetario.getRecetas().size());
    }

    @Test
    public void testAgregarRecetaNula_lanzaExcepcion() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            recetario.agregarReceta(null);
        });
        assertEquals("Receta nula", ex.getMessage());
    }

    @Test
    public void testVerIngredientesPrimerNivel_espada() {
        Map<ObjetoComponente, Integer> ingredientes = recetario.verIngredientesPrimerNivel("Espada");
        
        assertEquals(2, ingredientes.size());
        assertTrue(ingredientes.containsKey(baston));
        assertTrue(ingredientes.containsKey(hierro));
        //assertEquals(1, ingredientes.get(baston));
        //assertEquals(1, ingredientes.get(hierro));
        recetario.mostrarReceta("Espada");
        recetario.mostrarRecetaDesdeCero("Espada");
    }

    @Test
    public void testVerIngredientesPrimerNivel_noExiste_lanzaExcepcion() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            recetario.verIngredientesPrimerNivel("Arco");
        });
        assertEquals("No se encontró receta para: Arco", ex.getMessage());
    }
}

