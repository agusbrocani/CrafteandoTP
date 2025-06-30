package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.*;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoCompuestoTest {

    @Test
    void deberiaAgregarYObtenerComponentes() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");

        mango.agregar(madera);

        Map<ObjetoComponente, Integer> obtenidos = mango.obtener();
        assertEquals(1, obtenidos.size());
        assertEquals(2, obtenidos.get(madera));
    }

    @Test
    void deberiaDescomponerEnBasicos() throws Exception {
        ObjetoBasico cuerda = new ObjetoBasico("Cuerda");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");
        mango.agregar(cuerda);

        Map<ObjetoBasico, Integer> basicos = mango.descomponerEnBasicos();
        assertEquals(1, basicos.size());
        assertEquals(4, basicos.get(cuerda));
    }

    @Test
    void deberiaCalcularTiempoDeReceta() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera");
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango");
        mango.agregar(madera);

        Receta recetaMango = new Receta();
        recetaMango.setTipo("Mango");
        recetaMango.setTiempoEnSegundos(10);
        recetaMango.agregarIngrediente(madera, 2);

        Map<ObjetoCompuesto, Receta> recetas = Map.of(mango, recetaMango);
        assertEquals(10, mango.calcularTiempo(recetas));
    }
}
