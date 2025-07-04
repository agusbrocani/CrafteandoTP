package ar.edu.unlam.crafteando;

import java.time.LocalDateTime;
import java.util.Map;

public class RegistroCrafteo {
	/*
    private ObjetoCompuesto objetoCrafteado;
    private int cantidad;
    */
	String nombreObjetoCrafteado;
    private final Map<ObjetoComponente, Integer> ingredientes;
    private LocalDateTime fechaHora;

    /*
    public RegistroCrafteo(ObjetoCompuesto objeto, int cantidad, Map<ObjetoComponente, Integer> ingredientes, LocalDateTime fechaHora) {
        this.objetoCrafteado = objeto;
        this.cantidad = cantidad;
        this.ingredientes = ingredientes;
        this.fechaHora = fechaHora;
    }
    
    public String toString() {
        return "Objeto: " + objetoCrafteado.getNombre() +
               " | Cantidad: " + cantidad +
               " | Ingredientes: " + ingredientes +
               " | Fecha: " + fechaHora;
    }
    */

    public RegistroCrafteo(String nombreObjetoCrafteado, Map<ObjetoComponente, Integer> ingredientes, LocalDateTime fechaHora) {
    	this.nombreObjetoCrafteado = nombreObjetoCrafteado;
        this.ingredientes = ingredientes;
        this.fechaHora = fechaHora;
    }

    public String toString() {
        return "Objeto: " + nombreObjetoCrafteado +
               " | Ingredientes: " + ingredientes +
               " | Fecha: " + fechaHora;
    }
}