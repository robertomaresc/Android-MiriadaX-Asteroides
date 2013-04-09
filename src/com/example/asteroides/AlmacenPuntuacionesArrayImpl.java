/**
 * 
 */
package com.example.asteroides;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de la interfaz {@link AlmacenPuntuaciones} utilizando un
 * ArrayList
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesArrayImpl implements AlmacenPuntuaciones {
	private final List<String> puntuaciones;

	public AlmacenPuntuacionesArrayImpl() {
		puntuaciones = new ArrayList<String>();
		puntuaciones.add("123000 Pepito Domingez");
		puntuaciones.add("111000 Pedro Martinez");
		puntuaciones.add("011000 Paco Pérez");
	}

	@Override
	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		puntuaciones.add(0, puntos + " " + nombre);
	}

	@Override
	public List<String> listaPuntuaciones(int cantidad) {
		return puntuaciones;
	}
}
