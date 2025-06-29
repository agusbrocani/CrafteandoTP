package ar.edu.unlam.crafteando.test;
import ar.edu.unlam.crafteando.*;


import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoCompuestoTest {

	@Test
    void agregarUnObjetoBasico() throws Exception {
        ObjetoCompuesto pico = new ObjetoCompuesto("Pico", 1);
        ObjetoBasico hierro = new ObjetoBasico("Hierro", 3);
        pico.agregar(hierro);

        Map<ObjetoComponente, Integer> componentes = pico.obtener();
        assertEquals(1, componentes.size());
        assertEquals(3, componentes.get(hierro));
    }

    @Test
    void agregarDosVecesElMismoObjetoSumaCantidades() throws Exception {
        ObjetoCompuesto espada = new ObjetoCompuesto("Espada", 1);
        ObjetoBasico hierro = new ObjetoBasico("Hierro", 2);
        espada.agregar(hierro);
        espada.agregar(new ObjetoBasico("Hierro", 3)); // mismo nombre

        assertEquals(1, espada.obtener().size());
        assertEquals(5, espada.obtener().get(hierro));
    }

    @Test
    void removerDisminuyeCantidadYEliminaSiEsCero() throws Exception {
        ObjetoCompuesto hacha = new ObjetoCompuesto("Hacha", 1);
        ObjetoBasico madera = new ObjetoBasico("Madera", 4);
        hacha.agregar(madera);
        hacha.remover(new ObjetoBasico("Madera", 4));

        assertTrue(hacha.obtener().isEmpty());
    }

    @Test
    void agregarObjetoNuloLanzaExcepcion() throws Exception {
        ObjetoCompuesto armadura = new ObjetoCompuesto("Armadura", 1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            armadura.agregar(null);
        });
        assertEquals(Constant.EXCEPCION_AGREGAR_COMPONENTE_NULO, ex.getMessage());
    }

    @Test
    void removerObjetoNuloLanzaExcepcion() throws Exception {
        ObjetoCompuesto escudo = new ObjetoCompuesto("Escudo", 1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            escudo.remover(null);
        });
        assertEquals(Constant.EXCEPCION_ELIMINAR_COMPONENTE_NULO, ex.getMessage());
    }
    
    @Test
    void removerMasCantidadDeLaExistenteDebeLanzarExcepcion() throws Exception {
        ObjetoCompuesto compuesto = new ObjetoCompuesto("Espada", 1);
        ObjetoBasico hierroExistente = new ObjetoBasico("Hierro", 2);
        ObjetoBasico hierroRemover = new ObjetoBasico("Hierro", 3); // mayor cantidad

        compuesto.agregar(hierroExistente);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            compuesto.remover(hierroRemover);
        });

        assertEquals(Constant.EXCEPCION_CANTIDAD_INSUFICIENTE, ex.getMessage());
    }
    
    
    @Test
    void constructorVacioDeObjetoCompuestoDebeCrearObjetoValido() throws Exception {
        ObjetoCompuesto objeto = new ObjetoCompuesto();

        assertEquals("", objeto.getNombre());
        assertEquals(0, objeto.getCantidad());

        Map<ObjetoComponente, Integer> componentes = objeto.obtener();
        assertNotNull(componentes);
        assertTrue(componentes.isEmpty()); // mapa vacío
    }
    
    @Test
    void objetoCompuestoIgualAMismoContenidoYNombre() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera", 3);

        ObjetoCompuesto uno = new ObjetoCompuesto("Arco", 1);
        uno.agregar(madera);

        ObjetoCompuesto dos = new ObjetoCompuesto("Arco", 1);
        dos.agregar(new ObjetoBasico("Madera", 3)); // mismo nombre y cantidad

        assertEquals(uno, dos);
        assertEquals(uno.hashCode(), dos.hashCode());
    }

    @Test
    void objetoCompuestoConDiferenteNombreNoSonIguales() throws Exception {
        ObjetoBasico madera = new ObjetoBasico("Madera", 3);

        ObjetoCompuesto uno = new ObjetoCompuesto("Arco", 1);
        uno.agregar(madera);

        ObjetoCompuesto dos = new ObjetoCompuesto("Espada", 1);
        dos.agregar(new ObjetoBasico("Madera", 3));

        assertNotEquals(uno, dos);
    }

    @Test
    void objetoCompuestoConDiferenteComponentesNoSonIguales() throws Exception {
        ObjetoCompuesto uno = new ObjetoCompuesto("Arco", 1);
        uno.agregar(new ObjetoBasico("Madera", 3));

        ObjetoCompuesto dos = new ObjetoCompuesto("Arco", 1);
        dos.agregar(new ObjetoBasico("Hierro", 3)); // distinta clave

        assertNotEquals(uno, dos);
    }

    @Test
    void objetoCompuestoEqualsMismoObjeto() throws Exception {
        ObjetoCompuesto uno = new ObjetoCompuesto("Escudo", 1);
        assertEquals(uno, uno);
    }

    @Test
    void objetoCompuestoEqualsConNull() throws Exception {
        ObjetoCompuesto uno = new ObjetoCompuesto("Escudo", 1);
        assertNotEquals(uno, null);
    }
}
