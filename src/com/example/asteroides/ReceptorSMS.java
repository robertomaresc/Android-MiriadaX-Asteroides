package com.example.asteroides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Modulo 8: BroadcastReciever
 * <p>
 * Ejemplo para un receptor de notificaciones
 * 
 * @author robertome
 * 
 */
public class ReceptorSMS extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// Sacamos información del intent
		String estado = "", numero = "";
		Bundle extras = intent.getExtras();
		if (extras != null) {
			estado = extras.getString(TelephonyManager.EXTRA_STATE);
			if (estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				numero = extras
						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			}
		}
		String info = estado + " " + numero;
		Log.d("ReceptorAnuncio", info + " intent=" + intent);
		/*
		 * Lanzamos actividad tras recibir un SMS
		 */
		lanzarAcercaDe(context);
		// lanzarAsteroides(context);
	}

	private void lanzarAcercaDe(Context context) {
		Intent i = new Intent(context, AcercaDe.class);
		context.startActivity(i);
	}

	private void lanzarAsteroides(Context context) {
		/*-
		 * Android trata de forma diferente a las actividades principales de una
		 * tarea (marcada en <action>como MAIN). Si intentas arrancar una
		 * actividad de este tipo Android considera que estás tratando de
		 * arrancar una nueva tarea. El problema surge porque para lanzar una
		 * tarea desde una intención resulta imprescindible activar el flag
		 * FLAG_ACTIVITY_NEW_TASK. 
		 *  
		 * i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 */
		Intent i = new Intent(context, Asteroides.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}
