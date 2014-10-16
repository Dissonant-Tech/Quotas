package com.dissonant.quotas.ui.adapters;

import org.achartengine.ChartFactory;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.view.View;

import com.dissonant.quotas.R;
import com.dissonant.quotas.db.models.QuotaModel;

public class DoughnutChartAdapter {

	private Context context;

    private String[] titles;
    private double[] values;
    private int[] colors;
    boolean isEmpty = false;
    
    private DefaultRenderer mRenderer;
    private MultipleCategorySeries mSeries;
    
    public DoughnutChartAdapter(Context context) { 
		this.context = context;
        isEmpty = true;

        initChart();
    }

    public DoughnutChartAdapter(Context context, QuotaModel[] quotas) { 
		this.context = context;
        if (quotas != null) {
            parseQuotas(quotas);
        } else {
            isEmpty = true;
        }

        initChart();
    }

    public DoughnutChartAdapter(Context context, String[] titles, 
            double[] values, int[] colors) { 
		this.context = context;
        this.titles = titles;
        this.values = values;
        this.colors = colors;

        initChart();
    }

    public View getView() {
        return ChartFactory.getDoughnutChartView(
                this.context, this.mSeries, this.mRenderer);
    }

    public void parseQuotas(QuotaModel[] quotas) {
        for (int i = 0; i < quotas.length; i++) {
            this.titles[i] = quotas[i].getTitle();
            this.values[i] = quotas[i].getDurationAsInt();
            this.colors[i] = quotas[i].getColor();
        }
    }

    public void initChart() {
        mSeries = new MultipleCategorySeries("QuotasChart");
        mRenderer = setupRenderer();

        update();
    }

    public void update() {
        mSeries.clear();
        mRenderer.removeAllRenderers();

        if (isEmpty) {
            setEmpty();
        } else {
            mSeries.add(titles, values);

            for (int i = 0; i < titles.length; i++) {
                SimpleSeriesRenderer r = new SimpleSeriesRenderer();
                r.setColor(colors[i]);
                r.setDisplayChartValues(false);

                mRenderer.addSeriesRenderer(r);
            }
        }
    }
    
    public void setEmpty() {
        mSeries.clear();
        mRenderer.removeAllRenderers();

        double []emptyData = {100};
        String []emptyTitle = {"None"};

        mSeries.add(emptyTitle, emptyData);

        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(context.getResources().getColor(R.color.LightGrey));
        r.setDisplayChartValues(false);

        mRenderer.addSeriesRenderer(r);

    }
    
    public DefaultRenderer setupRenderer() {
        DefaultRenderer renderer = new DefaultRenderer();

        renderer.setChartTitle(
                context.getResources().getText(R.string.chart_empty).toString());
        renderer.setBackgroundColor(context.getResources().getColor(R.color.primary));
        renderer.setChartTitleTextSize(24f);
        renderer.setShowLabels(false);
        renderer.setShowLegend(false);
        
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);

        renderer.setAntialiasing(true);
        renderer.setLabelsTextSize(0.0f);


        return renderer;
    }
}
