package ar.edu.unlam.crafteando;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjetoCompuesto extends ObjetoComponente {
    private final Map<ObjetoComponente, Integer> objetos;

    public ObjetoCompuesto() throws Exception {
        super("", 0);
        this.objetos = new HashMap<>();
    }

    public ObjetoCompuesto(String nombre, int cantidad) throws Exception {
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
        	if (o.getCantidad() > cantidad) {
                throw new IllegalArgumentException(Constant.EXCEPCION_CANTIDAD_INSUFICIENTE);
            }
            int nuevaCantidad = cantidad - o.getCantidad();
            return (nuevaCantidad > 0) ? nuevaCantidad : null;
        });
    }

    @Override
    public Map<ObjetoComponente, Integer> obtener() {
        return Collections.unmodifiableMap(new HashMap<>(objetos));
    }

    @Override
    public int calcularTiempo(Map<ObjetoCompuesto, Receta> recetas) {
        Receta receta = recetas.get(this);
        if (receta == null) return 0;
        return receta.calcularTiempoTotal(recetas);
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
        return Objects.equals(getNombre(), that.getNombre()) &&
               Objects.equals(objetos, that.objetos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), objetos);
    }
}
