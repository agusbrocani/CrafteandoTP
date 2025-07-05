package ar.edu.unlam.crafteando.Jugador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ar.edu.unlam.crafteando.Clases.*;

public class Historial {
	private List<RegistroCrafteo> registros;

	public Historial() {
		this.registros = new ArrayList<>();
	}

	public void registrar(String nombreObjetoCrafteado, Map<ObjetoComponente, Integer> ingredientesUsados) {
		RegistroCrafteo nuevo = new RegistroCrafteo(nombreObjetoCrafteado, ingredientesUsados, LocalDateTime.now());
		registros.add(nuevo);
	}
	
	/*
	public void registrar(ObjetoCompuesto objeto, int cantidad, Map<ObjetoComponente, Integer> ingredientesUsados) {
		RegistroCrafteo nuevo = new RegistroCrafteo(objeto, cantidad, ingredientesUsados, LocalDateTime.now());
		registros.add(nuevo);
	}
	*/

	public List<RegistroCrafteo> getRegistros() {
		return new ArrayList<>(registros);
	}

	public void mostrarHistorial() {
		for (RegistroCrafteo r : registros) {
			System.out.println(r);
		}
	}
}