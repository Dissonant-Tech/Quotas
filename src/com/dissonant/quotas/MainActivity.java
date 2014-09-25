package com.dissonant.quotas;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class MainActivity extends Activity {

    GraphicalView mChart;
    PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FAB Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline mOutline = new Outline();
        mOutline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(mOutline);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if (mChart == null) {
            mPieChart = new PieChart();
            mPieChart.initChart();
            mPieChart.addSampleData();
            mChart = ChartFactory.getPieChartView(this, mPieChart.mSeries, mPieChart.mRenderer);
            layout.addView(mChart);
        } else {
            mChart.repaint();
        }
    }

}
