package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class LinearLayoutWithMaxWidth extends LinearLayout {

    public LinearLayoutWithMaxWidth(Context context) {
        super(context);
    }

    public LinearLayoutWithMaxWidth(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutWithMaxWidth(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WeekButton.setSuggestedWidth((View.MeasureSpec.getSize(widthMeasureSpec)) / 7);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
