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

    @Override
    public Map<ObjetoComponente, Integer> obtener() {
        return Collections.unmodifiableMap(new HashMap<>(objetos));
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
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;

        ObjetoCompuesto that = (ObjetoCompuesto) o;
        return Objects.equals(getNombre(), that.getNombre()) &&
               Objects.equals(objetos, that.objetos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), objetos);
    }
}
