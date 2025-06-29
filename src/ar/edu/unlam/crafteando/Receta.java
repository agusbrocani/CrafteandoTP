package ar.edu.unlam.crafteando;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Receta {
    private final Map<ObjetoComponente, Integer> ingredientes;
    private String tipo;
    private Integer tiempoEnSegundos;

    public Receta() {
        ingredientes = new HashMap<>();
    }

    public Receta(String tipo, Integer tiempoEnSegundos) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(Constant.EXCEPCION_TIPO_VACIO);
        }
        if (tiempoEnSegundos == null || tiempoEnSegundos < 0) {
            throw new IllegalArgumentException(Constant.EXCEPCION_TIEMPO_INVALIDO);
        }
        this.tipo = tipo;
        this.tiempoEnSegundos = tiempoEnSegundos;
        this.ingredientes = new HashMap<>();
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getTiempoEnSegundos() {
        return tiempoEnSegundos;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(Constant.EXCEPCION_TIPO_VACIO);
        }
        this.tipo = tipo;
    }

    public void setTiempoEnSegundos(Integer tiempoEnSegundos) {
        if (tiempoEnSegundos == null || tiempoEnSegundos < 0) {
            throw new IllegalArgumentException(Constant.EXCEPCION_TIEMPO_INVALIDO);
        }
        this.tiempoEnSegundos = tiempoEnSegundos;
    }

    public Map<ObjetoComponente, Integer> getIngredientes() {
        return Collections.unmodifiableMap(ingredientes);
    }

    public void agregarIngrediente(ObjetoComponente componente, int cantidad) {
        if (componente == null) {
            throw new IllegalArgumentException(Constant.EXCEPCION_COMPONENTE_NULO);
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException(Constant.EXCEPCION_CANTIDAD_INVALIDA);
        }
        ingredientes.put(componente, cantidad);
    }

    public Map<ObjetoBasico, Integer> listarIngredientesDesdeCero(ObjetoCompuesto o) {
        Map<ObjetoBasico, Integer> resultado = new HashMap<>();

        for (Map.Entry<ObjetoComponente, Integer> entry : o.obtener().entrySet()) {
            Map<ObjetoBasico, Integer> basicos = entry.getKey().descomponerEnBasicos();
            int cantidad = entry.getValue();

            for (Map.Entry<ObjetoBasico, Integer> basico : basicos.entrySet()) {
                resultado.merge(basico.getKey(), basico.getValue() * cantidad, Integer::sum);
            }
        }

        return resultado;
    }

    public Map<ObjetoComponente, Integer> listarIngredientes(ObjetoCompuesto o) {
        return o.obtener();
    }

    public int calcularTiempoTotal(Map<ObjetoCompuesto, Receta> recetas) {
        int tiempo = this.tiempoEnSegundos != null ? this.tiempoEnSegundos : 0;

        for (Map.Entry<ObjetoComponente, Integer> entry : ingredientes.entrySet()) {
            ObjetoComponente componente = entry.getKey();
            int cantidad = entry.getValue();
            tiempo += componente.calcularTiempo(recetas) * cantidad;
        }

        return tiempo;
    }

    public void validar() {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalStateException(Constant.EXCEPCION_TIPO_VACIO);
        }
        if (tiempoEnSegundos == null || tiempoEnSegundos < 0) {
            throw new IllegalStateException(Constant.EXCEPCION_TIEMPO_INVALIDO);
        }
        if (ingredientes.isEmpty()) {
            throw new IllegalStateException(Constant.EXCEPCION_RECETA_SIN_INGREDIENTES);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receta receta = (Receta) o;
        return Objects.equals(tipo, receta.tipo)
                && Objects.equals(tiempoEnSegundos, receta.tiempoEnSegundos)
                && Objects.equals(ingredientes, receta.ingredientes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, tiempoEnSegundos, ingredientes);
    }
}
