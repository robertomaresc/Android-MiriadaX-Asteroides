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
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * Implementacin utilizando un fichero externo, normalmente
 * (/sdcard/puntuaciones.txt)
 * <p>
 * Siempre es mejor utilizar
 * <code>Enviroment.getExternalStorageDirectory()</code>
 * <p>
 * Crearlo en la carpeta /sdcard/Android/data/…/files/. Aunque solo se eliminará
 * si la aplicación se instala en un dispositivo con versión 2.2 o superior.
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesFicheroExternoImpl implements
		AlmacenPuntuaciones {
	private static String FICHERO = Environment.getExternalStorageDirectory()
			+ "/puntuaciones.txt";
	private final Context context;

	public AlmacenPuntuacionesFicheroExternoImpl(Context context) {
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
		 * Comprobamos el estado de la Memoria externa
		 */
		String estadoSD = Environment.getExternalStorageState();
		if (!estadoSD.equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(context, "No puedo escribir en la memoria externa",
					Toast.LENGTH_LONG).show();
		} else {
			try {
				FileOutputStream f = new FileOutputStream(FICHERO, true);
				String texto = puntos + " " + nombre + "\n";
				f.write(texto.getBytes());
				f.close();
			} catch (Exception e) {
				Log.e("Asteroides", e.getMessage(), e);
			}
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
		/*
		 * Comprobamos el estado de la Memoria externa
		 */
		String estadoSD = Environment.getExternalStorageState();
		if (!estadoSD.equals(Environment.MEDIA_MOUNTED)
				&& !estadoSD.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			Toast.makeText(context, "No puedo leer en la memoria externa",
					Toast.LENGTH_LONG).show();
		} else {
			try {
				FileInputStream f = new FileInputStream(FICHERO);
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
		}
		return result;
	}
}
