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
	private final int numAsteroides = 5; // Número inicial de asteroides
	private final int numFragmentos = 3; // Fragmentos en que se divide
	/*
	 * NAVE
	 */
	private final Grafico nave;// Gráfico de la nave
	private int giroNave; // Incremento de dirección
	private float aceleracionNave; // aumento de velocidad
	// Incremento estándar de giro y aceleración
	private static final int PASO_GIRO_NAVE = 5;
	private static final float PASO_ACELERACION_NAVE = 0.5f;

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
	}

	@Override
	protected void onDraw(Canvas canvas) {
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
}