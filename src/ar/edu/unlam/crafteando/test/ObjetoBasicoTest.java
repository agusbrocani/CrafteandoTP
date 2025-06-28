package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoBasicoTest {

	@Test
    void crearObjetoBasicoConCantidadValida() throws Exception {
        ObjetoBasico piedra = new ObjetoBasico("Piedra", 3);
        assertEquals("Piedra", piedra.getNombre());
        assertEquals(3, piedra.getCantidad());
    }

    @Test
    void obtenerDebeDevolverMapaConElMismoObjeto() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera", 5);
        Map<ObjetoComponente, Integer> componentes = madera.obtener();

        assertEquals(1, componentes.size());
        assertTrue(componentes.containsKey(madera));
        assertEquals(5, componentes.get(madera));
    }

    @Test
    void cantidadNegativaLanzaExcepcion() {
        Exception ex = assertThrows(Exception.class, () -> {
            new ObjetoBasico("Arena", -1);
        });
        assertEquals(Constant.EXCEPCION_CANTIDAD_NEGATIVA, ex.getMessage());
    }
    
    @Test
    void compararDosObjetosBasicosDistintos() throws Exception {
    	ObjetoBasico madera = new ObjetoBasico("Madera", 5);
    	ObjetoBasico piedra = new ObjetoBasico("Piedra", 5);
    	
    	assertFalse(madera.equals(piedra));
    }
    
    @Test
    void compararDosObjetosBasicosIguales() throws Exception {
    	ObjetoBasico madera = new ObjetoBasico("Madera", 5);
    	
    	assertTrue(madera.equals(madera));
    }
    
    @Test
    void compararContraObjetoNull() throws Exception {
    	ObjetoBasico madera = new ObjetoBasico("Madera", 5);
    	
    	assertFalse(madera.equals(null));
    }
}
