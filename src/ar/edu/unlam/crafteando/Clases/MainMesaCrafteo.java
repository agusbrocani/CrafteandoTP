package ar.edu.unlam.crafteando.Clases;

import ar.edu.unlam.crafteando.Jugador.Inventario;
import ar.edu.unlam.crafteando.Jugador.Recetario;

public class MainMesaCrafteo {
	public static void main(String[] args) {
		Recetario recetario = new Recetario();
		Inventario inventario = new Inventario();

		// 🔨 1) Crear receta para la mesa de carpintería
		Receta recetaMesa = new Receta("Mesa de Carpintería", "Mesa", 10);
		recetaMesa.agregarIngrediente(new ObjetoBasico("Madera"), 4);
		recetaMesa.agregarIngrediente(new ObjetoBasico("Piedra"), 2);
		recetario.agregarReceta(recetaMesa);

		// 🛠️ 2) Crear receta que la mesa va a desbloquear
		Receta recetaPuerta = new Receta("Puerta de Madera", "Carpintería", 6);
		recetaPuerta.agregarIngrediente(new ObjetoBasico("Madera"), 6);

		// ⚒️ 3) Construir la mesa desde la receta (retorna MesaCrafteo por tipo =
		// "Mesa")
		ObjetoComponente objetoConstruido = recetario.construirObjetoDesdeReceta(recetaMesa);

		// 🪑 4) Como ya sé que es una mesa, la puedo usar como tal
		MesaCrafteo mesaCarpinteria = (MesaCrafteo) objetoConstruido;
		mesaCarpinteria.agregarReceta(recetaPuerta); // Se desbloquea con la mesa

		// 📦 5) Agregar la mesa al inventario
		inventario.agregar(mesaCarpinteria, 1);

		// 🔓 6) Desbloquear recetas (sin instanceof, usando polimorfismo)
		mesaCarpinteria.desbloquearSiEsMesa(recetario);

		// 👀 7) Mostrar receta desbloqueada
		recetario.mostrarReceta("Puerta de Madera");
	}
}
