package com.dissonant.quotas;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.dissonant.quotas.fragments.SettingsFragment;

public class SettingsActivity extends PreferenceActivity {
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
   }
}

