/**
 * 
 */
package com.example.asteroides;

import android.app.Activity;
import android.os.Bundle;

/**
 * Pantalla "Juego".
 * <p>
 * Incorpora la vista del juego
 * <p>
 * Intentamos poner en pausa el thread secundario cuando la actividad deje de
 * estar activa y reanudarlo cuando la actividad recupere el foco, ademass de
 * detener el thread cuando la actividad vaya a ser destruida
 * <p>
 * El uso de sensores ha de realizarse con mucho cuidado dado su elevado consumo
 * de batería. Resulta importante que cuando nuestra actividad quede en un
 * segundo plano se detenga la lectura de los sensores, para que así no siga
 * consumiendo batería.
 * 
 * @author robertome
 * 
 */
public class Juego extends Activity {
	private VistaJuego vistaJuego;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);
		vistaJuego.setActivityPadre(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * SENSORES: Registramos VistaJuego como sensorEventListener para el
		 * giro de la nave
		 */
		vistaJuego.registrarSensorParaGiro(vistaJuego,
				VistaJuego.TIPO_SENSOR_UTIL_GIRO);
		vistaJuego.getThread().reanudar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		vistaJuego.getThread().pausar();
		/*
		 * El uso de sensores ha de realizarse con mucho cuidado dado su elevado
		 * consumo de batería. Resulta importante que cuando nuestra actividad
		 * quede en un segundo plano se detenga la lectura de los sensores, para
		 * que así no siga consumiendo batería.
		 */
		vistaJuego.desregistrarSensorParaGiro(vistaJuego);
	}

	@Override
	protected void onDestroy() {
		vistaJuego.getThread().detener();
		super.onDestroy();
	}
}