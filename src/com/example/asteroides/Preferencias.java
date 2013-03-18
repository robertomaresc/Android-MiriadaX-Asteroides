package com.example.asteroides;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Pantalla "Preferencias"
 * 
 * @author robertome
 * 
 */
public class Preferencias extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}
}