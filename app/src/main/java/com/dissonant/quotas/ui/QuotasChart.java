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

    public void init(Context context) {
        mSeries = new MultipleCategorySeries("QuotasChart");
        mRenderer = new DefaultRenderer();
        
        mView = ChartFactory.getDoughnutChartView(context, mSeries, mRenderer);
    }

    /*
     * Updates Chart after reloading information from the database
     */
    public void updateData() {
        this.repaint();
    }

    /*
     * Repaints the view. Needed after re-opening app or updating data
     */
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

        this.updateData();
    }
    
    public int genColor() {
        return Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
}
