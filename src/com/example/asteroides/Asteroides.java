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

/**
 * Pantalla principal de la aplicacion Asteroides
 * 
 * @author robertome
 * 
 */
public class Asteroides extends Activity {
	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArrayImpl();
	private Button btnAcercaDe;
	private Button btnSalir;
	private Button btnConfigurar;
	private Button btnPuntuaciones;
	private Button btnJugar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
