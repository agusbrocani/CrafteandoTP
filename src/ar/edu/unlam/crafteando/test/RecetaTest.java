package ar.edu.unlam.crafteando.test;

import org.junit.jupiter.api.Test;
import java.util.Map;
import ar.edu.unlam.crafteando.*;

import static org.junit.jupiter.api.Assertions.*;

class RecetaTest {

    @Test
    void deberiaCalcularIngredientesBasicos() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera", 2);
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango", 1);
        mango.agregar(madera);

        Receta receta = new Receta();
        Map<ObjetoBasico, Integer> resultado = receta.listarIngredientesDesdeCero(mango);

        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(madera));
    }

    @Test
    void deberiaCalcularTiempoTotal() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera", 2);
        ObjetoCompuesto mango = new ObjetoCompuesto("Mango", 1);
        mango.agregar(madera);

        Receta receta = new Receta();
        receta.setTipo("Mango");
        receta.setTiempoEnSegundos(15);
        receta.agregarIngrediente(madera, 2);

        Map<ObjetoCompuesto, Receta> recetas = Map.of(mango, receta);
        assertEquals(15, receta.calcularTiempoTotal(recetas));
    }
}
