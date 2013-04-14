/**
 * 
 */
package com.example.asteroides;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Manejador para el procesamiento del XML de puntuaciones
 * 
 * @author robertome
 * 
 */
public class ManejadorXML extends DefaultHandler {
	private StringBuilder cadena;
	private List<Puntuacion> listaPuntuaciones;
	private Puntuacion puntuacion;

	@Override
	public void startDocument() throws SAXException {
		listaPuntuaciones = new ArrayList<Puntuacion>();
		cadena = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String nombreLocal,
			String nombreCualif, Attributes atr) throws SAXException {
		cadena.setLength(0);
		if (nombreLocal.equals("puntuacion")) {
			puntuacion = new Puntuacion();
			puntuacion.fecha = Long.parseLong(atr.getValue("fecha"));
		}
	}

	@Override
	public void characters(char ch[], int comienzo, int lon) {
		cadena.append(ch, comienzo, lon);
	}

	@Override
	public void endElement(String uri, String nombreLocal, String nombreCualif)
			throws SAXException {
		if (nombreLocal.equals("puntos")) {
			puntuacion.puntos = Integer.parseInt(cadena.toString());
		} else if (nombreLocal.equals("nombre")) {
			puntuacion.nombre = cadena.toString();
		} else if (nombreLocal.equals("puntuacion")) {
			listaPuntuaciones.add(puntuacion);
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}
}
