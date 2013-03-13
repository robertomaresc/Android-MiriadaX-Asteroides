/**
 * 
 */
package com.example.asteroides;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author robertome
 * 
 */
public class VistaJuego extends View {
	/*
	 * ASTEROIDES
	 */
	private final List<Grafico> listaAsteroides; // List con los Asteroides
	private final int numAsteroides = 5; // N�mero inicial de asteroides
	private final int numFragmentos = 3; // Fragmentos en que se divide
	/*
	 * NAVE
	 */
	private final Grafico nave;// Gr�fico de la nave
	private int giroNave; // Incremento de direcci�n
	private float aceleracionNave; // aumento de velocidad
	// Incremento est�ndar de giro y aceleraci�n
	private static final int PASO_GIRO_NAVE = 5;
	private static final float PASO_ACELERACION_NAVE = 0.5f;
	/*
	 * THREAD Y TIEMPO
	 */
	// Thread encargado de procesar el juego
	private final ThreadJuego thread = new ThreadJuego();
	// Cada cuanto queremos procesar cambios (milisegundos)
	private static int PERIODO_PROCESO = 50;
	// Cuando se realizó el último proceso
	private long ultimoProceso = 0;
	/*
	 * Variables para los eventos onTouchEvent
	 */
	// Ultima posicion X
	private float mX = 0;
	// Ultima posicion Y
	private float mY = 0;
	private boolean disparo = false;

	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);
		/*
		 * Inicializamos nave
		 */
		Drawable drawableNave = context.getResources().getDrawable(
				R.drawable.nave);
		nave = new Grafico(this, drawableNave);
		/*
		 * Inicializamos asteroides
		 */
		Drawable drawableAsteroide = context.getResources().getDrawable(
				R.drawable.asteroide1);
		listaAsteroides = new ArrayList<Grafico>();
		for (int i = 0; i < numAsteroides; i++) {
			Grafico asteroide = new Grafico(this, drawableAsteroide);
			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setAngulo((int) (Math.random() * 360));
			asteroide.setRotacion((int) (Math.random() * 8 - 4));
			listaAsteroides.add(asteroide);
		}
	}

	@Override
	protected void onSizeChanged(int ancho, int alto, int ancho_anter,
			int alto_anter) {
		super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
		/*
		 * Una vez que conocemos nuestro ancho y alto.
		 */
		/*
		 * Posicionamos nave en el centro de la pantalla
		 */
		nave.setPosX((ancho - nave.getAncho()) / 2);
		nave.setPosY((alto - nave.getAlto()) / 2);
		/*
		 * Posicionamos asteroides aleatoriamente
		 */
		for (Grafico asteroide : listaAsteroides) {
			// asteroide.setPosX(Math.random() * (ancho -
			// asteroide.getAncho()));
			// asteroide.setPosY(Math.random() * (alto - asteroide.getAlto()));
			/*
			 * Evitamos posicionar asteroides sobre nave
			 */
			do {
				asteroide.setPosX(Math.random()
						* (ancho - asteroide.getAncho()));
				asteroide.setPosY(Math.random() * (alto - asteroide.getAlto()));
			} while (asteroide.distancia(nave) < (ancho + alto) / 5);
		}
		/*
		 * Lanzamos hilo para que actualice los graficos del juego
		 */
		ultimoProceso = System.currentTimeMillis();
		thread.start();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*
		 * Dibujamos nave
		 */
		nave.dibujaGrafico(canvas);
		/*
		 * Dibujamos asteroides
		 */
		for (Grafico asteroide : listaAsteroides) {
			asteroide.dibujaGrafico(canvas);
		}
	}

	protected synchronized void actualizaFisica() {
		long ahora = System.currentTimeMillis();
		/*
		 * No hagas nada si el período de proceso no se ha cumplido.
		 */
		if (ultimoProceso + PERIODO_PROCESO > ahora) {
			return;
		}
		/*
		 * Para una ejecución en tiempo real calculamos retardo
		 */
		double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
		ultimoProceso = ahora; // Para la próxima vez
		/*
		 * Actualizamos velocidad y dirección de la nave a partir de giroNave y
		 * aceleracionNave (según la entrada del jugador)
		 */
		nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));
		double nIncX = nave.getIncX() + aceleracionNave
				* Math.cos(Math.toRadians(nave.getAngulo())) * retardo;
		double nIncY = nave.getIncY() + aceleracionNave
				* Math.sin(Math.toRadians(nave.getAngulo())) * retardo;
		/*
		 * Actualizamos si el módulo de la velocidad no excede el máximo
		 */
		if (Math.hypot(nIncX, nIncY) <= Grafico.MAX_VELOCIDAD) {
			nave.setIncX(nIncX);
			nave.setIncY(nIncY);
		}
		/*
		 * Actualizamos posiciones X e Y
		 */
		nave.incrementaPos(retardo);
		for (Grafico asteroide : listaAsteroides) {
			asteroide.incrementaPos(retardo);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			disparo = true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dy < 6 && dx > 6) {
				giroNave = Math.round((x - mX) / 2);
				disparo = false;
			} else if (dx < 6 && dy > 6) {
				aceleracionNave = Math.round((mY - y) / 25);
				disparo = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			giroNave = 0;
			/*
			 * Modifica el código anterior para que no sea posible decelerar.
			 */
			// aceleracionNave = 0;
			if (disparo) {
				// TODO:
				// ActivaMisil();
			}
			break;
		}
		mX = x;
		mY = y;
		return true;
	}

	/**
	 * Clase interna para actualizar los graficos
	 * 
	 * @author robertome
	 * 
	 */
	class ThreadJuego extends Thread {
		@Override
		public void run() {
			while (true) {
				actualizaFisica();
			}
		}
	}
}