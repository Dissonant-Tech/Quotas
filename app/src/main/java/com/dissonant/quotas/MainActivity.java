package com.dissonant.quotas;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.dissonant.quotas.db.QuotaModel;
import com.dissonant.quotas.settings.SettingsActivity;
import com.dissonant.quotas.ui.QuotasChart;

public class MainActivity extends Activity implements OnClickListener {
    boolean resuming = false;

    QuotasChart mQuotasChart;
    QuotaModel []models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline mOutline = new Outline();
        mOutline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(mOutline);

        ImageButton button = (ImageButton)findViewById(R.id.fab);
        button.setOnClickListener(this);

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
            .getActionView();
        int searchBarId = searchView.getContext().getResources().getIdentifier("android:id/search_bar", null, null);
        LinearLayout searchBar = (LinearLayout) searchView.findViewById(searchBarId);
        searchBar.setLayoutTransition(new LayoutTransition());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        Intent i = new Intent(this, EditActivity.class);
        startActivity(i);
    }

    protected void onResume() {
        super.onResume();
        if (!resuming) {
            populateView();
            resuming = true;
        } else {
            repaintView();
        }
    }

    public void populateView() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        mQuotasChart = new QuotasChart();
        mQuotasChart.init(this);
        mQuotasChart.setData(models);
        layout.addView(mQuotasChart.getView());
    }

    public void repaintView() {
        mQuotasChart.repaint();
    }
}