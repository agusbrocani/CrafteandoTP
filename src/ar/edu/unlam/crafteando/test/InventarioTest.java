package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import ar.edu.unlam.crafteando.Clases.*;
import ar.edu.unlam.crafteando.Jugador.Inventario;

public class InventarioTest {
    private Inventario inventario;
    private final String RUTA_JSON = "archivos/Inventario-out-test.json";
    @BeforeEach
    public void setUp() {
        inventario = new Inventario();

        inventario.agregar(new ObjetoBasico("Bastón"), 10);
        inventario.agregar(new ObjetoBasico("Madera"), 60);
        inventario.agregar(new ObjetoBasico("Hierro"), 20);
        inventario.agregar(new ObjetoBasico("Lana"), 5);
        inventario.agregar(new ObjetoBasico("Carbón Mineral"), 17);
    }

    private ObjetoComponente buscar(String nombre) {
        return inventario.getObjetos().keySet().stream()
                .filter(obj -> obj.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Objeto no encontrado: " + nombre));
    }

    @Test
    public void eliminarCantidadValida_deberiaReducirCantidad() {
        ObjetoComponente baston = buscar("Bastón");

        inventario.quitar(baston, 8);
        assertEquals(2, inventario.obtenerCantidad(baston).intValue());
    }

    @Test
    public void eliminarCantidadExacta_deberiaEliminarDelMap() {
        ObjetoComponente baston = buscar("Bastón");

        inventario.quitar(baston, 10);
        assertFalse(inventario.contiene(baston));
    }

    @Test
    public void eliminarCantidadMayor_lanzaExcepcion() {
        ObjetoComponente baston = buscar("Bastón");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar(baston, 11);
        });

        assertEquals("Cantidad insuficiente. Actual: 10, Solicitada: 11", ex.getMessage());
    }

    @Test
    public void eliminarObjetoInexistente_lanzaExcepcion() {
        ObjetoComponente cama = new ObjetoBasico("Cama");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            inventario.quitar(cama, 1);
        });

        assertEquals("El objeto no está en el inventario.", ex.getMessage());
    }

    @Test
    @Disabled("Test manual de impresión. No tiene aserciones.")
    public void modificarYMostrarInventarioOK() {
        System.out.println("Inventario antes de modificar:");
        inventario.ver();

        inventario.quitar(buscar("Bastón"), 5);
        inventario.agregar(buscar("Madera"), 10);
        inventario.agregar(buscar("Hierro"), 20);

        System.out.println("\nInventario modificado:");
        inventario.ver();
    }

    @Test
    public void testGuardarComoJson(@TempDir Path tempDir) throws IOException {
        Inventario inv = new Inventario();
        inv.agregar(new ObjetoBasico("Madera"), 10);
        inv.agregar(new ObjetoBasico("Hierro"), 20);

        Path ruta = tempDir.resolve("inventario-test.json");
        inv.guardarComoJson(ruta.toString());

        //assertTrue(java.nio.file.Files.exists(ruta), "El archivo JSON no fue creado.");
    }
}
