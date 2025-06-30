package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoComponenteTest {

    // Dummy completamente vacío
    private static class DummyVacio extends ObjetoComponente {
        public DummyVacio(String nombre, Integer cantidad) {
            super(nombre, cantidad);
        }

        @Override
        public Map<ObjetoComponente, Integer> obtener() {
            return Collections.emptyMap();
        }

        @Override
        public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
            return Collections.emptyMap();
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

    // Hijo rastreable
    private static class HijoEspia extends ObjetoBasico {
        private final AtomicBoolean llamado = new AtomicBoolean(false);

        public HijoEspia(String nombre) {
            super(nombre);
        }

        @Override
        protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
            llamado.set(true);
        }

        public boolean fueLlamado() {
            return llamado.get();
        }
    }

    // Dummy con múltiples hijos espiables
    private static class DummyMultiHijo extends ObjetoComponente {
        private final HijoEspia hijo1 = new HijoEspia("A");
        private final HijoEspia hijo2 = new HijoEspia("B");

        public DummyMultiHijo(String nombre, Integer cantidad) {
            super(nombre, cantidad);
        }

        @Override
        public Map<ObjetoComponente, Integer> obtener() {
            return Map.of(hijo1, 1, hijo2, 2);
        }

        @Override
        public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
            return Collections.emptyMap();
        }

        @Override
        public boolean estaVacio() {
            return false;
        }

        @Override
        protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
            // No registra nada, porque se testean los hijos
        }

        public boolean ambosHijosLlamados() {
            return hijo1.fueLlamado() && hijo2.fueLlamado();
        }
    }

    @Test
    void deberiaObtenerNombreYCantidadCorrectos() {
        ObjetoComponente componente = new DummyVacio("Cobre", 3);
        assertEquals("Cobre", componente.getNombre());
        assertEquals(3, componente.getCantidad());
    }

    @Test
    void noDeberiaPermitirNombreVacioONulo() {
        assertThrows(IllegalArgumentException.class, () -> new DummyVacio("", 1));
        assertThrows(IllegalArgumentException.class, () -> new DummyVacio("   ", 1));
        assertThrows(IllegalArgumentException.class, () -> new DummyVacio(null, 1));
    }

    @Test
    void noDeberiaPermitirCantidadNegativaONula() {
        assertThrows(IllegalArgumentException.class, () -> new DummyVacio("Hierro", -1));
        assertThrows(IllegalArgumentException.class, () -> new DummyVacio("Hierro", null));
    }

    @Test
    void mostrarConstruccionNoDebeFallarSiEstaVacio() {
        ObjetoComponente vacio = new DummyVacio("Nada", 1);
        assertDoesNotThrow(() -> vacio.mostrarConstruccion(true));
    }

    @Test
    void mostrarConstruccionDebeLlamarInternoEnSusHijos() {
        HijoEspia hijo = new HijoEspia("Tiza");

        ObjetoComponente compuesto = new ObjetoComponente("Lapiz", 1) {
            @Override
            public Map<ObjetoComponente, Integer> obtener() {
                return Map.of(hijo, 2);
            }

            @Override
            public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
                return Collections.emptyMap();
            }

            @Override
            public boolean estaVacio() {
                return false;
            }

            @Override
            protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
                // no usado
            }
        };

        assertDoesNotThrow(() -> compuesto.mostrarConstruccion(false));
        assertTrue(hijo.fueLlamado(), "Se espera que se llame al hijo internamente");
    }

    @Test
    void mostrarConstruccionSoloPrimerNivelNoLlamaANietos() {
        HijoEspia nieto = new HijoEspia("Polvo de Hueso");

        ObjetoComponente hijo = new ObjetoComponente("Arcilla", 1) {
            @Override
            public Map<ObjetoComponente, Integer> obtener() {
                return Map.of(nieto, 1);
            }

            @Override
            public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
                return Collections.emptyMap();
            }

            @Override
            public boolean estaVacio() {
                return false;
            }

            @Override
            protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
                // se llamará igual
            }
        };

        ObjetoComponente compuesto = new ObjetoComponente("Jarra", 1) {
            @Override
            public Map<ObjetoComponente, Integer> obtener() {
                return Map.of(hijo, 2);
            }

            @Override
            public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
                return Collections.emptyMap();
            }

            @Override
            public boolean estaVacio() {
                return false;
            }

            @Override
            protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
                // no usado
            }
        };

        assertDoesNotThrow(() -> compuesto.mostrarConstruccion(true));
        assertFalse(nieto.fueLlamado(), "No debe ejecutarse mostrarConstruccionInterno en nietos si soloPrimerNivel=true");
    }

    @Test
    void mostrarConstruccionConMultiplesHijosLlamaCadaUno() {
        DummyMultiHijo objeto = new DummyMultiHijo("Combinado", 1);

        assertDoesNotThrow(() -> objeto.mostrarConstruccion(false));
        assertTrue(objeto.ambosHijosLlamados(), "Debe llamar internamente a todos los hijos");
    }
}
