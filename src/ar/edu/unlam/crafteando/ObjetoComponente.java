package ar.edu.unlam.crafteando;

import java.util.Map;

public abstract class ObjetoComponente {
    private final String nombre;
    private final Integer cantidad;

    protected ObjetoComponente(String nombre, Integer cantidad) {
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
    
    public abstract boolean estaVacio();
    protected abstract void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo);
    public final void mostrarConstruccion(boolean soloPrimerNivel) {
        System.out.println("Objeto: " + this.getNombre());

        if (estaVacio()) {
            System.out.println("No contiene ingredientes.");
            return;
        }

        int total = this.obtener().size();
        int i = 0;
        for (Map.Entry<ObjetoComponente, Integer> entry : this.obtener().entrySet()) {
            boolean ultimo = (++i == total);
            entry.getKey().mostrarConstruccionInterno(entry.getValue(), 1, soloPrimerNivel, "", ultimo);
        }
    }
}