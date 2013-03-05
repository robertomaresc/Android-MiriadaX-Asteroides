package com.example.asteroides;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Puntuaciones extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puntuaciones);
		// setListAdapter(crearArrayAdapterSimpleList());
		// setListAdapter(crearArrayAdapterElementoLista());
		setListAdapter(crearMiAdaptador());
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		Object o = getListAdapter().getItem(position);
		Toast.makeText(
				this,
				"Selección: " + Integer.toString(position) + " - "
						+ o.toString(), Toast.LENGTH_LONG).show();
	}

	/*
	 * 1.- Adapter utilizando la vista de sistema simple_list_item_1
	 */
	private ListAdapter crearArrayAdapterSimpleList() {
		return new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				Asteroides.almacen.listaPuntuaciones(10));
	}

	/*
	 * 2.- Adapter utilizando la vista definida en res/layout/elemento_lista.xml
	 */
	private ListAdapter crearArrayAdapterElementoLista() {
		return new ArrayAdapter<String>(this, R.layout.elemento_lista,
				R.id.titulo, Asteroides.almacen.listaPuntuaciones(10));
	}

	/*
	 * 3.- Adapter propio
	 */
	private ListAdapter crearMiAdaptador() {
		return new MiAdaptador(this, Asteroides.almacen.listaPuntuaciones(10));
	}

}