package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoComponenteTest {

    @Test
    void queGetNombreYCantidadFuncionen() throws Exception {
        ObjetoComponente mock = new ObjetoComponente("mock", 7) {
            @Override
            public Map<ObjetoComponente, Integer> obtener() {
                return null;
            }
        };

        assertEquals("mock", mock.getNombre());
        assertEquals(7, mock.getCantidad());
    }
}
