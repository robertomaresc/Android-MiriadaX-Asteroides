/**
 * 
 */
package com.example.asteroides;

import java.util.List;

/**
 * Interfaz para el almacenamiento de puntuaciones
 * 
 * @author Administrador
 * 
 */
public interface AlmacenPuntuaciones {

	public void guardarPuntuacion(int puntos, String nombre, long fecha);

	public List<String> listaPuntuaciones(int cantidad);
}
