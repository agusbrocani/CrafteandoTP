package ar.edu.unlam.crafteando.test;

import org.junit.jupiter.api.Test;

import ar.edu.unlam.crafteando.ObjetoBasico;
import ar.edu.unlam.crafteando.ObjetoComponente;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoBasicoTest {

    @Test
    void constructorDeberiaGuardarNombre() {
        ObjetoBasico carbon = new ObjetoBasico("Carbón");
        assertEquals("Carbón", carbon.getNombre());
    }

    @Test
    void deberiaObtenerseASiMismo() {
        ObjetoBasico piedra = new ObjetoBasico("Piedra");
        Map<ObjetoComponente, Integer> obtenido = piedra.obtener();

        assertEquals(1, obtenido.size());
        assertTrue(obtenido.containsKey(piedra));
        assertEquals(1, obtenido.get(piedra));
    }

    @Test
    void deberiaDescomponerseEnSiMismo() {
        ObjetoBasico sal = new ObjetoBasico("Sal");
        Map<ObjetoBasico, Integer> resultado = sal.descomponerEnBasicos();

        assertEquals(1, resultado.size());
        assertTrue(resultado.containsKey(sal));
        assertEquals(1, resultado.get(sal));
    }

    @Test
    void objetosConMismoNombreSonIguales() {
        ObjetoBasico a = new ObjetoBasico("Agua");
        ObjetoBasico b = new ObjetoBasico("Agua");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void objetosConDistintoNombreNoSonIguales() {
        ObjetoBasico a = new ObjetoBasico("Tierra");
        ObjetoBasico b = new ObjetoBasico("Fuego");

        assertNotEquals(a, b);
    }

    @Test
    void noDeberiaSerIgualANull() {
        ObjetoBasico a = new ObjetoBasico("Madera");
        assertNotEquals(null, a);
    }

    @Test
    void noDeberiaSerIgualAOtroTipoDeObjeto() {
        ObjetoBasico a = new ObjetoBasico("Madera");
        assertNotEquals("una cadena", a);
    }

    @Test
    void toStringDeberiaMostrarNombreYCantidad() {
        ObjetoBasico sal = new ObjetoBasico("Sal");
        String esperado = "Nombre: Sal, Cantidad: 1";
        assertEquals(esperado, sal.toString());
    }

    @Test
    void deberiaEjecutarMostrarConstruccionParaCubrirInterno() {
        ObjetoBasico hierro = new ObjetoBasico("Hierro");
        hierro.mostrarConstruccion(false); // llama internamente al metodo protected
    }

    @Test
    void deberiaReportarQueNoEstaVacio() {
        ObjetoBasico agua = new ObjetoBasico("Agua");
        assertFalse(agua.estaVacio());
    }
}