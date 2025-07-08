package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.lectura.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RecetaLecturaTest {

    @Test
    void testGetterSetter() {
        RecetaLecturaItem item = new RecetaLecturaItem();
        RecetaLectura lectura = new RecetaLectura();
        lectura.setRecetas(List.of(item));

        assertEquals(1, lectura.getRecetas().size());
        assertSame(item, lectura.getRecetas().get(0));
    }
}
