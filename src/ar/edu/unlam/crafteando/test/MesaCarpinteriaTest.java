package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ar.edu.unlam.crafteando.MesaCarpinteria;
import ar.edu.unlam.crafteando.Receta;

public class MesaCarpinteriaTest {
	@Test
    public void crearMesaCarpinteria_conRecetas() {
        Receta receta1 = new Receta("R1", "Madera", 4);
        Receta receta2 = new Receta("R2", "Puerta", 6);

        List<Receta> recetas = Arrays.asList(receta1, receta2);
        MesaCarpinteria mesa = new MesaCarpinteria(recetas);

        List<Receta> recetasObtenidas = mesa.obtenerRecetas();

        assertEquals(2, recetasObtenidas.size());
        assertTrue(recetasObtenidas.contains(receta1));
        assertTrue(recetasObtenidas.contains(receta2));
    }
}
