package ar.edu.unlam.crafteando.lectura;

import java.util.List;

public class RecetaLecturaItem {
    private String nombre;
    private String tipoDeMesaCrafteo;
    private Integer tiempoConstruccion;
    private List<ObjetoLecturaItem> ingredientes;

    public String getNombre() {
        return nombre;
    }

    public String getTipoDeMesaCrafteo() {
        return tipoDeMesaCrafteo;
    }

    public Integer getTiempoConstruccion() {
        return tiempoConstruccion;
    }

    public List<ObjetoLecturaItem> getIngredientes() {
        return ingredientes;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipoDeMesaCrafteo + ") - " + tiempoConstruccion + "s";
    }
}
