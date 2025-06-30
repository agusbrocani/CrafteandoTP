package ar.edu.unlam.crafteando.test;

import org.junit.jupiter.api.Test;
import ar.edu.unlam.crafteando.*;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ObjetoBasicoTest {
	@Test
    void constructor() {
		String nombre = "Carbón Mineral";
        ObjetoBasico carbonMineral = new ObjetoBasico(nombre);
        assertEquals(nombre, carbonMineral.getNombre());
    }
	
	@Test
    void deberiaObtenerseASiMismo() throws Exception {
		String nombre = "Carbón Mineral";
        ObjetoBasico carbonMineral = new ObjetoBasico(nombre);
        Map<ObjetoComponente, Integer> obtenido = carbonMineral.obtener();

        assertEquals(1, obtenido.size());
        assertTrue(obtenido.containsKey(carbonMineral));
        assertEquals(1, obtenido.get(carbonMineral));

        for (ObjetoComponente clave : obtenido.keySet()) {
            assertEquals(nombre, clave.getNombre());
        }
    }
    
	
}