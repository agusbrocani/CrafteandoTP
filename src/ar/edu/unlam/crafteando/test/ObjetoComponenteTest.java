package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObjetoComponenteTest {

    @Test
    void noDeberiaPermitirCantidadNegativa() {
        Exception e = assertThrows(Exception.class, () -> {
            new ObjetoBasico("Hierro", -1);
        });
        assertEquals("La cantidad es menor a cero.", e.getMessage());
    }
}