/*package ar.edu.unlam.crafteando.test;

import org.junit.jupiter.api.Test;
import ar.edu.unlam.crafteando.*;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ObjetoBasicoTest {
    @Test
    void deberiaObtenerseASiMismo() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera", 5);
        Map<ObjetoComponente, Integer> obtenido = madera.obtener();

        assertEquals(1, obtenido.size());
        assertEquals(5, obtenido.get(madera));
    }

    @Test
    void deberiaDescomponerEnSiMismo() throws Exception {
        ObjetoBasico cuerda = new ObjetoBasico("Cuerda", 3);
        Map<ObjetoBasico, Integer> resultado = cuerda.descomponerEnBasicos();

        assertEquals(1, resultado.size());
        assertEquals(3, resultado.get(cuerda));
    }

    @Test
    void tiempoDeObjetoBasicoDeberiaSerCero() throws Exception {
        ObjetoBasico hierro = new ObjetoBasico("Hierro", 1);
        assertEquals(0, hierro.calcularTiempo(Map.of()));
    }
}*/