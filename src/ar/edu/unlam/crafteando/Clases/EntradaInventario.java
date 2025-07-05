package ar.edu.unlam.crafteando.Clases;

public class EntradaInventario {
    private String nombre;
    private int cantidad;

    public EntradaInventario(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}