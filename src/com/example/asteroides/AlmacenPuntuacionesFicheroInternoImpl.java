/**
 * 
 */
package com.example.asteroides;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * Implementacin utilizando un fichero interno
 * (/data/data/com.example.asteroides/files/puntuaciones.txt)
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesFicheroInternoImpl implements
		AlmacenPuntuaciones {
	private static final String FICHERO = "puntuaciones.txt";
	private final Context context;

	public AlmacenPuntuacionesFicheroInternoImpl(Context context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#guardarPuntuacion(int,
	 * java.lang.String, long)
	 */
	@Override
	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		try {
			FileOutputStream f = context.openFileOutput(FICHERO,
					Context.MODE_APPEND);
			String texto = puntos + " " + nombre + "\n";
			f.write(texto.getBytes());
			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#listaPuntuaciones(int)
	 */
	@Override
	public List<String> listaPuntuaciones(int cantidad) {
		List<String> result = new ArrayList<String>();
		try {
			FileInputStream f = context.openFileInput(FICHERO);
			BufferedReader entrada = new BufferedReader(
					new InputStreamReader(f));
			int n = 0;
			String linea;
			do {
				linea = entrada.readLine();
				if (linea != null) {
					result.add(linea);
					n++;
				}
			} while (n < cantidad && linea != null);
			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
		return result;
	}
}
