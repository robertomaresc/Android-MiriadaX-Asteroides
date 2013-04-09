package com.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Pantalla principal de la aplicacion Asteroides
 * 
 * @author robertome
 * 
 */
public class Asteroides extends Activity {
	public static AlmacenPuntuaciones almacen;
	// private static final String VAR_ESTADO__POSICION_MUSICA =
	// "posicionMusica";
	public static final int PUNTUACION_REQUEST_CODE = 1234;
	private Button btnAcercaDe;
	private Button btnSalir;
	private Button btnConfigurar;
	private Button btnPuntuaciones;
	private Button btnJugar;

	/*
	 * Modulo 8: Sustituimos el MediaPlayer por ServicioMusica
	 */
	// private MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.main);
		btnAcercaDe = (Button) findViewById(R.id.btnAcercaDe);
		btnConfigurar = (Button) findViewById(R.id.btnConfigurar);
		btnSalir = (Button) findViewById(R.id.btnSalir);
		btnPuntuaciones = (Button) findViewById(R.id.btnPuntuaciones);
		btnJugar = (Button) findViewById(R.id.btnJugar);
		/*-
		 * Para cualquier version del API
		 * Solo a partir del API 16 se puede asignar en re/layout/...xml un metodo 
		 * al evento onClick (y solo al evento onClick)
		 */
		btnAcercaDe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				lanzarAcercaDe(null);
			}
		});
		/*
		 * Configurar
		 */
		btnConfigurar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				lanzarPreferencias(null);
			}
		});
		/*
		 * Puntuaciones
		 */
		btnPuntuaciones.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				lanzarPuntuaciones(null);
			}
		});
		/*
		 * Jugar
		 */
		btnJugar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				lanzarJuego(null);
			}
		});
		/*
		 * Finalizar aplicacion
		 */
		btnSalir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		/*
		 * Modulo 8: Sustituimos el MediaPlayer por ServicioMusica
		 */
		// mp = MediaPlayer.create(this, R.raw.audio);
		/*
		 * Modulo 9: Uso de distintas implementaciones para el
		 * AlmacenPuntuaciones
		 */
		almacen = crearAlmacenPuntuaciones();
	}

	private AlmacenPuntuaciones crearAlmacenPuntuaciones() {
		AlmacenPuntuaciones almacen = null;
		// almacen = new AlmacenPuntuacionesArrayImpl();
		// almacen = new AlmacenPuntuacionesPreferencesImpl(this);
		almacen = new AlmacenPuntuacionesFicheroInternoImpl(this);
		return almacen;
	}

	@Override
	protected void onStart() {
		super.onStart();
		/*
		 * Modulo 8: Sustituimos el MediaPlayer por un servicio de musica
		 */
		startService(new Intent(this, ServicioMusica.class));
		Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		/*
		 * Modulo 8: Sustituimos el MediaPlayer por ServicioMusica
		 */
		// mp.start();
	}

	@Override
	protected void onPause() {
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
		/*
		 * Cuando la actividad deje de estar activa el audio deje de escucharse
		 */
		// mp.pause();
	}

	@Override
	protected void onStop() {
		Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		/*
		 * Cuando la actividad deje de estar visible el audio deje de escucharse
		 */
		/*
		 * Modulo 8: Sustituimos el MediaPlayer por ServicioMusica
		 */
		// mp.pause();
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		stopService(new Intent(this, ServicioMusica.class));
		super.onDestroy();
	}

	/**
	 * Para activar el menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		/* true -> el menu ya esta visible */
		return true;
	}

	/**
	 * Para activar el menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.acercaDe:
			lanzarAcercaDe(null);
			break;
		case R.id.config:
			lanzarPreferencias(null);
			break;
		}
		/* true -> consumimos el item, no se propaga */
		return true;
	}

	/**
	 * Cuando se ejecuta una actividad sensible a la inclinación del telefono,
	 * la actividad es destruida y vuelta a construir con las nuevas dimensiones
	 * de pantalla y por lo tanto se llama de nuevo al método onCreate(). Antes
	 * de que la actividad sea destruida puede resultar fundamental guardar su
	 * estado.
	 * <p>
	 * En este caso guardamos la posicion de la musica
	 */
	@Override
	protected void onSaveInstanceState(Bundle estadoGuardado) {
		super.onSaveInstanceState(estadoGuardado);
		/*
		 * Modulo 8: Sustituimos el MediaPlayer por ServicioMusica
		 */
		// if (mp != null) {
		// int pos = mp.getCurrentPosition();
		// estadoGuardado.putInt(VAR_ESTADO__POSICION_MUSICA, pos);
		// }
	}

	/**
	 * Recuperamos la posicion en la que se encontraba la musica cuando se
	 * destruyo la aplicacion
	 */
	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado) {
		super.onRestoreInstanceState(estadoGuardado);
		/*
		 * Modulo 8: Sustituimos el MediaPlayer por ServicioMusica
		 */
		// if (estadoGuardado != null && mp != null) {
		// int pos = estadoGuardado.getInt(VAR_ESTADO__POSICION_MUSICA);
		// mp.seekTo(pos);
		// }
	}

	private void lanzarAcercaDe(View view) {
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}

	private void lanzarPreferencias(View view) {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	public void lanzarPuntuaciones(View view) {
		Intent i = new Intent(this, Puntuaciones.class);
		startActivity(i);
	}

	public void lanzarJuego(View view) {
		Intent i = new Intent(this, Juego.class);
		/*
		 * Modulo 9
		 */
		// startActivity(i);
		startActivityForResult(i, 1234);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PUNTUACION_REQUEST_CODE & resultCode == RESULT_OK
				& data != null) {
			int puntuacion = data.getExtras().getInt("puntuacion");
			String nombre = "Yo";
			// Mejor leerlo desde un Dialog o una nueva actividad
			// //AlertDialog.Builder
			almacen.guardarPuntuacion(puntuacion, nombre,
					System.currentTimeMillis());
			lanzarPuntuaciones(null);
		}
	}
}
