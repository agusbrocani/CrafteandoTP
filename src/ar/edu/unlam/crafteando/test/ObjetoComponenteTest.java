package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ObjetoComponenteTest {

    // Clase dummy para testear directamente el constructor de la clase abstracta
    private static class DummyComponente extends ObjetoComponente {
        public DummyComponente(String nombre, Integer cantidad) {
            super(nombre, cantidad);
        }

        @Override
        public Map<ObjetoComponente, Integer> obtener() {
            return Map.of();
        }

        @Override
        public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
            return Map.of();
        }

        @Override
        public boolean estaVacio() {
            return true;
        }

        @Override
        protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
            // No hace nada
        }
    }

    @Test
    void deberiaObtenerNombreYCantidadCorrectos() {
        ObjetoComponente componente = new DummyComponente("Cobre", 3);
        assertEquals("Cobre", componente.getNombre());
        assertEquals(3, componente.getCantidad());
    }

    @Test
    void noDeberiaPermitirNombreVacioONulo() {
        assertThrows(IllegalArgumentException.class, () -> new DummyComponente("", 1));
        assertThrows(IllegalArgumentException.class, () -> new DummyComponente("   ", 1));
        assertThrows(IllegalArgumentException.class, () -> new DummyComponente(null, 1));
    }

    @Test
    void noDeberiaPermitirCantidadNegativaONula() {
        assertThrows(IllegalArgumentException.class, () -> new DummyComponente("Hierro", -1));
        assertThrows(IllegalArgumentException.class, () -> new DummyComponente("Hierro", null));
    }

    @Test
    void mostrarConstruccionNoDebeFallar() {
        ObjetoBasico piedra = new ObjetoBasico("Piedra");
        assertDoesNotThrow(() -> piedra.mostrarConstruccion(false));
    }
}
