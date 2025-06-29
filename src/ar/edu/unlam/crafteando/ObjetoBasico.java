package ar.edu.unlam.crafteando;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjetoBasico extends ObjetoComponente {

    public ObjetoBasico(String nombre, Integer cantidad) {
        super(nombre, cantidad);
    }

    @Override
    public Map<ObjetoComponente, Integer> obtener() {
        Map<ObjetoComponente, Integer> resultado = new HashMap<>();
        resultado.put(this, getCantidad());
        return Collections.unmodifiableMap(resultado);
    }
    
    
    @Override
    public int calcularTiempo(Map<ObjetoCompuesto, Receta> recetas) {
        return 0;
    }

    @Override
    public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
        Map<ObjetoBasico, Integer> resultado = new HashMap<>();
        resultado.put(this, getCantidad());
        return resultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjetoBasico that = (ObjetoBasico) o;
        return Objects.equals(getNombre(), that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }
    
    @Override
    public String toString() {
        return "Nombre: " + this.getNombre() + ", Cantidad: " + this.getCantidad();
    }
}

