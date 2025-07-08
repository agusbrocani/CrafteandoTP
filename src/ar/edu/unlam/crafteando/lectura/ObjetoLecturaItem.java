package ar.edu.unlam.crafteando.lectura;

public class ObjetoLecturaItem {
    private String tipo;
    private String nombre;
    private Integer cantidad;

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return tipo + " - " + nombre + " - " + cantidad;
    }
}
