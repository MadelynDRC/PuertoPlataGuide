package com.efitic.puertoplata.puertoplataguide;

/**
 * Created by madelyn on 20/6/15.
 */
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_language);
    }
}