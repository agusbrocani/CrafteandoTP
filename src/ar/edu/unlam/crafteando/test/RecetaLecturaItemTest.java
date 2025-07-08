package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.lectura.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecetaLecturaItemTest {

    @Test
    void testGetters() {
        RecetaLecturaItem item = new RecetaLecturaItem();

        assertNull(item.getNombre());
        assertNull(item.getTipoDeMesaCrafteo());
        assertNull(item.getTiempoConstruccion());
        assertNull(item.getIngredientes());
    }

    @Test
    void testToString() {
        RecetaLecturaItem item = new RecetaLecturaItem();
        assertNotNull(item.toString());
    }
}
