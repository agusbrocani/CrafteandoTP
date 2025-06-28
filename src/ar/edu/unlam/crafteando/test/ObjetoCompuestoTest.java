package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoCompuestoTest {

    @Test
    void queAgregueObjetoBasicoCorrectamente() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("madera", 3);
        ObjetoCompuesto antorcha = new ObjetoCompuesto("antorcha", 1);

        antorcha.agregar(madera);

        Map<ObjetoComponente, Integer> resultado = antorcha.obtener();
        assertEquals(1, resultado.size());
        assertEquals(3, resultado.get(madera));
    }

    @Test
    void queSumeCantidadSiSeAgregaElMismoObjeto() throws Exception {
        ObjetoBasico carbon = new ObjetoBasico("carbon", 2);
        ObjetoCompuesto fogata = new ObjetoCompuesto("fogata", 1);

        fogata.agregar(carbon);
        fogata.agregar(new ObjetoBasico("carbon", 1)); // mismo nombre

        Map<ObjetoComponente, Integer> resultado = fogata.obtener();
        assertEquals(3, resultado.get(carbon));
    }

    @Test
    void queRemuevaComponenteAlLlegarACero() throws Exception {
        ObjetoBasico palo = new ObjetoBasico("palo", 2);
        ObjetoCompuesto arma = new ObjetoCompuesto("arma", 1);

        arma.agregar(palo);
        arma.remover(new ObjetoBasico("palo", 2));

        Map<ObjetoComponente, Integer> resultado = arma.obtener();
        assertFalse(resultado.containsKey(palo));
    }

    @Test
    void queDisminuyaLaCantidadSinEliminarlo() throws Exception {
        ObjetoBasico cuerda = new ObjetoBasico("cuerda", 5);
        ObjetoCompuesto trampa = new ObjetoCompuesto("trampa", 1);

        trampa.agregar(cuerda);
        trampa.remover(new ObjetoBasico("cuerda", 2));

        assertEquals(3, trampa.obtener().get(cuerda));
    }

    @Test
    void queNoPermitaAgregarNull() throws Exception {
        ObjetoCompuesto receta = new ObjetoCompuesto("receta", 1);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> receta.agregar(null));
        assertEquals(Constant.EXCEPCION_AGREGAR_COMPONENTE_NULO, ex.getMessage());
    }

    @Test
    void queNoPermitaRemoverNull() throws Exception {
        ObjetoCompuesto receta = new ObjetoCompuesto("receta", 1);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> receta.remover(null));
        assertEquals(Constant.EXCEPCION_ELIMINAR_COMPONENTE_NULO, ex.getMessage());
    }
}
