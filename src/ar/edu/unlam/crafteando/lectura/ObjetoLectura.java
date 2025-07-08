package ar.edu.unlam.crafteando.lectura;

import java.util.List;

public class ObjetoLectura {
    private List<ObjetoLecturaItem> objetos;

    public List<ObjetoLecturaItem> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<ObjetoLecturaItem> objetos) {
        this.objetos = objetos;
    }

    @Override
    public String toString() {
        return objetos.toString();
    }
}
