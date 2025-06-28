package ar.edu.unlam.crafteando;
import java.util.Map;

public abstract class ObjetoComponente {
	private String nombre;
	private Integer cantidad;
	
	public ObjetoComponente() {
		
	}
	
	public ObjetoComponente(String nombre) {
		this.nombre = nombre;
		this.cantidad = 0;
	}
	
	public ObjetoComponente(String  nombre, Integer cantidad) throws Exception {
		this.nombre = nombre;
		if (cantidad < 0) {
			throw new Exception(Constant.EXCEPCION_CANTIDAD_NEGATIVA);
		}
		this.cantidad = cantidad;
	}
	
	public abstract Map<ObjetoComponente, Integer> obtener();
	
	public String getNombre() {
		return this.nombre;
	}
	
	public Integer getCantidad() {
		return this.cantidad;
	}
}
