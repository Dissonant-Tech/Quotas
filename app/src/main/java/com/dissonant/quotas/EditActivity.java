package com.dissonant.quotas;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.dissonant.quotas.settings.SettingsActivity;
import com.dissonant.quotas.ui.QuotasChart;
import com.dissonant.quotas.R;

import com.dissonant.quotas.db.QuotaModel;

public class EditActivity extends Activity {        

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
