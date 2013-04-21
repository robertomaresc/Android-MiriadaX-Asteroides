/**
 * 
 */
package com.example.asteroides;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import android.util.Log;

/**
 * Implementacion utilizando un servidor de puntuaciones
 * (Asteroides_ServidorPuntuaciones)
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesSocketImpl implements AlmacenPuntuaciones {
	private static final int PUERTO_SERVIDOR_PUNTUACIONES = 1234;
	private static final String IP_SERVIDOR_PUNTUACIONES = "192.168.1.33";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#guardarPuntuacion(int,
	 * java.lang.String, long)
	 */
	@Override
	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		try {
			Socket socket = new Socket(IP_SERVIDOR_PUNTUACIONES,
					PUERTO_SERVIDOR_PUNTUACIONES);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);
			salida.println(puntos + " " + nombre);
			String respuesta = entrada.readLine();
			if (!respuesta.equals("OK")) {
				Log.e("Asteroides", "Error: respuesta de servidor incorrecta");
			}
			socket.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.toString(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#listaPuntuaciones(int)
	 */
	@Override
	public List<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();
		try {
			Socket socket = new Socket(IP_SERVIDOR_PUNTUACIONES,
					PUERTO_SERVIDOR_PUNTUACIONES);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);
			salida.println("PUNTUACIONES");
			int n = 0;
			String respuesta;
			do {
				respuesta = entrada.readLine();
				if (respuesta != null) {
					result.add(respuesta);
					n++;
				}
			} while (n < cantidad && respuesta != null);
			socket.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.toString(), e);
		}
		return result;
	}
}
