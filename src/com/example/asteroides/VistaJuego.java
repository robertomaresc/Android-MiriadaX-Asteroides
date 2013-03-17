/**
 * 
 */
package com.example.asteroides;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author robertome
 * 
 */
public class VistaJuego extends View implements SensorEventListener {
	private enum TipoGrafico {
		VECTORIAL, BITMAP
	};

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
	 * MISIL
	 */
	private static final int PASO_VELOCIDAD_MISIL = 12;
	private final Grafico misil;
	private boolean misilActivo = false;
	private int tiempoMisil;
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
	 * Variables para los eventos "onTouchEvent"
	 */
	// Ultima posicion X de la nave
	private float mX = 0;
	// Ultima posicion Y de la nave
	private float mY = 0;
	private boolean disparo = false;
	/*
	 * Variable para movimiento con sensores. Evento "onSensorChanged"
	 */
	private static final int TIPO_SENSOR_UTIL_GIRO = /* Sensor.TYPE_ORIENTATION */Sensor.TYPE_ACCELEROMETER;
	private boolean hayValorInicial = false;
	private float valorInicial;

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
		/*
		 * Inicializamos misil
		 */
		Drawable drawableMisil = crearMisil(TipoGrafico.BITMAP);
		misil = new Grafico(this, drawableMisil);
		/*
		 * SENSORES: Registramos VistaJuego como sensorEventListener para el
		 * giro de la nave
		 */
		registrarSensorParaGiro(this, TIPO_SENSOR_UTIL_GIRO);
	}

	private Drawable crearMisil(TipoGrafico graficoType) {
		Drawable drawableMisil = null;
		if (TipoGrafico.VECTORIAL.equals(graficoType)) {
			ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
			dMisil.getPaint().setColor(Color.WHITE);
			dMisil.getPaint().setStyle(Style.STROKE);
			dMisil.setIntrinsicWidth(15);
			dMisil.setIntrinsicHeight(3);
			drawableMisil = dMisil;
		} else if (TipoGrafico.BITMAP.equals(graficoType)) {
			drawableMisil = getContext().getResources().getDrawable(
					R.drawable.misil1);
		}
		return drawableMisil;
	}

	private void registrarSensorParaGiro(
			SensorEventListener sensorEventListener, int sensorType) {
		SensorManager mSensorManager = (SensorManager) getContext()
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> listSensors = mSensorManager.getSensorList(sensorType);
		if (!listSensors.isEmpty()) {
			Sensor orientationSensor = listSensors.get(0);
			mSensorManager.registerListener(sensorEventListener,
					orientationSensor, SensorManager.SENSOR_DELAY_GAME);
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
		/*
		 * Dibujamos misil
		 */
		if (misilActivo) {
			misil.dibujaGrafico(canvas);
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
		/*
		 * Actualizamos posición de misil
		 */
		if (misilActivo) {
			misil.incrementaPos(retardo);
			tiempoMisil -= retardo;
			if (tiempoMisil < 0) {
				misilActivo = false;
			} else {
				for (int i = 0; i < listaAsteroides.size(); i++) {
					if (misil.verificaColision(listaAsteroides.get(i))) {
						destruyeAsteroide(i);
						break;
					}
				}
			}
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
				activaMisil();
			}
			break;
		}
		mX = x;
		mY = y;
		return true;
	}

	private void destruyeAsteroide(int i) {
		listaAsteroides.remove(i);
		misilActivo = false;
	}

	private void activaMisil() {
		misil.setPosX(nave.getPosX() + nave.getAncho() / 2 - misil.getAncho()
				/ 2);
		misil.setPosY(nave.getPosY() + nave.getAlto() / 2 - misil.getAlto() / 2);
		misil.setAngulo(nave.getAngulo());
		/*
		 * Para descomponerla en sus componentes X e Y utilizamos el coseno y el
		 * seno.
		 */
		misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo()))
				* PASO_VELOCIDAD_MISIL);
		misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo()))
				* PASO_VELOCIDAD_MISIL);
		/*
		 * Dada la naturaleza del espacio del juego (lo que sale por un lado
		 * aparece por el otro) si disparáramos un misil este podría acabar
		 * chocando contra la nave. Para solucionarlo vamos a dar un tiempo de
		 * vida al misil para impedir que pueda llegar de nuevo a la nave
		 * (tiempoMisil). Para obtener este tiempo nos quedamos con el mínimo
		 * entre el ancho dividido la velocidad en X y el alto dividido entre la
		 * velocidad en Y. Luego le restamos una constante. Terminamos activando
		 * el misil.
		 */
		tiempoMisil = (int) Math.min(
				this.getWidth() / Math.abs(misil.getIncX()), this.getHeight()
						/ Math.abs(misil.getIncY())) - 2;
		misilActivo = true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		giroNave = (TIPO_SENSOR_UTIL_GIRO == Sensor.TYPE_ORIENTATION) ? calcularGiroPorSensorORIENTATION(event)
				: calcularGiroPorSensorACCELOROMETER(event);
	}

	private int calcularGiroPorSensorORIENTATION(SensorEvent event) {
		float valor = event.values[1];
		if (!hayValorInicial) {
			valorInicial = valor;
			hayValorInicial = true;
		}
		return (int) (valor - valorInicial) / 3;
	}

	private int calcularGiroPorSensorACCELOROMETER(SensorEvent event) {
		/*-
		 * Jugamos con el valor de la aceleracion en el EjeY 
		 * (estamos con el telefono en apaisado)
		 * Los valores del acelerometro estaran entre -9.8 y +9.8
		 */
		float valor = event.values[1];
		return (int) valor;
	}

	public ThreadJuego getThread() {
		return thread;
	}

	/**
	 * Clase interna para un hilo que actualiza los graficos
	 * 
	 * @author robertome
	 * 
	 */
	class ThreadJuego extends Thread {
		private boolean pausa, corriendo;

		public synchronized void pausar() {
			pausa = true;
		}

		public synchronized void reanudar() {
			pausa = false;
			notify();
		}

		public void detener() {
			corriendo = false;
			if (pausa) {
				reanudar();
			}
		}

		@Override
		public void run() {
			corriendo = true;
			while (corriendo) {
				actualizaFisica();
				synchronized (this) {
					/*
					 * Se comprueba si se ha activado pausa. En tal caso, se
					 * entra en un bucle donde ponemos en espera elthread
					 * llamando al método wait(). Este quedará bloqueado hasta
					 * que se llame a notify(). Esta acción se realizará desde
					 * el método reanudar()
					 */
					while (pausa) {
						try {
							wait();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}
}