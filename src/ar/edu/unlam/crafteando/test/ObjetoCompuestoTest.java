package ar.edu.unlam.crafteando.test;

import org.junit.jupiter.api.Test;

import ar.edu.unlam.crafteando.Constant;
import ar.edu.unlam.crafteando.ObjetoBasico;
import ar.edu.unlam.crafteando.ObjetoComponente;
import ar.edu.unlam.crafteando.ObjetoCompuesto;

import java.lang.reflect.Constructor;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoCompuestoTest {

    @Test
    void deberiaAgregarYObtenerComponentes() {
        ObjetoBasico madera = new ObjetoBasico("Madera");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");

        mango.agregar(madera);
        mango.agregar(madera);

        Map<ObjetoComponente, Integer> obtenidos = mango.obtener();
        assertEquals(1, obtenidos.size());
        assertEquals(2, obtenidos.get(madera));
    }

    @Test
    void deberiaDescomponerEnBasicos() {
        ObjetoBasico cuerda = new ObjetoBasico("Cuerda");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");

        mango.agregar(cuerda);
        mango.agregar(cuerda);

        Map<ObjetoBasico, Integer> basicos = mango.descomponerEnBasicos();
        assertEquals(1, basicos.size());
        assertEquals(2, basicos.get(cuerda));
    }

    @Test
    void deberiaRemoverComponente() {
        ObjetoBasico metal = new ObjetoBasico("Metal");
        ObjetoCompuesto hoja = new ObjetoCompuesto("Hoja");

        hoja.agregar(metal);
        hoja.agregar(metal);
        hoja.remover(metal);

        assertEquals(1, hoja.obtener().get(metal));
    }

    @Test
    void deberiaEliminarComponenteSiSeRemueveTodo() {
        ObjetoBasico piedra = new ObjetoBasico("Piedra");
        ObjetoCompuesto cabeza = new ObjetoCompuesto("Cabeza");

        cabeza.agregar(piedra);
        cabeza.remover(piedra);

        assertFalse(cabeza.obtener().containsKey(piedra));
        assertTrue(cabeza.estaVacio());
    }

    @Test
    void deberiaLanzarExcepcionAlAgregarNulo() {
        ObjetoCompuesto objeto = new ObjetoCompuesto("Algun objeto");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            objeto.agregar(null);
        });
        assertEquals(Constant.EXCEPCION_AGREGAR_COMPONENTE_NULO, ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlRemoverNulo() {
        ObjetoCompuesto objeto = new ObjetoCompuesto("Otro objeto");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            objeto.remover(null);
        });
        assertEquals(Constant.EXCEPCION_ELIMINAR_COMPONENTE_NULO, ex.getMessage());
    }

    @Test
    void mostrarConstruccionNoDebeFallar() {
        ObjetoBasico hilo = new ObjetoBasico("Hilo");
        ObjetoCompuesto cuerda = new ObjetoCompuesto("Cuerda");

        cuerda.agregar(hilo);
        assertDoesNotThrow(() -> cuerda.mostrarConstruccion(false));
    }

    @Test
    void deberiaMantenerEqualsYHashCodePorNombre() {
        ObjetoCompuesto a = new ObjetoCompuesto("Espada");
        ObjetoCompuesto b = new ObjetoCompuesto("Espada");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringDebeMostrarNombreYCantidad() {
        ObjetoCompuesto arco = new ObjetoCompuesto("Arco");
        String esperado = "Nombre: Arco\nCantidad: 1";

        assertEquals(esperado, arco.toString());
    }

    @Test
    void deberiaInvocarConstructorPrivadoPorReflexion() throws Exception {
        Constructor<ObjetoCompuesto> ctor = ObjetoCompuesto.class.getDeclaredConstructor(String.class, Integer.class);
        ctor.setAccessible(true);
        ObjetoCompuesto raro = ctor.newInstance("Inaccesible", 5);
        assertEquals("Inaccesible", raro.getNombre());
        assertEquals(5, raro.getCantidad());
    }
}
