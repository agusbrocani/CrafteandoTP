package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import ar.edu.unlam.crafteando.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InventarioTest {
    private Inventario inventario;
    private ObjetoCompuesto baston;
    private final String RUTA_JSON = "C:\\Users\\lulis\\eclipse-workspace\\CrafteandoTP\\archivos\\Inventario-out-test.json";
	
    @BeforeEach
    public void setUp() throws Exception {
        inventario = new Inventario();
        baston = new ObjetoCompuesto("Bastón", 10);
    	
        ObjetoBasico madera = new ObjetoBasico("Madera", 60);
    	ObjetoBasico hierro = new ObjetoBasico("Hierro", 20);
    	ObjetoBasico lana = new ObjetoBasico("Lana", 5);
    	ObjetoBasico carbonMineral = new ObjetoBasico("Carbón Mineral", 17);
        
    	inventario.agregar(baston);
        inventario.agregar(madera);
        inventario.agregar(lana);
        inventario.agregar(hierro);
        inventario.agregar(carbonMineral);
    }
    
    @Test
    public void eliminarCantidadValida_deberiaReducirCantidad() throws Exception {
    	ObjetoCompuesto bastoncito = new ObjetoCompuesto("Bastón", 8);
    	inventario.quitar(bastoncito);
        Integer res = 2;
        assertEquals(res, inventario.obtenerCantidad(baston));
    }

    @Test
    public void eliminarCantidadExacta_deberiaEliminarDelMap() throws Exception {
    	ObjetoCompuesto bastoncito = new ObjetoCompuesto("Bastón", 10);
    	inventario.quitar(bastoncito);
        assertFalse(inventario.contiene(baston));
    }

    @Test
    public void eliminarCantidadMayor_lanzaExcepcion() throws Exception {
    	ObjetoCompuesto bastoncito = new ObjetoCompuesto("Bastón", 11);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar(bastoncito);
        });
        assertEquals("Cantidad insuficiente. Actual: 10, Solicitada: 11", ex.getMessage());
    }

    @Test
    public void eliminarObjetoInexistente_lanzaExcepcion() throws Exception {
        ObjetoCompuesto cama = new ObjetoCompuesto("Cama", 5);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar(cama);
        });
        assertEquals("El objeto no está en el inventario.", ex.getMessage());
    }
    
    @Test
    public void modificarYMostrarInventarioOK() throws Exception{
    	
    	System.out.println("Inventario antes de modificar:");
    	inventario.ver();
    	
    	baston = new ObjetoCompuesto("Bastón", 5);
    	ObjetoBasico madera = new ObjetoBasico("Madera", 60);
    	ObjetoBasico hierro = new ObjetoBasico("Hierro", 30);
    	ObjetoCompuesto baston = new ObjetoCompuesto("Bastón", 5);
    	inventario.quitar(baston);
    	inventario.agregar(madera);
    	inventario.agregar(hierro);
    	
    	System.out.println("\nInventario modificado:");
    	inventario.ver();
    }
    
    @Test
    public void testGuardarComoJson() throws IOException {
        Inventario inventario = new Inventario();

        ObjetoBasico madera = new ObjetoBasico("Madera", 3);
        ObjetoBasico hierro = new ObjetoBasico("Hierro", 5);

        inventario.agregar(madera);
        inventario.agregar(hierro);

        inventario.guardarComoJson(RUTA_JSON);
        
        

        	
    }

}
