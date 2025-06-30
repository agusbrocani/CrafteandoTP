package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ar.edu.unlam.crafteando.MesaHerreria;
import ar.edu.unlam.crafteando.Receta;

public class MesaHerreriaTest {
	@Test
	public void crearMesaHerreria_conRecetas() {
		Receta receta1 = new Receta("R1", "Espada", 10);
		Receta receta2 = new Receta("R2", "Yunque", 15);

		List<Receta> recetas = Arrays.asList(receta1, receta2);
		MesaHerreria mesa = new MesaHerreria(recetas);

		List<Receta> recetasObtenidas = mesa.obtenerRecetas();

		assertEquals(2, recetasObtenidas.size());
		assertTrue(recetasObtenidas.contains(receta1));
		assertTrue(recetasObtenidas.contains(receta2));
	}

}
