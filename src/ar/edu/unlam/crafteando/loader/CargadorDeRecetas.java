package ar.edu.unlam.crafteando.loader;

import java.util.Map;
import ar.edu.unlam.crafteando.Jugador.Recetario;
import ar.edu.unlam.crafteando.lectura.*;
import ar.edu.unlam.crafteando.Clases.*;

public class CargadorDeRecetas {

    private final FabricadorDeObjetos fabrica = new FabricadorDeObjetos();

    public Recetario cargar(RecetaLectura lectura) throws Exception {
        Recetario recetario = new Recetario();

        for (RecetaLecturaItem recetaItem : lectura.getRecetas()) {
            Receta receta = new Receta(
                recetaItem.getNombre(),
                recetaItem.getTipoDeMesaCrafteo(),
                recetaItem.getTiempoConstruccion()
            );

            for (ObjetoLecturaItem ingrediente : recetaItem.getIngredientes()) {
                ObjetoComponente obj = fabrica.construir(ingrediente.getNombre(), ingrediente.getTipo());
                int cantidad = ingrediente.getCantidad();

                if (obj instanceof ObjetoCompuesto) {
                    ObjetoCompuesto compuesto = (ObjetoCompuesto) obj;
                    for (int i = 1; i < cantidad; i++) {
                        ObjetoComponente otro = fabrica.construir(ingrediente.getNombre(), ingrediente.getTipo());
                        compuesto.agregar(otro);
                    }
                    receta.agregarIngrediente(compuesto, cantidad);
                } else {
                    receta.agregarIngrediente(obj, cantidad);
                }
            }

            recetario.agregarReceta(receta);
        }

        return recetario;
    }
}
