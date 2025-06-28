package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoBasicoTest {

    @Test
    void queSeCreeConNombreYCantidadCorrecta() throws Exception {
        ObjetoBasico piedra = new ObjetoBasico("piedra", 5);

        assertEquals("piedra", piedra.getNombre());
        assertEquals(5, piedra.getCantidad());
    }

    @Test
    void queObtengaUnMapaConSiMismo() throws Exception {
        ObjetoBasico hierro = new ObjetoBasico("hierro", 10);

        Map<ObjetoComponente, Integer> resultado = hierro.obtener();

        assertEquals(1, resultado.size());
        assertEquals(10, resultado.get(hierro));
        assertTrue(resultado.containsKey(hierro));
    }

    @Test
    void queNoPermitaCantidadNegativa() {
        Exception ex = assertThrows(Exception.class, () -> new ObjetoBasico("error", -3));
        assertEquals(Constant.EXCEPCION_CANTIDAD_NEGATIVA, ex.getMessage());
    }

    @Test
    void queDosObjetosBasicosConMismoNombreSeanIguales() throws Exception {
        ObjetoBasico a = new ObjetoBasico("agua", 5);
        ObjetoBasico b = new ObjetoBasico("agua", 10);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
