package jp.dip.pcdennokan.pcdennokanviewer;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class AppPreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	}
}
