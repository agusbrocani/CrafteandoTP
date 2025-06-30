package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;
import java.io.IOException;

public class InventarioTest {
    private Inventario inventario;
    private final String RUTA_JSON = "archivos/Inventario-out-test.json";
	
    @BeforeEach
    public void setUp() {
        inventario = new Inventario();

        inventario.agregar("Bastón", 10);
        inventario.agregar("Madera", 60);
        inventario.agregar("Hierro", 20);
        inventario.agregar("Lana", 5);
        inventario.agregar("Carbón Mineral", 17);
    }
    
    @Test
    public void eliminarCantidadValida_deberiaReducirCantidad() throws Exception {
    	inventario.quitar("Bastón", 8);
        Integer res = 2;
        assertEquals(res, inventario.obtenerCantidad("Bastón"));
    }

    @Test
    public void eliminarCantidadExacta_deberiaEliminarDelMap() throws Exception {
    	inventario.quitar("Bastón", 10);
        assertFalse(inventario.contiene("Bastón"));
    }

    @Test
    public void eliminarCantidadMayor_lanzaExcepcion() throws Exception {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar("Bastón", 11);
        });
        assertEquals("Cantidad insuficiente. Actual: 10, Solicitada: 11", ex.getMessage());
    }

    @Test
    public void eliminarObjetoInexistente_lanzaExcepcion() throws Exception {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar("Cama",1);
        });
        assertEquals("El objeto no está en el inventario.", ex.getMessage());
    }
    
    @Test
    public void modificarYMostrarInventarioOK() throws Exception{
    	
    	System.out.println("Inventario antes de modificar:");
    	inventario.ver();
    	
    	inventario.quitar("Bastón",5); 
    	inventario.agregar("Madera",10);
    	inventario.agregar("Hierro",20);
    	
    	System.out.println("\nInventario modificado:");
    	inventario.ver();
    }
    
    @Test
    public void testGuardarComoJson() throws IOException {
        Inventario inventario = new Inventario();

        inventario.agregar("Madera", 10);
        inventario.agregar("Hierro", 20);

        inventario.guardarComoJson(RUTA_JSON);
        
        

        	
    }

}
