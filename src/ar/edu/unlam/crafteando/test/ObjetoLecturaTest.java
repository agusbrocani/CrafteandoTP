package ar.edu.unlam.crafteando.test;
import org.junit.jupiter.api.Test;

import ar.edu.unlam.crafteando.lectura.ObjetoLectura;
import ar.edu.unlam.crafteando.lectura.ObjetoLecturaItem;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoLecturaTest {

    @Test
    void testGetterYToString() throws Exception {
        ObjetoLecturaItem item1 = crearObjetoLecturaItem("Basico", "Madera", 10);
        ObjetoLecturaItem item2 = crearObjetoLecturaItem("Compuesto", "Antorcha", 2);

        ObjetoLectura lectura = new ObjetoLectura();
        lectura.setObjetos(List.of(item1, item2));

        List<ObjetoLecturaItem> objetos = lectura.getObjetos();
        assertNotNull(objetos);
        assertEquals(2, objetos.size());
        assertEquals("Madera", objetos.get(0).getNombre());
        assertEquals("Antorcha", objetos.get(1).getNombre());

        String texto = lectura.toString();
        assertTrue(texto.contains("Basico"));
        assertTrue(texto.contains("Madera"));
        assertTrue(texto.contains("Compuesto"));
    }

    private ObjetoLecturaItem crearObjetoLecturaItem(String tipo, String nombre, int cantidad) throws Exception {
        ObjetoLecturaItem item = new ObjetoLecturaItem();

        Field tipoField = ObjetoLecturaItem.class.getDeclaredField("tipo");
        Field nombreField = ObjetoLecturaItem.class.getDeclaredField("nombre");
        Field cantidadField = ObjetoLecturaItem.class.getDeclaredField("cantidad");

        tipoField.setAccessible(true);
        nombreField.setAccessible(true);
        cantidadField.setAccessible(true);

        tipoField.set(item, tipo);
        nombreField.set(item, nombre);
        cantidadField.set(item, cantidad);

        return item;
    }
}
