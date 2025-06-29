package ar.edu.unlam.crafteando;

import java.util.Map;

public abstract class ObjetoComponente {
    private final String nombre;
    private final Integer cantidad;

    public ObjetoComponente(String nombre, Integer cantidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(Constant.EXCEPCION_NOMBRE_INVALIDO);
        }
        if (cantidad == null || cantidad < 0) {
            throw new IllegalArgumentException(Constant.EXCEPCION_CANTIDAD_NEGATIVA);
        }
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public abstract Map<ObjetoComponente, Integer> obtener();
    public abstract int calcularTiempo(Map<ObjetoCompuesto, Receta> recetas);
    public abstract Map<ObjetoBasico, Integer> descomponerEnBasicos();
}
