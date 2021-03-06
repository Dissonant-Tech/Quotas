package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dissonant.quotas.R;

public class EditView extends LinearLayout {

    public EditView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public String getTitle() {
        return ((TextView) this.findViewById(R.id.title)).getText().toString();
    }

    public TextView getTitleView() {
        return (TextView) this.findViewById(R.id.title);
    }

    public String getDescription() {
        return ((TextView) this.findViewById(R.id.description)).getText().toString();
    }

    /*
     * Start and End Time buttons
     */

    public View getStartView() {
        return this.findViewById(R.id.starttime_button);
    }

    public View getEndView() {
        return this.findViewById(R.id.endtime_button);
    }

    public void setStartView(String text) {
        ((Button) this.findViewById(R.id.starttime_button)).setText(text);
    }

    public void setEndView(String text) {
        ((Button) this.findViewById(R.id.endtime_button)).setText(text);
    }

    /**
     * Color Picker
     */

    public View getColorPicker() {
        return this.findViewById(R.id.colorpicker);
    }

    public void setColorPicked(String name, int color) {
        ((TextView) this.findViewById(R.id.color_value)).setText(name);
        ((ImageView) this.findViewById(R.id.color_icon)).setColorFilter(color);
    }

    /**
     * TimeRange/Duration
     */

    public View getTimeRange() {
        return this.findViewById(R.id.timerange);
    }

    /**
     * Repeat Switch/Options
     */

    public View getRepeat() {
        return this.findViewById(R.id.repeat);
    }
}
