/**
 * 
 */
package com.example.asteroides;

import java.io.FileNotFoundException;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * Implementacin utilizando un fichero XML, el fichero se almacenará en
 * "/data/data/com.example.asteroides/files/puntuaciones.xml". Pero, puedes
 * almacenarlos en otro lugar, como por ejemplo en la memoria SD
 * <p>
 * Se utiliza SAX para trabajar con XML de puntuaciones.xml. En el
 * almacenamiento se hace uso de la clase {@link ListaPuntuaciones} que
 * representa el modelo a guardar en el fichero XML.
 * <p>
 * La variable <code>cargadaLista</code> nos indica si lista ya ha sido leída
 * desde el fichero.
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesXML_SAXImpl implements AlmacenPuntuaciones {
	private static String FICHERO = "puntuaciones.xml";
	private final Context contexto;
	private final ListaPuntuacionesSAXParser listaParser;
	private boolean cargadaLista;

	public AlmacenPuntuacionesXML_SAXImpl(Context contexto) {
		this.contexto = contexto;
		listaParser = new ListaPuntuacionesSAXParser();
		cargadaLista = false;
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
			if (!cargadaLista) {
				listaParser.leerXML(contexto.openFileInput(FICHERO));
				cargadaLista = true;
			}
		} catch (FileNotFoundException e) {
			Log.e("Asteroides", e.getMessage(), e);
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
		listaParser.nuevo(puntos, nombre, fecha);
		try {
			listaParser.escribirXML(contexto.openFileOutput(FICHERO,
					Context.MODE_PRIVATE));
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
		try {
			if (!cargadaLista) {
				listaParser.leerXML(contexto.openFileInput(FICHERO));
			}
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
		return listaParser.toListString();
	}
}
