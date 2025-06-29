package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;

public class RecetarioTest {
	private Recetario recetario;

    @BeforeEach
    public void setUp() throws Exception {
        recetario = new Recetario();

        ObjetoBasico madera = new ObjetoBasico("Madera", 1);
        ObjetoBasico hierro = new ObjetoBasico("Hierro", 1);

        ObjetoCompuesto baston = new ObjetoCompuesto("Bastón", 1);
        ObjetoCompuesto espada = new ObjetoCompuesto("Espada", 1);

        Receta recetaBaston = new Receta("Bastón", 10);
        recetaBaston.agregarIngrediente(madera, 2);

        Receta recetaEspada = new Receta("Espada", 30);
        recetaEspada.agregarIngrediente(baston, 1);
        recetaEspada.agregarIngrediente(hierro, 1);

        recetario.agregarReceta(recetaBaston);
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
    void testMostrarReceta() {
        recetario.mostrarReceta("Bastón");
        recetario.mostrarReceta("Espada");
    }

    @Test
    void testMostrarRecetaDesdeCero() {

        recetario.mostrarRecetaDesdeCero("Espada");
        recetario.mostrarRecetaDesdeCero("Bastón");

    }

    @Test
    void testMostrarRecetaInexistente() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            recetario.mostrarReceta("Hacha");
        });
        assertTrue(ex.getMessage().contains("No se encontró receta"));
    }

}

