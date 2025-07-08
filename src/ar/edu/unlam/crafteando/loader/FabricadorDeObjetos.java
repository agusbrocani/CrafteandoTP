package ar.edu.unlam.crafteando.loader;

import ar.edu.unlam.crafteando.Clases.ObjetoBasico;
import ar.edu.unlam.crafteando.Clases.ObjetoCompuesto;
import ar.edu.unlam.crafteando.Clases.ObjetoComponente;

public class FabricadorDeObjetos {

    public ObjetoComponente construir(String nombre, String tipo) throws Exception {
        if ("Basico".equalsIgnoreCase(tipo)) {
            return new ObjetoBasico(nombre);
        } else if ("Compuesto".equalsIgnoreCase(tipo)) {
            return new ObjetoCompuesto(nombre);
        } else {
            throw new IllegalArgumentException("Tipo de objeto desconocido: " + tipo);
        }
    }
}
