package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ObjetoCompuestoTest {

    @Test
    void deberiaAgregarYObtenerComponentes() {
        ObjetoBasico madera = new ObjetoBasico("Madera");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");

        mango.agregar(madera);
        mango.agregar(madera); // se acumula

        Map<ObjetoComponente, Integer> obtenidos = mango.obtener();
        assertEquals(1, obtenidos.size(), "Debe haber un solo tipo de componente");
        assertEquals(2, obtenidos.get(madera), "Debe tener cantidad acumulada de madera");
    }

    @Test
    void deberiaDescomponerEnBasicos() {
        ObjetoBasico cuerda = new ObjetoBasico("Cuerda");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");

        mango.agregar(cuerda);
        mango.agregar(cuerda); // total 2

        Map<ObjetoBasico, Integer> basicos = mango.descomponerEnBasicos();
        assertEquals(1, basicos.size(), "Debe haber una sola entrada básica");
        assertEquals(2, basicos.get(cuerda), "Debe reflejar la cantidad total del componente");
    }

    @Test
    void deberiaRemoverComponente() {
        ObjetoBasico metal = new ObjetoBasico("Metal");
        ObjetoCompuesto hoja = new ObjetoCompuesto("Hoja");

        hoja.agregar(metal); // +1
        hoja.agregar(metal); // +1 → total 2

        hoja.remover(metal); // -1 → queda 1

        Map<ObjetoComponente, Integer> resultado = hoja.obtener();
        assertEquals(1, resultado.get(metal));
    }

    @Test
    void deberiaEliminarComponenteSiSeRemueveTodo() {
        ObjetoBasico piedra = new ObjetoBasico("Piedra");
        ObjetoCompuesto cabeza = new ObjetoCompuesto("Cabeza");

        cabeza.agregar(piedra); // +1
        cabeza.remover(piedra); // -1 → desaparece

        Map<ObjetoComponente, Integer> resultado = cabeza.obtener();
        assertFalse(resultado.containsKey(piedra), "Debe eliminarse si su cantidad llega a cero");
        assertTrue(cabeza.estaVacio(), "Debe estar vacío si no tiene componentes");
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
        ObjetoCompuesto arma = new ObjetoCompuesto("Arco");

        String esperado = "Nombre: Arco\nCantidad: 1";
        assertEquals(esperado, arma.toString());
    }
}
