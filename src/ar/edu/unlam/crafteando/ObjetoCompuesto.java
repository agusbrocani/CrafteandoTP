package ar.edu.unlam.crafteando;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class ObjetoCompuesto extends ObjetoComponente {
    private final Map<ObjetoComponente, Integer> objetos;

    public ObjetoCompuesto(String nombre) {
        super(nombre, 1);
        this.objetos = new HashMap<>();
    }

    private ObjetoCompuesto(String nombre, Integer cantidad) {
        super(nombre, cantidad);
        this.objetos = new HashMap<>();
    }

    public void agregar(ObjetoComponente o) {
        if (o == null) {
            throw new IllegalArgumentException(Constant.EXCEPCION_AGREGAR_COMPONENTE_NULO);
        }
        objetos.merge(o, o.getCantidad(), Integer::sum);
    }

    public void remover(ObjetoComponente o) {
        if (o == null) {
            throw new IllegalArgumentException(Constant.EXCEPCION_ELIMINAR_COMPONENTE_NULO);
        }
        objetos.computeIfPresent(o, (clave, cantidad) -> {
            int nuevaCantidad = cantidad - o.getCantidad();
            return (nuevaCantidad > 0) ? nuevaCantidad : null;
        });
    }

    @Override
    public Map<ObjetoComponente, Integer> obtener() {
        return Collections.unmodifiableMap(new HashMap<>(objetos));
    }

    @Override
    public Map<ObjetoBasico, Integer> descomponerEnBasicos() {
        Map<ObjetoBasico, Integer> resultado = new HashMap<>();
        for (Map.Entry<ObjetoComponente, Integer> entry : objetos.entrySet()) {
            Map<ObjetoBasico, Integer> sub = entry.getKey().descomponerEnBasicos();
            int cantidad = entry.getValue();
            for (Map.Entry<ObjetoBasico, Integer> e : sub.entrySet()) {
                resultado.merge(e.getKey(), e.getValue() * cantidad, Integer::sum);
            }
        }
        return resultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjetoCompuesto that = (ObjetoCompuesto) o;
        return Objects.equals(getNombre(), that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }

    @Override
    public String toString() {
        return "Nombre: " + this.getNombre() + "\nCantidad: " + this.getCantidad();
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

        if (!soloPrimerNivel) {
            List<Map.Entry<ObjetoComponente, Integer>> hijos = new ArrayList<>(objetos.entrySet());
            for (int i = 0; i < hijos.size(); i++) {
                Map.Entry<ObjetoComponente, Integer> entry = hijos.get(i);
                boolean ultimo = (i == hijos.size() - 1);
                String nuevoPrefijo = prefijo + (nivel > 0 ? (esUltimo ? "    " : "│   ") : "");
                entry.getKey().mostrarConstruccionInterno(entry.getValue(), nivel + 1, false, nuevoPrefijo, ultimo);
            }
        }
    }

    @Override
    public boolean estaVacio() {
        return objetos.isEmpty();
    }
}