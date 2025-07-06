package ar.edu.unlam.crafteando.test;

import ar.edu.unlam.crafteando.Clases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecetaTest {

    private ObjetoBasico madera;
    private ObjetoBasico carbon;
    private ObjetoBasico hierro;
    private ObjetoCompuesto baston;

    @BeforeEach
    void setUp() {
        madera = new ObjetoBasico("Madera");
        carbon = new ObjetoBasico("Carbón");
        hierro = new ObjetoBasico("Hierro");

        baston = new ObjetoCompuesto("Bastón");
        baston.agregar(madera);
        baston.agregar(madera);
    }

    @Test
    void deberiaCrearRecetaValida() {
        Receta receta = new Receta("Antorcha", "Básico", 5);
        receta.agregarIngrediente(madera, 1);
        receta.agregarIngrediente(carbon, 1);

        assertEquals("Antorcha", receta.getNombre());
        assertEquals("Básico", receta.getTipo());
        assertEquals(5, receta.getTiempoEnSegundos());
        assertEquals(2, receta.getIngredientes().size());
    }

    @Test
    void noDeberiaPermitirTipoVacioEnConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Receta("Antorcha", "", 5));
    }

    @Test
    void noDeberiaPermitirTiempoNegativoEnConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Receta("Antorcha", "Básico", -3));
    }

    @Test
    void deberiaAgregarIngredienteConCantidadValida() {
        Receta receta = new Receta("Pico de Madera", "Carpintería", 10);
        receta.agregarIngrediente(madera, 3);

        assertEquals(1, receta.getIngredientes().size());
        assertEquals(3, receta.getIngredientes().get(madera));
    }

    @Test
    void noDeberiaAgregarIngredienteNulo() {
        Receta receta = new Receta("Espada", "Herrería", 15);
        assertThrows(IllegalArgumentException.class, () -> receta.agregarIngrediente(null, 1));
    }

    @Test
    void noDeberiaAgregarIngredienteConCantidadInvalida() {
        Receta receta = new Receta("Espada", "Herrería", 15);
        assertThrows(IllegalArgumentException.class, () -> receta.agregarIngrediente(madera, 0));
    }

    @Test
    void deberiaListarIngredientesDesdeCero() {
        Receta receta = new Receta("Espada", "Herrería", 15);
        receta.agregarIngrediente(baston, 1);

        Map<ObjetoBasico, Integer> descompuestos = receta.listarIngredientesDesdeCero(baston);
        assertEquals(1, descompuestos.size());
        assertEquals(2, descompuestos.get(madera));
    }

    @Test
    void deberiaListarIngredientesDeObjetoCompuesto() {
        Receta receta = new Receta("Espada", "Herrería", 15);
        receta.agregarIngrediente(baston, 1);

        Map<ObjetoComponente, Integer> ingredientes = receta.listarIngredientes(baston);
        assertEquals(1, ingredientes.size());
        assertEquals(2, ingredientes.get(madera));
    }

    @Test
    void deberiaValidarRecetaCorrecta() {
        Receta receta = new Receta("Puerta", "Carpintería", 10);
        receta.agregarIngrediente(madera, 6);

        assertDoesNotThrow(receta::validar);
    }

    @Test
    void noDeberiaValidarRecetaSinIngredientes() {
        Receta receta = new Receta("Cama", "Carpintería", 20);
        assertThrows(IllegalStateException.class, receta::validar);
    }

    @Test
    void noDeberiaValidarSiTipoEsNull() {
        Receta receta = new Receta();
        receta.setTiempoEnSegundos(5);
        receta.agregarIngrediente(madera, 1);
        assertThrows(IllegalStateException.class, receta::validar);
    }

    @Test
    void noDeberiaValidarSiTiempoEsNull() {
        Receta receta = new Receta();
        receta.setTipo("Herrería");
        receta.agregarIngrediente(madera, 1);
        assertThrows(IllegalStateException.class, receta::validar);
    }

    @Test
    void noDeberiaPermitirSetearTiempoNegativo() {
        Receta receta = new Receta("Mesa", "Carpintería", 5);
        assertThrows(IllegalArgumentException.class, () -> receta.setTiempoEnSegundos(-1));
    }

    @Test
    void noDeberiaValidarRecetaConTipoVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            Receta receta = new Receta();
            receta.setTiempoEnSegundos(10);
            receta.agregarIngrediente(madera, 1);
            receta.setTipo(""); // lanza excepción
            receta.validar();   // no se ejecuta
        });
    }

    @Test
    void deberiaCalcularTiempoTotalIncluyendoRecetasAnidadas() {
        Receta recetaBaston = new Receta("Bastón", "Básico", 2);
        recetaBaston.agregarIngrediente(madera, 2);

        Receta recetaEspada = new Receta("Espada de Hierro", "Herrería", 15);
        recetaEspada.agregarIngrediente(hierro, 2);
        recetaEspada.agregarIngrediente(baston, 1);

        Map<String, Receta> recetas = new HashMap<>();
        recetas.put("Bastón", recetaBaston);
        recetas.put("Espada de Hierro", recetaEspada);

        assertEquals(17, recetaEspada.calcularTiempoTotal(recetas)); // 15 + 1*2
    }

    @Test
    void calcularTiempoTotalCuandoNoTieneTiempoNiRecetaAsociada() {
        Receta receta = new Receta();
        receta.agregarIngrediente(madera, 1);
        assertEquals(0, receta.calcularTiempoTotal(new HashMap<>()));
    }

    @Test
    void deberiaValidarComportamientoEquals() {
        Receta receta = new Receta("Flecha", "Arquería", 5);
        Receta igual = new Receta("Flecha", "Arquería", 5);
        ObjetoBasico hierro = new ObjetoBasico("Hierro");
        receta.agregarIngrediente(hierro, 1);
        igual.agregarIngrediente(hierro, 1);

        assertEquals(receta, receta); // a sí mismo
        assertEquals(igual, receta);  // igualdad estructural
        assertNotEquals(null, receta); // null
        assertNotEquals(receta, null); // null reverso
        assertNotEquals("otra clase", receta); // otra clase
    }

    @Test
    void deberiaDetectarRecetasDistintas() {
        Receta r1 = new Receta("Mesa", "Carpintería", 10);
        Receta r2 = new Receta("Mesa", "Herrería", 10);
        Receta r3 = new Receta("Mesa", "Carpintería", 5);
        Receta r4 = new Receta("Mesa", "Carpintería", 10);
        r1.agregarIngrediente(madera, 1);
        r2.agregarIngrediente(madera, 1);
        r3.agregarIngrediente(madera, 1);
        r4.agregarIngrediente(hierro, 1);

        assertNotEquals(r1, r2); // distinto tipo
        assertNotEquals(r1, r3); // distinto tiempo
        assertNotEquals(r1, r4); // distinto ingrediente
    }

    @Test
    void deberiaTenerHashCodeIgualSiSonIguales() {
        Receta r1 = new Receta("Fogata", "Básico", 10);
        Receta r2 = new Receta("Fogata", "Básico", 10);
        r1.agregarIngrediente(madera, 4);
        r2.agregarIngrediente(madera, 4);

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void deberiaPermitirSetTipoConValorValido() {
        Receta receta = new Receta("Mesa", "Inicial", 5);
        receta.setTipo("Avanzado");
        assertEquals("Avanzado", receta.getTipo());
    }

    @Test
    void deberiaPermitirSetTiempoEnSegundosConValorValido() {
        Receta receta = new Receta("Silla", "Carpintería", 5);
        receta.setTiempoEnSegundos(10);
        assertEquals(10, receta.getTiempoEnSegundos());
    }

    @Test
    void noDeberiaPermitirSetearTipoInvalido() {
        Receta receta = new Receta("Mesa", "Carpintería", 5);
        assertThrows(IllegalArgumentException.class, () -> receta.setTipo(null));
        assertThrows(IllegalArgumentException.class, () -> receta.setTipo("   "));
    }

    @Test
    void noDeberiaPermitirSetearTiempoInvalido() {
        Receta receta = new Receta("Mesa", "Carpintería", 5);
        assertThrows(IllegalArgumentException.class, () -> receta.setTiempoEnSegundos(null));
        assertThrows(IllegalArgumentException.class, () -> receta.setTiempoEnSegundos(-10));
    }
}
