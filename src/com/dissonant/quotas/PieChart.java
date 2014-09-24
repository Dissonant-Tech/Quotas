package com.dissonant.quotas;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class PieChart {
    public GraphicalView mChart;
    public XYMultipleSeriesDataset mDataSet = new XYMultipleSeriesDataset();
    public XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

    public XYSeries mCurrentSeries;
    public XYSeriesRenderer mCurrentRenderer;

    public void initChart() {
        mCurrentSeries = new XYSeries("Sample Data");
        mDataSet.addSeries(mCurrentSeries);
        mCurrentRenderer = new XYSeriesRenderer();
        mRenderer.addSeriesRenderer(mCurrentRenderer);
    }

    public void addSampleData() {
        mCurrentSeries.add(1,2);
        mCurrentSeries.add(2,3);
        mCurrentSeries.add(3,2);
        mCurrentSeries.add(4,5);
        mCurrentSeries.add(5,3);
        mCurrentSeries.add(4,6);
    }

}
