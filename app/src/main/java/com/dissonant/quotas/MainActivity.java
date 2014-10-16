package com.dissonant.quotas;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Outline;
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
import android.widget.SearchView;

import com.dissonant.quotas.db.models.QuotaModel;
import com.dissonant.quotas.ui.adapters.DoughnutChartAdapter;

public class MainActivity extends Activity {
    boolean resuming = false;

    QuotaModel[] quotas;

    private DoughnutChartAdapter doughnutChart;
    private ImageButton fabButton;
    private Animation slideUp;
    private View mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        mCardView = (View) findViewById(R.id.card_view);

        //init FAB, as well as set onClick
        initView();
        createClickListeners();

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

    public void initView() {
        fabButton = (ImageButton)findViewById(R.id.fab);
        LinearLayout chartLayout = (LinearLayout) findViewById(R.id.chart);

        // FAB Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline mOutline = new Outline();
        mOutline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(mOutline);

        // achartengine Doughnut Chart
        doughnutChart = new DoughnutChartAdapter(
                getApplicationContext(), quotas);
        chartLayout.addView(doughnutChart.getView());
    }

    public void createClickListeners() {
        final View fabView = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(MainActivity.this,
                        Pair.create((View) fabView, "fab_button"));
                //mCardView.startAnimation(slideUp);
                startActivity(i, options.toBundle());
            }
        });
    }
}
