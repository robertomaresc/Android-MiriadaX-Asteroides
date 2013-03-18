package com.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArrayImpl();
	private static final String VAR_ESTADO__POSICION_MUSICA = "posicionMusica";
	private Button btnAcercaDe;
	private Button btnSalir;
	private Button btnConfigurar;
	private Button btnPuntuaciones;
	private Button btnJugar;
	private MediaPlayer mp;

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
		mp = MediaPlayer.create(this, R.raw.audio);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		mp.start();
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
		mp.pause();
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
		return true;
		/** true -> el men� ya est� visible */
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
		return true;
		/** true -> consumimos el item, no se propaga */
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
		if (mp != null) {
			int pos = mp.getCurrentPosition();
			estadoGuardado.putInt(VAR_ESTADO__POSICION_MUSICA, pos);
		}
	}

	/**
	 * Recuperamos la posicion en la que se encontraba la musica cuando se
	 * destruyo la aplicacion
	 */
	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado) {
		super.onRestoreInstanceState(estadoGuardado);
		if (estadoGuardado != null && mp != null) {
			int pos = estadoGuardado.getInt(VAR_ESTADO__POSICION_MUSICA);
			mp.seekTo(pos);
		}
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
		startActivity(i);
	}
}
