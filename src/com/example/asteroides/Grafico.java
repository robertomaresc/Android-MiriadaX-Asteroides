/**
 * 
 */
package com.example.asteroides;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Modelo grafico para la aplicacion
 * 
 * @author robertome
 * 
 */
class Grafico {
	// Para determinar el espacio a borrar (view.ivalidate)
	public static final int MAX_VELOCIDAD = 20;
	// Imagen que dibujaremos
	private final Drawable drawable;
	// Donde dibujamos el gráfico (usada en view.ivalidate)
	private final View view;
	// Posición
	private double posX;
	private double posY;
	// Velocidad desplazamiento
	private double incX;
	private double incY;
	// Ángulo y velocidad rotación
	private int angulo;
	private int rotacion;
	// Dimensiones de la imagen
	private final int ancho;
	private final int alto;
	// Para determinar colisión
	private final int radioColision;

	public Grafico(View view, Drawable drawable) {
		this.view = view;
		this.drawable = drawable;
		ancho = drawable.getIntrinsicWidth();
		alto = drawable.getIntrinsicHeight();
		radioColision = (alto + ancho) / 4;
	}

	public void dibujaGrafico(Canvas canvas) {
		canvas.save();
		int x = (int) (posX + ancho / 2);
		int y = (int) (posY + alto / 2);
		canvas.rotate(angulo, x, y);
		drawable.setBounds((int) posX, (int) posY, (int) posX + ancho,
				(int) posY + alto);
		drawable.draw(canvas);
		canvas.restore();
		int rInval = (int) Math.hypot(ancho, alto) / 2 + MAX_VELOCIDAD;
		view.invalidate(x - rInval, y - rInval, x + rInval, y + rInval);
	}

	public void incrementaPos(double factor) {
		posX += incX * factor;
		// Si salimos de la pantalla, corregimos posición
		if (posX < -ancho / 2) {
			posX = view.getWidth() - ancho / 2;
		}
		if (posX > view.getWidth() - ancho / 2) {
			posX = -ancho / 2;
		}
		posY += incY * factor;
		if (posY < -alto / 2) {
			posY = view.getHeight() - alto / 2;
		}
		if (posY > view.getHeight() - alto / 2) {
			posY = -alto / 2;
		}
		// Actualizamos ángulo
		angulo += rotacion * factor;
	}

	public double distancia(Grafico g) {
		return Math.hypot(posX - g.posX, posY - g.posY);
	}

	public boolean verificaColision(Grafico g) {
		return (distancia(g) < (radioColision + g.radioColision));
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getIncX() {
		return incX;
	}

	public void setIncX(double incX) {
		this.incX = incX;
	}

	public double getIncY() {
		return incY;
	}

	public void setIncY(double incY) {
		this.incY = incY;
	}

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public int getRotacion() {
		return rotacion;
	}

	public void setRotacion(int rotacion) {
		this.rotacion = rotacion;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getRadioColision() {
		return radioColision;
	}
}