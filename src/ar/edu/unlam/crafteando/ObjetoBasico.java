package ar.edu.unlam.crafteando;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjetoBasico extends ObjetoComponente {

    public ObjetoBasico(String nombre) {
        super(nombre, 1);
    }

    @Override
    public Map<ObjetoComponente, Integer> obtener() {
        Map<ObjetoComponente, Integer> resultado = new HashMap<>();
        resultado.put(this, getCantidad());
        return Collections.unmodifiableMap(resultado);
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

    @Override
    protected void mostrarConstruccionInterno(int cantidad, int nivel, boolean soloPrimerNivel, String prefijo, boolean esUltimo) {
        String linea = prefijo;
        if (nivel > 0) {
            linea += esUltimo ? "└─ " : "├─ ";
        }
        linea += "Ingrediente: " + this.getNombre();
        System.out.println(linea);
        System.out.println(prefijo + (nivel > 0 ? (esUltimo ? "    " : "│   ") : "") + "Cantidad: " + cantidad);
    }

    @Override
    public boolean estaVacio() {
        return false;
    }
}
