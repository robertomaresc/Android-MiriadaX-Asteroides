/**
 * 
 */
package com.example.asteroides;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Implementacion utilizando Preferences
 * (/data/data/com.example.asteroides/shared_prefs/puntuaciones.xml)
 * <p>
 * Se almacenearan las 10 ultimas puntuaciones
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesPreferencesImpl implements AlmacenPuntuaciones {
	private static final String PREFERENCIAS = "puntuaciones";
	private final Context context;

	public AlmacenPuntuacionesPreferencesImpl(Context context) {
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
		SharedPreferences preferencias = context.getSharedPreferences(
				PREFERENCIAS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencias.edit();
		// editor.putString("puntuacion", puntos + " " + nombre);
		for (int n = 9; n >= 1; n--) {
			editor.putString("puntuacion" + n,
					preferencias.getString("puntuacion" + (n - 1), ""));
		}
		editor.putString("puntuacion0", puntos + " " + nombre);
		editor.commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#listaPuntuaciones(int)
	 */
	@Override
	public List<String> listaPuntuaciones(int cantidad) {
		List<String> result = new Vector<String>();
		SharedPreferences preferencias = context.getSharedPreferences(
				PREFERENCIAS, Context.MODE_PRIVATE);
		// String s = preferencias.getString("puntuacion", "");
		// if (s != "") {
		// result.add(s);
		// }
		for (int n = 0; n <= 9; n++) {
			String s = preferencias.getString("puntuacion" + n, "");
			if (s != "") {
				result.add(s);
			}
		}
		return result;
	}
}
