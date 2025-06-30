package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;
import java.io.IOException;

public class InventarioTest {
    private Inventario inventario;
    private ObjetoCompuesto baston;
    private final String RUTA_JSON = "archivos/Inventario-out-test.json";
	
    @BeforeEach
    public void setUp() throws Exception {
        inventario = new Inventario();
        baston = new ObjetoCompuesto("Bastón");
    	
        ObjetoBasico madera = new ObjetoBasico("Madera");
    	ObjetoBasico hierro = new ObjetoBasico("Hierro");
    	ObjetoBasico lana = new ObjetoBasico("Lana");
    	ObjetoBasico carbonMineral = new ObjetoBasico("Carbón Mineral");
        
    	inventario.agregar(baston);
        inventario.agregar(madera);
        inventario.agregar(lana);
        inventario.agregar(hierro);
        inventario.agregar(carbonMineral);
    }
    
    @Test
    public void eliminarCantidadValida_deberiaReducirCantidad() throws Exception {
    	ObjetoCompuesto bastoncito = new ObjetoCompuesto("Bastón");
    	inventario.quitar(bastoncito);
        Integer res = 2;
        assertEquals(res, inventario.obtenerCantidad(baston));
    }

    @Test
    public void eliminarCantidadExacta_deberiaEliminarDelMap() throws Exception {
    	ObjetoCompuesto bastoncito = new ObjetoCompuesto("Bastón");
    	inventario.quitar(bastoncito);
        assertFalse(inventario.contiene(baston));
    }

    @Test
    public void eliminarCantidadMayor_lanzaExcepcion() throws Exception {
    	ObjetoCompuesto bastoncito = new ObjetoCompuesto("Bastón");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar(bastoncito);
        });
        assertEquals("Cantidad insuficiente. Actual: 10, Solicitada: 11", ex.getMessage());
    }

    @Test
    public void eliminarObjetoInexistente_lanzaExcepcion() throws Exception {
        ObjetoCompuesto cama = new ObjetoCompuesto("Cama");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar(cama);
        });
        assertEquals("El objeto no está en el inventario.", ex.getMessage());
    }
    
    @Test
    public void modificarYMostrarInventarioOK() throws Exception{
    	
    	System.out.println("Inventario antes de modificar:");
    	inventario.ver();
    	
    	baston = new ObjetoCompuesto("Bastón");
    	ObjetoBasico madera = new ObjetoBasico("Madera");
    	ObjetoBasico hierro = new ObjetoBasico("Hierro");
    	ObjetoCompuesto baston = new ObjetoCompuesto("Bastón");
    	inventario.quitar(baston);
    	inventario.agregar(madera);
    	inventario.agregar(hierro);
    	
    	System.out.println("\nInventario modificado:");
    	inventario.ver();
    }
    
    @Test
    public void testGuardarComoJson() throws IOException {
        Inventario inventario = new Inventario();

        ObjetoBasico madera = new ObjetoBasico("Madera");
        ObjetoBasico hierro = new ObjetoBasico("Hierro");

        inventario.agregar(madera);
        inventario.agregar(hierro);

        inventario.guardarComoJson(RUTA_JSON);
        
        

        	
    }

}
