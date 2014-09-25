package com.dissonant.quotas;

import java.util.Random;

import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;


public class PieChart {
    private float []TimeValues;
    private Color []mColors;

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
            r.setDisplayChartValues(true);
            r.setDisplayBoundingPoints(true);
            mRenderer.addSeriesRenderer(r);
        }
    }

    public void genColors() {
        Random mRand = new Random();

        for (int i = 0; i < this.TimeValues.length; ++i) {
            
        }
    }

    public void updateData() {
        this.genColors();
    }

}
