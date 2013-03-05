/**
 * 
 */
package com.example.asteroides;

import java.util.ArrayList;

/**
 * Implementacion de la interfaz {@link AlmacenPuntuaciones} utilizando un
 * ArrayList
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesArrayImpl implements AlmacenPuntuaciones {
	private final ArrayList<String> puntuaciones;

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
	public ArrayList<String> listaPuntuaciones(int cantidad) {
		return puntuaciones;
	}
}
