/**
 * 
 */
package com.example.asteroides;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author robertome
 * 
 */
public class AlmacenPuntuacionesSQLiteImpl extends SQLiteOpenHelper implements
		AlmacenPuntuaciones {
	private static final String DB_NAME = "dbPuntuacionesAsteroides";
	private static final String TABLE_NAME = "PUNTUACION";

	private class Columns {
		static final String ID = "id";
		static final String PUNTOS = "puntos";
		static final String NOMBRE = "nombre";
		static final String FECHA = "fecha";
	}

	public AlmacenPuntuacionesSQLiteImpl(Context context) {
		super(context, DB_NAME, null, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + Columns.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.PUNTOS
				+ " INTEGER, " + Columns.NOMBRE + " TEXT, " + Columns.FECHA
				+ " LONG)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 * En caso de una nueva versión habría que actualizar las tablas
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#guardarPuntuacion(int,
	 * java.lang.String, long)
	 */
	@Override
	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		SQLiteDatabase db = getWritableDatabase();
		// db.execSQL("INSERT INTO puntuaciones VALUES ( null, " + puntos +
		// ", '" + nombre + "', " + fecha + ")");
		ContentValues values = new ContentValues();
		values.put(Columns.PUNTOS, puntos);
		values.put(Columns.NOMBRE, nombre);
		values.put(Columns.FECHA, fecha);
		db.insert(TABLE_NAME, null, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.asteroides.AlmacenPuntuaciones#listaPuntuaciones(int)
	 */
	@Override
	public List<String> listaPuntuaciones(int cantidad) {
		List<String> result = new ArrayList<String>();
		SQLiteDatabase db = getReadableDatabase();
		// Cursor cursor = db.rawQuery("SELECT puntos, nombre FROM "
		// + "puntuaciones ORDER BY puntos DESC LIMIT " + cantidad, null);
		/*
		 * Ejercicio paso a paso: Utilización del método query() para guardar
		 * puntuaciones
		 */
		// Cursor cursor = db.rawQuery("SELECT " + Columns.PUNTOS + ", "
		// + Columns.NOMBRE + " FROM " + TABLE_NAME + " ORDER BY "
		// + Columns.PUNTOS + " DESC LIMIT " + cantidad, null);
		String[] CAMPOS = { Columns.PUNTOS, Columns.NOMBRE };
		Cursor cursor = db.query(TABLE_NAME, CAMPOS, null, null, null, null,
				Columns.PUNTOS + " DESC", Integer.toString(cantidad));
		while (cursor.moveToNext()) {
			result.add(cursor.getInt(0) + " " + cursor.getString(1));
		}
		cursor.close();
		return result;
	}
}
