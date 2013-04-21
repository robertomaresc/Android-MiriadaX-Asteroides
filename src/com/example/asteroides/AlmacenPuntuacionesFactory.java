/**
 * 
 */
package com.example.asteroides;

import android.content.Context;

/**
 * Singleton factory de puntuaciones
 * 
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesFactory {
	public enum TipoAlmacenamiento {
		ARRAY, PREFERENCIAS, FICHERO_INTERNO, FICHERO_EXTERNO, FICHERO_XML_SAX, BASE_DATOS, SERVIDOR_PUNTUACIONES
	};

	private static AlmacenPuntuacionesFactory instance;
	private final Context context;

	public static AlmacenPuntuacionesFactory getInstance(Context context) {
		if (instance == null) {
			instance = new AlmacenPuntuacionesFactory(context);
		}
		return instance;
	}

	private AlmacenPuntuacionesFactory(Context context) {
		this.context = context;
	}

	public AlmacenPuntuaciones crearAlmacenPuntuaciones(
			TipoAlmacenamiento tipoAlmacenamiento) {
		AlmacenPuntuaciones almacen = null;
		switch (tipoAlmacenamiento) {
		case PREFERENCIAS:
			almacen = new AlmacenPuntuacionesPreferencesImpl(context);
			break;
		case FICHERO_INTERNO:
			almacen = new AlmacenPuntuacionesFicheroInternoImpl(context);
			break;
		case FICHERO_EXTERNO:
			almacen = new AlmacenPuntuacionesFicheroExternoImpl(context);
			break;
		case FICHERO_XML_SAX:
			almacen = new AlmacenPuntuacionesXML_SAXImpl(context);
			break;
		case BASE_DATOS:
			almacen = new AlmacenPuntuacionesSQLiteImpl(context);
			break;
		case SERVIDOR_PUNTUACIONES:
			almacen = new AlmacenPuntuacionesSocketImpl();
			break;
		default:
			// ARRAY
			almacen = new AlmacenPuntuacionesArrayImpl();
		}
		return almacen;
	}
}
