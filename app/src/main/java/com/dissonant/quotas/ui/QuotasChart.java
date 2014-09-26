package com.dissonant.quotas.ui;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class QuotasChart {
    private GraphicalView mView;
    private DefaultRenderer mRenderer;
    private MultipleCategorySeries mSeries;

    public void initChart() {
        mSeries = new MultipleCategorySeries("QuotasChart");
        mRenderer = new DefaultRenderer();
    }

    /*
     * Updates Chart after reloading information from the database
     */
    public void updateData() {

    }

    /*
     * Initialize the view
     */
    public void initView(Context context) {
        mView = ChartFactory.getDoughnutChartView(context, mSeries, mRenderer);
    }

    public void repaint() {
        mView.repaint();
    }

    public GraphicalView getView() {
        return this.mView;
    }


    /*============  TESTING  =============*/

    Random rand = new Random();

    public void useSampleData() {
        double []sampleData = {42, 43, 23, 5};
        String []sampleTitles = {"test1", "test2", "test3", "test4"};
        mSeries.add(sampleTitles, sampleData);

        for (double data : sampleData) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(genColor());
            r.setDisplayChartValues(true);
            r.setDisplayBoundingPoints(true);
            mRenderer.addSeriesRenderer(r);
        }
        mRenderer.setShowLegend(false);
        mRenderer.setZoomEnabled(false);
        mRenderer.setPanEnabled(false);

        mRenderer.setAntialiasing(true);
        mRenderer.setLabelsTextSize(24.0f);
    }
    
    public int genColor() {
        return Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
}
