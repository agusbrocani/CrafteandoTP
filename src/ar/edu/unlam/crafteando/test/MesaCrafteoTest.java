package ar.edu.unlam.crafteando.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ar.edu.unlam.crafteando.MesaCrafteo;
import ar.edu.unlam.crafteando.Receta;
import ar.edu.unlam.crafteando.Recetario;

public class MesaCrafteoTest {

    @Test
    public void agregarUnaReceta() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa básica");
        Receta receta = new Receta("Receta1", "Espada", 10);
        mesa.agregarReceta(receta);

        List<Receta> recetas = mesa.obtenerRecetas();
        assertEquals(1, recetas.size());
        assertTrue(recetas.contains(receta));
    }

    @Test
    public void agregarVariasRecetas() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa básica");
        Receta r1 = new Receta("Receta1", "Espada", 10);
        Receta r2 = new Receta("Receta2", "Arco", 15);
        mesa.agregarVariasRecetas(Arrays.asList(r1, r2));

        List<Receta> recetas = mesa.obtenerRecetas();
        assertEquals(2, recetas.size());
        assertTrue(recetas.contains(r1));
        assertTrue(recetas.contains(r2));
    }

    @Test
    public void desbloquearRecetasEnRecetario() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa");
        Receta receta = new Receta("Receta1", "Espada", 10);
        mesa.agregarReceta(receta);

        Recetario recetario = new Recetario();
        mesa.desbloquearRecetasEn(recetario);

        assertTrue(recetario.getRecetas().contains(receta));
    }
    
    @Test
    public void agregarVariasRecetas_correctamente() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa básica");

        Receta r1 = new Receta("R1", "Espada", 5);
        Receta r2 = new Receta("R2", "Escudo", 8);

        List<Receta> nuevas = Arrays.asList(r1, r2);
        mesa.agregarVariasRecetas(nuevas);

        assertEquals(2, mesa.obtenerRecetas().size());
        assertTrue(mesa.obtenerRecetas().contains(r1));
        assertTrue(mesa.obtenerRecetas().contains(r2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarVariasRecetas_lanzaExcepcionSiListaEsNull() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa básica");
        mesa.agregarVariasRecetas(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarVariasRecetas_lanzaExcepcionSiContieneNull() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa básica");

        Receta r1 = new Receta("R1", "Espada", 5);
        List<Receta> nuevas = Arrays.asList(r1, null);

        mesa.agregarVariasRecetas(nuevas);
    }
}