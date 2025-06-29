package ar.edu.unlam.crafteando;

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

    public String getTipo() {
        return tipo;
    }

    public Integer getTiempoEnSegundos() {
        return tiempoEnSegundos;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTiempoEnSegundos(Integer tiempoEnSegundos) {
        this.tiempoEnSegundos = tiempoEnSegundos;
    }

    public Map<ObjetoComponente, Integer> getIngredientes() {
        return ingredientes;
    }

    public void agregarIngrediente(ObjetoComponente componente, int cantidad) {
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
