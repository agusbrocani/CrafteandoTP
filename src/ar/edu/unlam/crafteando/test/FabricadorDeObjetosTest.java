package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.loader.*;

import ar.edu.unlam.crafteando.Clases.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FabricadorDeObjetosTest {

    @Test
    void fabricaObjetoBasico() throws Exception {
        FabricadorDeObjetos f = new FabricadorDeObjetos();
        ObjetoComponente obj = f.construir("Hierro", "Basico");

        assertTrue(obj instanceof ObjetoBasico);
        assertEquals("Hierro", obj.getNombre());
    }

    @Test
    void fabricaObjetoCompuesto() throws Exception {
        FabricadorDeObjetos f = new FabricadorDeObjetos();
        ObjetoComponente obj = f.construir("Espada", "Compuesto");

        assertTrue(obj instanceof ObjetoCompuesto);
        assertEquals("Espada", obj.getNombre());
    }

    @Test
    void tipoDesconocidoLanzaExcepcion() {
        FabricadorDeObjetos f = new FabricadorDeObjetos();
        assertThrows(IllegalArgumentException.class, () -> {
            f.construir("Invalido", "Desconocido");
        });
    }
}
