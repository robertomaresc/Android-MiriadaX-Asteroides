/**
 * 
 */
package com.example.asteroides;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

/**
 * Parser para trabajar con el XML de puntuaciones.
 * <p>
 * Se utiliza SAX para trabajar con XML de puntuaciones.xml
 * 
 * @author robertome
 * 
 */
public class ListaPuntuacionesSAXParser {
	private static final String XML_NAMESPACE = "";
	private final List<Puntuacion> listaPuntuaciones;

	public ListaPuntuacionesSAXParser() {
		listaPuntuaciones = new ArrayList<Puntuacion>();
	}

	public void nuevo(int puntos, String nombre, long fecha) {
		Puntuacion puntuacion = new Puntuacion();
		puntuacion.puntos = puntos;
		puntuacion.nombre = nombre;
		puntuacion.fecha = fecha;
		listaPuntuaciones.add(puntuacion);
	}

	public void leerXML(InputStream entrada) throws Exception {
		/*
		 * Para leer un documento XML comenzamos creando una instancia de la
		 * clase SAXParserFactory, lo que nos permite crear un nuevo parser XML
		 * de tipo SAXParser. Luego creamos un lector, de la claseXMLReader,
		 * asociado a este parser. Creamos manejadorXML de la clase XMLHadler y
		 * asociamos este manejador al XMLReader.
		 */
		SAXParserFactory fabrica = SAXParserFactory.newInstance();
		SAXParser parser = fabrica.newSAXParser();
		XMLReader lector = parser.getXMLReader();
		ManejadorXML manejadorXML = new ManejadorXML();
		lector.setContentHandler(manejadorXML);
		lector.parse(new InputSource(entrada));
	}

	public void escribirXML(OutputStream salida) {
		XmlSerializer serializador = Xml.newSerializer();
		try {
			serializador.setOutput(salida, "UTF-8");
			serializador.startDocument("UTF-8", true);
			serializador.startTag(XML_NAMESPACE, "lista_puntuaciones");
			for (Puntuacion puntuacion : listaPuntuaciones) {
				serializador.startTag(XML_NAMESPACE, "puntuacion");
				serializador.attribute(XML_NAMESPACE, "fecha",
						String.valueOf(puntuacion.fecha));
				serializador.startTag(XML_NAMESPACE, "nombre");
				serializador.text(puntuacion.nombre);
				serializador.endTag(XML_NAMESPACE, "nombre");
				serializador.startTag(XML_NAMESPACE, "puntos");
				serializador.text(String.valueOf(puntuacion.puntos));
				serializador.endTag(XML_NAMESPACE, "puntos");
				serializador.endTag(XML_NAMESPACE, "puntuacion");
			}
			serializador.endTag(XML_NAMESPACE, "lista_puntuaciones");
			serializador.endDocument();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
	}

	public List<String> toListString() {
		List<String> result = new ArrayList<String>();
		for (Puntuacion puntuacion : listaPuntuaciones) {
			result.add(puntuacion.nombre + " " + puntuacion.puntos);
		}
		return result;
	}
}
