/**
 * 
 */
package com.example.asteroides;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * Implementacin utilizando un fichero de recurso
 * (/data/data/com.example.asteroides/res/raw/puntuaciones.txt)
 * <p>
 * Estos tipos de ficheros no pueden ser modificados.
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesRecursoImpl implements AlmacenPuntuaciones {
	private final Context context;

	public AlmacenPuntuacionesRecursoImpl(Context context) {
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
		/*
		 * Estos tipos de ficheros no pueden ser modificados.
		 */
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
			InputStream f = context.getResources().openRawResource(
					R.raw.puntuaciones);
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
