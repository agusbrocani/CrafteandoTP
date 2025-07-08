package ar.edu.unlam.crafteando.test;

import org.junit.jupiter.api.Test;

import ar.edu.unlam.crafteando.lectura.ObjetoLecturaItem;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoLecturaItemTest {

    @Test
    void testGetters() {
        ObjetoLecturaItem item = new ObjetoLecturaItem();
        assertNull(item.getTipo());
        assertNull(item.getNombre());
        assertNull(item.getCantidad());
    }

    @Test
    void testToStringFormat() {
        ObjetoLecturaItem item = new ObjetoLecturaItem();
        assertNotNull(item.toString());
    }
}