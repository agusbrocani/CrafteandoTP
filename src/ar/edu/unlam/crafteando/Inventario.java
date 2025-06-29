package ar.edu.unlam.crafteando;

import java.util.*;

public class Inventario {

	private final Map<ObjetoComponente, Integer> objetos;
	
	//Constructor
	public Inventario() {
		
		objetos = new HashMap<ObjetoComponente, Integer>();
	}
	
	public void ver() {
	    for (Map.Entry<ObjetoComponente, Integer> entry : objetos.entrySet()) {
	        ObjetoComponente objeto = entry.getKey();
	        Integer cantidad = entry.getValue();
	        System.out.println("[" + objeto.getNombre() + " - " + cantidad + "]");
	    }
	}
	
	//quitar un objeto del inventario
	public void quitar(ObjetoComponente o)
	{
	    if (!contiene(o)) {
	        throw new IllegalArgumentException("El objeto no está en el inventario.");
	    }
	    
    	int cantidadActual = objetos.get(o);
    	
	    if (o.getCantidad() > cantidadActual) {
	        throw new IllegalArgumentException("Cantidad insuficiente. Actual: " + cantidadActual + 
	        		", Solicitada: " + o.getCantidad());
	    }
    	
	    int nuevaCantidad = cantidadActual - o.getCantidad();
	    
    	if(nuevaCantidad == 0)
    		objetos.remove(o);
    	else
    		objetos.put(o, nuevaCantidad);

}
	
	//agregar un objeto al inventario
	public void agregar(ObjetoComponente o) 
	{
	    if (contiene(o)) {
	        int cantidadActual = obtenerCantidad(o);
	        objetos.put(o, cantidadActual + o.getCantidad());
	    } else {
	        objetos.put(o, o.getCantidad());
	    }
	}
	
	public Integer obtenerCantidad(ObjetoComponente o) {
		return objetos.get(o);
	}
	
	public boolean contiene(ObjetoComponente o)
	{
		return objetos.containsKey(o);
	}
	
	public void guardarComoJson(String rutaArchivo) {
	    List<EntradaInventario> lista = objetos.entrySet().stream()
	        .map(entry -> new EntradaInventario(entry.getKey().getNombre(), entry.getValue()))
	        .toList();

	    GestorJson.guardar(lista, rutaArchivo);
	}
	

}

