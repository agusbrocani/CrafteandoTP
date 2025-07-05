package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.Clases.*;
import ar.edu.unlam.crafteando.Jugador.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class JugadorTest {

    private Jugador jugador;

    private ObjetoBasico madera;
    private ObjetoBasico carbon;
    private ObjetoCompuesto antorcha;
    private ObjetoCompuesto baston;
    
    @BeforeEach
    void setUp() throws Exception {

        Recetario recetario = new Recetario();

        // Ingredientes básicos
        carbon = new ObjetoBasico("Carbon");
        madera = new ObjetoBasico("Madera");

        // Objeto compuesto: Basto (para tener otras recetas posibles)
        baston = new ObjetoCompuesto("Baston");
        Receta recetaBaston = new Receta("Baston", "Básico", 2);
        recetaBaston.agregarIngrediente(madera, 2);
        recetario.agregarReceta(recetaBaston);

        // Objeto compuesto: Antorcha
        antorcha = new ObjetoCompuesto("Antorcha");
        Receta recetaAntorcha = new Receta("Antorcha", "Básico", 5);
        recetaAntorcha.agregarIngrediente(carbon, 1);
        recetaAntorcha.agregarIngrediente(madera, 1);
        recetario.agregarReceta(recetaAntorcha);

        // Objeto compuesto: Fogata (requiere 4 Madera y 1 Antorcha)
        Receta recetaFogata = new Receta("Fogata", "Básico", 10);
        recetaFogata.agregarIngrediente(madera, 4);
        recetaFogata.agregarIngrediente(antorcha, 1);
        recetario.agregarReceta(recetaFogata);

        // 2) Crear el jugador apuntando al stub de Prolog de tests
        jugador = new Jugador("Tester", recetario, Constant.PATH_PROLOG_INTEGRACION_TEST);
    }

    // --- consultarFaltantesPrimerNivel ---

    @Test
    void testConsultarFaltantesPrimerNivelFogata_conFaltantes() throws Exception {
        // Sin nada en inventario → faltan 4 de Madera y 1 de Antorcha
        List<Map<ObjetoComponente, Integer>> falt = jugador.consultarFaltantesPrimerNivel("Fogata");
        assertEquals(1, falt.size());
        Map<ObjetoComponente, Integer> mapa = falt.get(0);
        assertEquals(4, mapa.get(new ObjetoBasico("Madera")));
        assertEquals(1, mapa.get(new ObjetoCompuesto("Antorcha")));
    }

    
    @Test
    void testConsultarFaltantesPrimerNivelFogata_sinFaltantes() throws Exception {
        // Proveemos exactamente lo necesario
        jugador.recolectar(madera, 4);
        jugador.recolectar(antorcha, 1);

        List<Map<ObjetoComponente, Integer>> falt = jugador.consultarFaltantesPrimerNivel("Fogata");
        assertEquals(1, falt.size());
        assertTrue(falt.get(0).isEmpty(), "No debería faltar nada en primer nivel");
    }

    
    // --- consultarFaltantesBasicos ---

    @Test
    void testConsultarFaltantesBasicosFogata_conFaltantes() throws Exception {
        // Desde cero, faltan 5 de Madera y 1 de Carbon
        List<Map<ObjetoComponente, Integer>> faltB = jugador.consultarFaltantesBasicos("Fogata");
        assertEquals(1, faltB.size());
        Map<ObjetoComponente, Integer> mapa = faltB.get(0);
        assertEquals(5, mapa.get(new ObjetoBasico("Madera")));
        assertEquals(1, mapa.get(new ObjetoBasico("Carbon")));
    }

    @Test
    void testConsultarFaltantesBasicosFogata_sinFaltantes() throws Exception {
        // Proveemos todo lo necesario para fogata + antorcha
        jugador.recolectar(madera, 5);
        jugador.recolectar(carbon, 1);

        List<Map<ObjetoComponente, Integer>> faltB = jugador.consultarFaltantesBasicos("Fogata");
        assertEquals(1, faltB.size());
        assertTrue(faltB.get(0).isEmpty(), "No debería faltar nada desde cero");
    }

    // --- cuantoPuedoCraftear ---

    @Test
    void testCuantoPuedoCraftearFogata_sinRecursos() {
        // Sin inventario porque no recoelcto nada -> 0 fogatas posibles
        assertEquals(0, jugador.cuantoPuedoCraftear("Fogata"));
    }

    @Test
    void testCuantoPuedoCraftearFogata_conRecursos() {
        // Proveemos madera y carbon suficientes
        jugador.recolectar(madera, 10);
        jugador.recolectar(carbon, 3);
        // Con 10 madera y 3 carbon, lógica recursiva da 2 fogatas
        assertEquals(2, jugador.cuantoPuedoCraftear("Fogata"));
    }

    // --- craftear ---

    @Test
    void testCraftearAntorcha_sinRecursos() throws Exception {
        // Sin ingredientes → no se puede craftear
        assertFalse(jugador.craftear("Antorcha"));
    }

    
    @Test
    void testCraftearAntorcha_conRecursos() throws Exception {

        jugador.recolectar(carbon, 1);
        jugador.recolectar(madera, 1);

        assertTrue(jugador.craftear("Antorcha"));

        assertEquals(1, jugador.cuantoHayDe(antorcha));
        assertEquals(0, jugador.cuantoHayDe(carbon));
        assertEquals(0, jugador.cuantoHayDe(madera));
    }

     

    // --- consultarObjetosCrafteables ---

    @Test
    void testConsultarObjetosCrafteables() {
    	
        List<String> objs = jugador.consultarObjetosCrafteables();
        assertTrue(objs.contains("antorcha"));
        assertTrue(objs.contains("fogata"));
        assertEquals(2, objs.size());
    }
}
