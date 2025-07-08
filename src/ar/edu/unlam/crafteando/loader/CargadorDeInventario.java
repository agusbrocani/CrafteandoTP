package ar.edu.unlam.crafteando.loader;

import ar.edu.unlam.crafteando.lectura.*;
import ar.edu.unlam.crafteando.Jugador.Jugador;
import ar.edu.unlam.crafteando.Clases.*;

public class CargadorDeInventario {

    private final FabricadorDeObjetos fabrica = new FabricadorDeObjetos();

    public void cargar(Jugador jugador, ObjetoLectura inventario) throws Exception {
        for (ObjetoLecturaItem objItem : inventario.getObjetos()) {
            ObjetoComponente objeto = fabrica.construir(objItem.getNombre(), objItem.getTipo());
            int cantidad = objItem.getCantidad();

            if (objeto instanceof ObjetoCompuesto) {
                ObjetoCompuesto compuesto = (ObjetoCompuesto) objeto;
                for (int i = 1; i < cantidad; i++) {
                    ObjetoComponente otro = fabrica.construir(objItem.getNombre(), objItem.getTipo());
                    compuesto.agregar(otro);
                }
                jugador.recolectar(compuesto, 1);
            } else {
                jugador.recolectar(objeto, cantidad);
            }
        }
    }
}
