package ar.edu.unlam.crafteando.test;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unlam.crafteando.Clases.*;
import ar.edu.unlam.crafteando.Jugador.Recetario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MesaCrafteoTest {

    private Recetario recetario;
    private Receta recetaEspada;

    @BeforeEach
    void setUp() {
        recetario = new Recetario();

        // Ingredientes básicos
        ObjetoBasico hierro = new ObjetoBasico("Hierro");
        ObjetoBasico baston = new ObjetoBasico("Bastón");

        // Receta que será desbloqueada por la mesa
        recetaEspada = new Receta("Espada de Hierro", "Herrería", 15);
        recetaEspada.agregarIngrediente(hierro, 2);
        recetaEspada.agregarIngrediente(baston, 1);
    }

    @Test
    void unaMesaDebeDesbloquearSusRecetasEnElRecetario() {
        // Creo la mesa y le agrego una receta
        MesaCrafteo mesa = new MesaCrafteo("Mesa de Herrería");
        mesa.agregarReceta(recetaEspada);

        // Ejecutamos la llamada polimórfica
        mesa.desbloquearSiEsMesa(recetario);

        // Verificamos que la receta ahora esté en el recetario
        List<Receta> recetasEncontradas = recetario.buscarRecetasPorNombre("Espada de Hierro");
        assertEquals(1, recetasEncontradas.size());
        assertEquals("Espada de Hierro", recetasEncontradas.get(0).getNombre());
    }
    
    @Test
    void unaMesaDebePoderAgregarMultiplesRecetas() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa de Carpintería");

        Receta receta1 = new Receta("Puerta", "Carpintería", 10);
        receta1.agregarIngrediente(new ObjetoBasico("Madera"), 6);

        Receta receta2 = new Receta("Cama", "Carpintería", 15);
        receta2.agregarIngrediente(new ObjetoBasico("Madera"), 3);
        receta2.agregarIngrediente(new ObjetoBasico("Lana"), 3);

        mesa.agregarVariasRecetas(List.of(receta1, receta2));

        List<Receta> recetas = mesa.obtenerRecetas();
        assertEquals(2, recetas.size());
        assertTrue(recetas.contains(receta1));
        assertTrue(recetas.contains(receta2));
    }
    
    @Test
    void noDebePoderAgregarUnaRecetaNula() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa de Carpintería");
        assertThrows(IllegalArgumentException.class, () -> mesa.agregarReceta(null));
    }
    
    @Test
    void noDebePoderAgregarUnaListaDeRecetasNula() {
        MesaCrafteo mesa = new MesaCrafteo("Mesa de Carpintería");
        assertThrows(IllegalArgumentException.class, () -> mesa.agregarVariasRecetas(null));
    }
    
    @Test
    void dosMesasConElMismoNombreSonIguales() {
        MesaCrafteo mesa1 = new MesaCrafteo("Mesa de Herrería");
        MesaCrafteo mesa2 = new MesaCrafteo("Mesa de Herrería");

        assertEquals(mesa1, mesa2);
    }
    
    @Test
    void hashCodeDebeSerIgualSiNombreEsIgual() {
        MesaCrafteo mesa1 = new MesaCrafteo("Mesa de Carpintería");
        MesaCrafteo mesa2 = new MesaCrafteo("Mesa de Carpintería");

        assertEquals(mesa1.hashCode(), mesa2.hashCode());
    }
}
