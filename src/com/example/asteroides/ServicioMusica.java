/**
 * 
 */
package com.example.asteroides;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Servicio encargado de la musica de la aplicacion.
 * 
 * @author robertome
 * 
 */
public class ServicioMusica extends Service {
	private static final int ID_NOTIFICACION_CREAR = 1;
	private MediaPlayer reproductor;
	private NotificationManager nm;

	@Override
	public void onCreate() {
		Toast.makeText(this, "Servicio creado", Toast.LENGTH_SHORT).show();
		reproductor = MediaPlayer.create(this, R.raw.audio);
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intenc, int flags, int idArranque) {
		Toast.makeText(this, "Servicio arrancado " + idArranque,
				Toast.LENGTH_SHORT).show();
		reproductor.start();
		Notification notificacion = new Notification(R.drawable.ic_launcher,
				"Creando Servicio de Música", System.currentTimeMillis());
		/*
		 * Define información adicional que será utilizada en la ventana de
		 * notificaciones. Esta información incluye el mensaje expandido y la
		 * actividad a ejecutar cuando se pulse sobre la notificación:
		 */
		PendingIntent intencionPendiente = PendingIntent.getActivity(this, 0,
				new Intent(this, Asteroides.class), 0);
		notificacion.setLatestEventInfo(this, "Reproduciendo música",
				"información adicional", intencionPendiente);
		/*
		 * Pasar la notificación creada al NotificationManager
		 */
		nm.notify(ID_NOTIFICACION_CREAR, notificacion);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT).show();
		reproductor.stop();
		nm.cancel(ID_NOTIFICACION_CREAR);
	}

	@Override
	public IBinder onBind(Intent intencion) {
		return null;
	}
}
