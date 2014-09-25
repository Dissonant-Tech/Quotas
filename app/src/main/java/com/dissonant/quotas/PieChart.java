package com.dissonant.quotas;

import android.graphics.Color;

import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.Random;


public class PieChart {
    private float []TimeValues;
    private Color []mColors;
    private Random rand = new Random();

    public CategorySeries mSeries;
    public DefaultRenderer mRenderer;

    public void initChart() {
        mSeries = new CategorySeries("pie");
        mRenderer = new DefaultRenderer();

    }

    public void addSampleData() {
        int []sampleData = {42, 43, 23, 5};
        mSeries.add("Test1", sampleData[0]);
        mSeries.add("Test2", sampleData[1]);
        mSeries.add("Test3", sampleData[2]);
        mSeries.add("Test4", sampleData[3]);
        
        for (int data : sampleData) {
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
