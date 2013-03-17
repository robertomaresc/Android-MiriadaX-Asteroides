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
 * intentamos poner en pausa el thread secundario cuando la actividad deje de
 * estar activa y reanudarlo cuando la actividad recupere el foco, ademass de
 * detener el thread cuando la actividad vaya a ser destruida
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
	}

	@Override
	protected void onPause() {
		super.onPause();
		vistaJuego.getThread().pausar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		vistaJuego.getThread().reanudar();
	}

	@Override
	protected void onDestroy() {
		vistaJuego.getThread().detener();
		super.onDestroy();
	}
}