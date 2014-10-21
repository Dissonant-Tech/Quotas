package com.dissonant.quotas;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dissonant.quotas.db.models.QuotaModel;
import com.dissonant.quotas.ui.adapters.DoughnutChartAdapter;

public class MainActivity extends Activity {
    QuotaModel[] quotas;

    private DoughnutChartAdapter doughnutChart;
    private ImageButton fabButton;
    private LinearLayout chartContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init FAB, as well as set onClick
        init();
        setupView();
        createListeners();

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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

    public void init() {
        fabButton = (ImageButton)findViewById(R.id.fab);
        chartContainer = (LinearLayout) findViewById(R.id.chart);
        doughnutChart = new DoughnutChartAdapter(
                getApplicationContext(), quotas);
    }

    public void setupView() {
        fabButton.bringToFront();
        chartContainer.addView(doughnutChart.getView());
    }

    public void createListeners() {
        final View fabView = findViewById(R.id.fab);

        // Open EditActivity on FAB click
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(MainActivity.this,
                        Pair.create((View) fabView, "fab_button"));
                startActivity(i, options.toBundle());
            }
        });
    }
}
